package httpd.io;

import java.io.IOException;
import java.io.InputStream;

public class Source {
    private final Object source;
    private final NestLoader loader;
    private final String name;

    public long getLength() {
        return loader.getLength(source);
    }

    Source(String name, Object source, NestLoader loader) {
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

    public InputStream getInputStream() throws IOException {
        return loader.getInputStream(source);
    }

    public void close() throws IOException {
        loader.close(source);
    }
}