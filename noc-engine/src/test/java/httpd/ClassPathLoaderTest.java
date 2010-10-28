package httpd;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class ClassPathLoaderTest extends TestCase {

    NestLoader loader;

    @Override
    protected void setUp() throws Exception {
        loader = new ClassPathLoader(this.getClass().getClassLoader(), "freemarker/cache");
    }

    public void testClassPathLoader() {
    }

    public void testFindSource() throws IOException {
        Object o = loader.findSource("TemplateLoader.class");
        assertNotNull(o);
    }

    public void testGetLastModified() throws IOException {
        Object o = loader.findSource("TemplateLoader.class");
        long lm = loader.getLastModified(o);
        System.out.println(lm);
    }

    public void testGetReader() throws IOException {
        Object o = loader.findSource("TemplateLoader.class");
        InputStream in = loader.getInputStream(o, null);
        assertEquals(true, in.read() > 0);
        in.close();
    }

    public void testCloseSource() throws IOException {
        Object o = loader.findSource("TemplateLoader.class");
        loader.closeSource(o);
    }

}
