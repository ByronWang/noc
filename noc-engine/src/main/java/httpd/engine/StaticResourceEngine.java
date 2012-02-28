package httpd.engine;

import httpd.io.Loader;
import httpd.io.Source;
import httpd.resource.StaticResource;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import com.google.inject.Inject;

import frame.Engine;

public class StaticResourceEngine implements Engine<Address, Resource> {
    protected final Loader loader;

    @Inject
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
