package httpd.resource;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class NullResource implements Resource {

    private final File f;
    private final String mime;

    public NullResource(File file) {
        this.f = file;
        this.mime = "text/html";
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            resp.setCode(404);
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
