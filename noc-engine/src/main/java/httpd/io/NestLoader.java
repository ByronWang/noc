package httpd.io;

import java.io.IOException;
import java.io.InputStream;


interface NestLoader extends Loader {

    long getLastModified(Object source);

    long getLength(Object source);

    InputStream getInputStream(Object source) throws IOException;

    void close(Object source) throws IOException;
}
