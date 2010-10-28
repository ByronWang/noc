package httpd;

import java.io.IOException;
import java.io.InputStream;

public class Source {
    private final Object source;
    private final NestLoader loader;
    private final String name;
    private int length;

    public int getLength() {
        return length;
    }

    Source(String name,Object source, NestLoader loader) {
        this.name = name;
        this.source = source;
        this.loader = loader;
    }

    public String getName() {
        return name;
    }

    public long getLastModified() {
        return loader.getLastModified(source);
    }

    public InputStream getInputStream(String encoding) throws IOException {
        return loader.getInputStream(source, encoding);
    }

    public void closeSource() throws IOException {
        loader.closeSource(source);
    }
}