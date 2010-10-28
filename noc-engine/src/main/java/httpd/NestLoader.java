package httpd;

import java.io.IOException;
import java.io.InputStream;

interface NestLoader extends Loader {

    long getLastModified(Object source);
    long getLength(Object source);

    InputStream getInputStream(Object source, String encoding) throws IOException;

    void closeSource(Object source) throws IOException;
}
