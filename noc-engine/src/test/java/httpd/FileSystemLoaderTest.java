package httpd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class FileSystemLoaderTest extends TestCase {

    NestLoader loader;

    @Override
    protected void setUp() throws Exception {
        loader = new FileSystemLoader(new File("src/main/resources/htdocs"));
    }

    public void testClassPathLoader() {
    }

    public void testFindSource() throws IOException {
        Source o = loader.findSource("/login.htm");
        assertNotNull(o);
    }

    public void testGetLastModified() throws IOException {
        Source o = loader.findSource("/login.htm");
        long lm = o.getLastModified();
        System.out.println(lm);
    }

    public void testGetReader() throws IOException {
        Source o = loader.findSource("/login.htm");
        InputStream in = o.getInputStream();
        assertEquals(true, in.read() > 0);
        in.close();
    }

    public void testCloseSource() throws IOException {
        Source o = loader.findSource("/login.htm");
        o.closeSource();
    }

}
