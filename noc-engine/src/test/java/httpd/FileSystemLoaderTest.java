package httpd;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class FileSystemLoaderTest extends TestCase {

    NestLoader loader;

    @Override
    protected void setUp() throws Exception {
        loader = new FileSystemLoader("src/main/resources/htdocs");
    }

    public void testClassPathLoader() {
    }

    public void testFindSource() throws IOException {
        Object o = loader.findSource("login.htm");
        assertNotNull(o);
    }

    public void testGetLastModified() throws IOException {
        Object o = loader.findSource("login.htm");
        long lm = loader.getLastModified(o);
        System.out.println(lm);
    }

    public void testGetReader() throws IOException {
        Object o = loader.findSource("login.htm");
        InputStream in = loader.getInputStream(o, null);
        assertEquals(true, in.read() > 0);
        in.close();
    }

    public void testCloseSource() throws IOException {
        Object o = loader.findSource("login.htm");
        loader.closeSource(o);
    }

}
