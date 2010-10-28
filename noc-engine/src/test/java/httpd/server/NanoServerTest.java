package httpd.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

public class NanoServerTest extends TestCase {

    public void testJarPath() throws Exception {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource("freemarker/core/Comment.class");

        if (url != null) {
            if ("jar".equals(url.getProtocol())) {
                System.out.println(url.openStream().getClass().getName());
                URLConnection conn = url.openConnection();
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                BufferedInputStream bio = new BufferedInputStream(conn.getInputStream());
                int bufferSize = 1024;
                byte[] data = new byte[bufferSize];
                int count = -1;
                while ((count = bio.read(data)) > 0) {
                    bout.write(data, 0, count);
                }
                bout.flush();

                bio.close();
                bout.close();

                byte[] bb = bout.toByteArray();

                System.out.println(bb.length);
            } else if ("file".equals(url.getProtocol())) {
                File file = new File(url.getFile());
                System.out.println(file.lastModified());

                File lo = new File(file, "login.htm");
                if (lo.exists()) {
                    System.out.println(lo.getPath() + " - " + lo.lastModified());
                }
            }
        }
    }
}
