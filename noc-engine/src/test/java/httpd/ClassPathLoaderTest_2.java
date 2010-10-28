package httpd;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class ClassPathLoaderTest_2 extends TestCase {

    Loader loader;

    @Override
    protected void setUp() throws Exception {
        loader = new ClassPathLoader(this.getClass().getClassLoader(), "htdocs");
    }

    public void testClassPathLoader() {
    }

    public void testFindSource() throws IOException {
        Object o = loader.findSource("/login.htm");
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
