package httpd.resource;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class StaticResource implements Resource {
    private static final Log log = LogFactory.getLog(StaticResource.class);

    private final File f;
    // private final Address address;
    private final String mime;

    // For Cache Check file

    final int delay = 6000;

    long lastChecked;
    long lastModified;

    public StaticResource(File file, Address address) {
        this.f = file;
        // this.address = address;
        this.mime = theMimeTypes.get(address.getPath().getExtension());
        lastChecked = System.currentTimeMillis();
        lastModified = f.lastModified();
    }

    public long lastModified() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            lastModified = f.lastModified();
            log.debug("Check File[ " + f.getPath() + "] LastModified : " + lastModified);
            lastChecked = now;
        }
        return lastModified;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            // Cache
            long req_LastModified = req.getDate("If-Modified-Since");
            if (req_LastModified > 0) {
                lastModified = lastModified();

                if (lastModified - req_LastModified <= 1000) {
                    resp.setCode(304);
                    resp.close();
                    log.debug(req.getPath() + " Response 304 no change");
                    return;
                }
            }

            // normal parse

            resp.set("Cache-Control", "max-age=60000000000");
            resp.set("Content-Language", "en-US");
            resp.set("Content-Type", mime);
            resp.setContentLength((int) f.length());
            resp.setDate("Date", System.currentTimeMillis());
            resp.setDate("Last-Modified", f.lastModified());
            resp.set("ETag", "\"" + f.lastModified() + "\"");
            // max-age
            log.debug("Transfer file [" + f.getPath() + "] contents to client by [[" + req.getPath() + "]]");
            FileChannel in = new FileInputStream(f).getChannel();
            WritableByteChannel out = resp.getByteChannel();
            in.transferTo(0, f.length(), out);

            in.close();
            resp.close();
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Hashtable<String, String> getTheMimeTypes() {
        return theMimeTypes;
    }

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static Hashtable<String, String> theMimeTypes = new Hashtable<String, String>();
    static {
        StringTokenizer st = new StringTokenizer("htm        text/html " + "html       text/html "
                + "txt        text/plain " + "java       text/plain " + "ftl        text/plain "
                + "asc        text/plain " + "gif        image/gif " + "jpg        image/jpeg "
                + "jpeg       image/jpeg " + "png        image/png " + "mp3        audio/mpeg "
                + "m3u        audio/mpeg-url " + "pdf        application/pdf " + "doc        application/msword "
                + "ogg        application/x-ogg " + "zip        application/octet-stream "
                + "exe        application/octet-stream " + "class      application/octet-stream ");
        // "htm        text/html " +
        // "html       text/html " +
        // "txt        text/plain " +
        // "java       text/plain " +
        // "ftl        freemarker template " +
        // "asc        text/plain " +
        // "gif        image/gif " +
        // "jpg        image/jpeg " +
        // "jpeg       image/jpeg " +
        // "png        image/png " +
        // "mp3        audio/mpeg " +
        // "m3u        audio/mpeg-url " +
        // "pdf        application/pdf " +
        // "doc        application/msword " +
        // "ogg        application/x-ogg " +
        // "zip        application/octet-stream " +
        // "exe        application/octet-stream " +
        // "class      application/octet-stream ");

        while (st.hasMoreTokens())
            theMimeTypes.put(st.nextToken(), st.nextToken());
    }

}
