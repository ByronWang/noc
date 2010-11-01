package httpd.engine;

import httpd.Loader;
import httpd.Source;
import httpd.resource.StaticResource;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;

public class StaticResourceEngine implements Engine<Address, Resource> {

    // protected final File homeDir;
    protected final Loader loader;

    public StaticResourceEngine(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Resource resolve(Address target) {
        Source source = loader.findSource(target.getPath().getPath());
        if (source != null) {
            return new StaticResource(source, target);
        } else {
            throw new RuntimeException(target.toString());
        }
    }

}
