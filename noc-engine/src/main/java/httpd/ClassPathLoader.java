package httpd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassPathLoader implements NestLoader {
    private static final Log log = LogFactory.getLog(ClassPathLoader.class);

    final protected ClassLoader classLoader;
    final protected String appHome;

    public ClassPathLoader(ClassLoader classLoader, String appHome) {
        this.classLoader = classLoader;
        this.appHome = appHome;
    }

    @Override
    public Source findSource(String name) {
        URL url = classLoader.getResource(appHome + name);
        if (url != null) {
            log.debug("findSrouce " + appHome + name + " --  SUCCEED");
            return new Source(name, url, this);
        }
        return null;
    }

    @Override
    public long getLastModified(Object source) {
        try {
            URL urlSource = (URL) source;
            return urlSource.openConnection().getLastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream(Object source) throws IOException {
        URL urlSource = (URL) source;
        return urlSource.openStream();
    }

    @Override
    public void closeSource(Object source) throws IOException {
        ;
    }

    @Override
    public long getLength(Object source) {
        try {
            URL urlSource = (URL) source;
            return urlSource.openConnection().getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
