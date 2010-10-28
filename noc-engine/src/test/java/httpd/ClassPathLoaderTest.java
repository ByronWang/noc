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
        Source o = loader.findSource("/TemplateLoader.class");
        assertNotNull(o);
    }

    public void testGetLastModified() throws IOException {
        Source o = loader.findSource("/TemplateLoader.class");
        long lm = o.getLastModified();
        System.out.println(lm);
    }

    public void testGetReader() throws IOException {
        Source o = loader.findSource("/TemplateLoader.class");
        InputStream in = o.getInputStream();
        assertEquals(true, in.read() > 0);
        in.close();
    }
    
    public void testGetLength() throws IOException {
        Source o = loader.findSource("/TemplateLoader.class");
        long length = o.getLength();
        assertEquals(true, length > 0);
    }

    public void testCloseSource() throws IOException {
        Source o = loader.findSource("/TemplateLoader.class");
        o.closeSource();
    }

}
