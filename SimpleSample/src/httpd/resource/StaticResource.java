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

    public StaticResource(File file, Address address) {
        this.f = file;
        // this.address = address;
        this.mime = theMimeTypes.get(address.getPath().getExtension());
    }

    @Override
    public void handle(Request req, Response resp) {
        log.debug("METHOD " + req.getMethod());
        
        try {
            resp.set("Cache-control", "max-age=60000");
            resp.set("Content-Language", "en-US");
            resp.set("Content-Type", mime);
            resp.setContentLength((int) f.length());
            resp.setDate("Date", f.lastModified());
            resp.setDate("Last-Modified", f.lastModified());
//max-age
            FileChannel in = new FileInputStream(f).getChannel();
            WritableByteChannel out = resp.getByteChannel();
            in.transferTo(0, f.length(), out);

            in.close();
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
        StringTokenizer st = new StringTokenizer(
                "htm        text/html " + 
                "html       text/html "  + 
                "txt        text/plain " +
                "java       text/plain " +
                "ftl        text/plain " +
                "asc        text/plain " + 
                "gif        image/gif " + 
                "jpg        image/jpeg "  + 
                "jpeg       image/jpeg " +
                "png        image/png " +
                "mp3        audio/mpeg "  +
                "m3u        audio/mpeg-url " +
                "pdf        application/pdf " +
                "doc        application/msword "  + 
                "ogg        application/x-ogg " + 
                "zip        application/octet-stream "  + 
                "exe        application/octet-stream " + 
                "class      application/octet-stream ");
//        "htm        text/html " + 
//        "html       text/html "  + 
//        "txt        text/plain " +
//        "java       text/plain " +
//        "ftl        freemarker template " +
//        "asc        text/plain " + 
//        "gif        image/gif " + 
//        "jpg        image/jpeg "  + 
//        "jpeg       image/jpeg " +
//        "png        image/png " +
//        "mp3        audio/mpeg "  +
//        "m3u        audio/mpeg-url " +
//        "pdf        application/pdf " +
//        "doc        application/msword "  + 
//        "ogg        application/x-ogg " + 
//        "zip        application/octet-stream "  + 
//        "exe        application/octet-stream " + 
//        "class      application/octet-stream ");
        
        
        while (st.hasMoreTokens())
            theMimeTypes.put(st.nextToken(), st.nextToken());
    }

}
