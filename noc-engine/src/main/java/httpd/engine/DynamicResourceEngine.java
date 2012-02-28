package httpd.engine;

import httpd.resource.CachableResource;
import httpd.resource.DynamicResource;

import java.io.File;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import com.google.inject.Inject;

import frame.Engine;
import freemarker.template.Template;

public class DynamicResourceEngine implements Engine<Address, Resource> {
    protected final PresentationResourceEngine presentationEngine;
    protected final DataResourceEngine dataResourceEngine;
    protected final File appHome;

    @Inject
    public DynamicResourceEngine(PresentationResourceEngine presentationEngine, DataResourceEngine dataResourceEngine,
            File appHome) {
        this.presentationEngine = presentationEngine;
        this.dataResourceEngine = dataResourceEngine;
        this.appHome = appHome;
    }

    public File getRealPath(String path) {
        return new File(this.appHome, path);
    }

    @Override
    public Resource resolve(Address target) {

        CachableResource<Template> presentationResource;
        CachableResource<Object> dataResource;
        if (target.getPath().getName() != null) {
            dataResource = dataResourceEngine.resolve(target);
            presentationResource = presentationEngine.resolve(target);
        } else {
            dataResource = dataResourceEngine.resolve(target);
            presentationResource = presentationEngine.resolve(target);
        }

        DynamicResource dynamicResource = new DynamicResource(target, dataResource, presentationResource);
        return dynamicResource;
    }

}
