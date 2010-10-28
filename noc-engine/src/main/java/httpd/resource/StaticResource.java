package httpd.resource;

import httpd.Loader;
import httpd.Source;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class StaticResource implements CachableResource<Source>, Resource {
    private static final Log log = LogFactory.getLog(StaticResource.class);

    private final Source underlySource;
    // private final Address address;
    private final String mime;
    Loader loader;

    public StaticResource(Source source, Address address) {
        this.underlySource = source;
        this.mime = theMimeTypes.get(address.getPath().getExtension());

        this.update();
    }

    // For Cache Check file
    final int delay = 6000;
    long lastChecked = -1;
    long sourceLastModified = -1;

    long lastModified = -1;

    public void update() {
        log.debug("check update " + this.underlySource.getName());

        long srcLastModified = underlySource.getLastModified();

        if (srcLastModified - sourceLastModified > 1000) {
            reload();
        }

        lastChecked = System.currentTimeMillis();
    }

    synchronized public void reload() {
        log.debug("check to reload " + this.underlySource.getName());

        this.sourceLastModified = underlySource.getLastModified();
        this.lastModified = this.sourceLastModified; // System.currentTimeMillis();

        log.debug("refresh template, bufferStream " + this.sourceLastModified);
    }

    @Override
    public long lastModified() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            update();
        }
        return this.lastModified;
    }

    @Override
    public Source getUnderlyObject() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            update();
        }
        return this.underlySource;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            long now = System.currentTimeMillis();
            if (now - lastChecked >= delay) {
                update();
            }

            // Cache
            long clientLastModified = req.getDate("If-Modified-Since");
            if (clientLastModified > 0) {
                if (this.lastModified - clientLastModified <= 1000) {
                    resp.set("Cache-Control", "max-age=60000");
                    // resp.set("ETag", "\"" + underlyFile.lastModified() +
                    // "\"");
                    // resp.setDate("Date", System.currentTimeMillis());
                    resp.setCode(304);
                    resp.close();
                    log.debug(req.getPath() + " 304 : The document has not been modified! ");
                    return;
                }
            }

            // normal parse
            resp.set("Cache-Control", "max-age=6000");
            resp.set("Content-Language", "en-US");
            resp.set("Content-Type", mime);
            resp.setContentLength((int) underlySource.getLength());
            resp.setDate("Date", System.currentTimeMillis());
            resp.setDate("Last-Modified", this.lastModified);
            resp.set("ETag", "\"" + this.lastModified + "\"");
            // max-age
            log.debug("Load file [" + underlySource.getName() + "] contents to client by [[" + req.getPath() + "]]");
            InputStream in = underlySource.getInputStream(null);
            OutputStream out = resp.getOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
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
