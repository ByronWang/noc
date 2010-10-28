package httpd.engine;

import httpd.ClassPathLoader;
import httpd.FileSystemLoader;
import httpd.Loader;
import httpd.MultiLoader;
import httpd.Source;
import httpd.resource.NullResource;
import httpd.resource.StaticResource;

import java.io.File;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;

public class StaticResourceEngine implements Engine<Address, Resource> {

//    protected final File homeDir;
    protected final Resource unknownResource;
    protected final Loader loader;

    public Resource getUnknownResource() {
        return unknownResource;
    }

    public StaticResourceEngine(String path) {
//        this.homeDir = root;
        this.loader = new MultiLoader(new FileSystemLoader("src/main/resources/" + path),
                new FileSystemLoader(path), new ClassPathLoader(this.getClass().getClassLoader(),
                        path));
        unknownResource = new NullResource(new File(path, "404.htm"));
    }

    @Override
    public Resource resolve(Address target) {
        Source source = loader.findSource(target.getPath().getPath());
        if (source != null) {
            return new StaticResource(source, target);
        } else {
            return unknownResource;
        }
    }

}
