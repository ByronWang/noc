package httpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemLoader implements NestLoader {
    final protected File appHome;

    public FileSystemLoader(String rootPath) {
        this.appHome = new File(rootPath);
        if(!this.appHome.exists()){
            throw new RuntimeException();
        }
    }

    @Override
    public Source findSource(String name){
        File file = new File(appHome, name);
        if (file.exists()) {
            return new Source(name,file, this);
        }
        return null;
    }

    @Override
    public long getLastModified(Object source) {
        File fileSource= (File)source;
        return fileSource.lastModified();
    }

    @Override
    public InputStream getInputStream(Object source, String encoding) throws IOException {
        File fileSource= (File)source;
        return new FileInputStream(fileSource);
    }

    @Override
    public void closeSource(Object source) throws IOException {
        ;
    }

    @Override
    public long getLength(Object source) {   
        File fileSource= (File)source;
        return fileSource.length();
    }

}
