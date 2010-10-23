package httptest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

public class ResourceHander implements Container {
    // ==================================================
    // API parts
    // ==================================================

    /**
     * Some HTTP response status codes
     */
    public static final String HTTP_OK = "200 OK", HTTP_REDIRECT = "301 Moved Permanently",
            HTTP_FORBIDDEN = "403 Forbidden", HTTP_NOTFOUND = "404 Not Found", HTTP_BADREQUEST = "400 Bad Request",
            HTTP_INTERNALERROR = "500 Internal Server Error", HTTP_NOTIMPLEMENTED = "501 Not Implemented";

    /**
     * Common mime types for dynamic content
     */
    public static final String MIME_PLAINTEXT = "text/plain", 
    MIME_HTML = "text/html",
            MIME_DEFAULT_BINARY = "application/octet-stream";

    // ==================================================
    // Socket & server code
    // ==================================================

    /**
     * URL-encodes everything between "/"-characters. Encodes spaces as '%20'
     * instead of '+'.
     */
    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/"))
                newUri += "/";
            else if (tok.equals(" "))
                newUri += "%20";
            else {
                // For Java 1.4 you'll want to use this instead:
                try {
                    newUri += URLEncoder.encode(tok, "UTF-8");
                } catch (UnsupportedEncodingException uee) {
                }
            }
        }
        return newUri;
    }

    File myFileDir;

    // ==================================================
    // File server code
    // ==================================================

    File homeDir = new File("./htdocs");

    @Override
    public void handle(Request req, Response resp) {
        
        File f = new File(homeDir, req.getTarget());
        if (!f.exists()) {
            resp.setCode(404);
            resp.set("", MIME_PLAINTEXT);
            // resp.getPrintStream().print("Error 404, file not found.");
            return;
        }

        try {
            // Get MIME type from file name extension, if possible
            String mime = null;
            int dot = f.getCanonicalPath().lastIndexOf('.');
            if (dot >= 0)
                mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
            if (mime == null)
                mime = MIME_DEFAULT_BINARY;

            // Support (simple) skipping:
//            long startFrom = 0;
//            String range = req.getValue("range");
//            if (range != null) {
//                if (range.startsWith("bytes=")) {
//                    range = range.substring("bytes=".length());
//                    int minus = range.indexOf('-');
//                    if (minus > 0)
//                        range = range.substring(0, minus);
//                    try {
//                        startFrom = Long.parseLong(range);
//                    } catch (NumberFormatException nfe) {
//                    }
//                }
//            }

            resp.set("Cache-control", "max-age=60000");
            resp.set("Content-Language", "en-US");
            resp.set("Content-Type", mime);
            resp.setContentLength((int) f.length());
            resp.setDate("Date", f.lastModified());
            resp.setDate("Last-Modified", f.lastModified());

            FileChannel in = new FileInputStream(f).getChannel();
            WritableByteChannel out = resp.getByteChannel();
            in.transferTo(0, f.length(), out);

            in.close();
            return;
        } catch (IOException ioe) {
            resp.setCode(403);
            resp.set("", MIME_PLAINTEXT);
            // resp.getPrintStream().print("FORBIDDEN: Reading file failed.");
            return;
        }
    }

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static Hashtable<String, String> theMimeTypes = new Hashtable<String, String>();
    static
    {
        StringTokenizer st = new StringTokenizer(
            "htm        text/html "+
            "html       text/html "+
            "txt        text/plain "+
            
            "java        text/plain "+
            
            "asc        text/plain "+
            "gif        image/gif "+
            "jpg        image/jpeg "+
            "jpeg       image/jpeg "+
            "png        image/png "+
            "mp3        audio/mpeg "+
            "m3u        audio/mpeg-url " +
            "pdf        application/pdf "+
            "doc        application/msword "+
            "ogg        application/x-ogg "+
            "zip        application/octet-stream "+
            "exe        application/octet-stream "+
            "class      application/octet-stream " );
        while ( st.hasMoreTokens())
            theMimeTypes.put( st.nextToken(), st.nextToken());
    }

    /**
     * GMT date formatter
     */
    private static java.text.SimpleDateFormat gmtFrmt;
    static {
        gmtFrmt = new java.text.SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

}