package httpd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ClassPathLoader implements NestLoader {
    final protected ClassLoader classLoader;
    final protected String appHome;

    public ClassPathLoader(ClassLoader classLoader, String appHome) {
        this.classLoader = classLoader;
        this.appHome = appHome;
    }

    @Override
    public Source findSource(String name) {
        URL url = classLoader.getResource(appHome + "/" + name);
        return new Source(name, url, this);
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
    public InputStream getInputStream(Object source, String encoding) throws IOException {
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
