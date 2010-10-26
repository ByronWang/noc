package httpd.engine;

import httpd.resource.TemplateResource;
import noc.frame.Store;
import noc.lang.reflect.Type;

import org.simpleframework.http.Address;
import org.simpleframework.http.Path;
import org.simpleframework.http.resource.Resource;

import frame.Engine;
import freemarker.template.Configuration;

public class PresentationResourceEngine implements Engine<Address, Resource> {

    Configuration templateEngine;
    Store<String, Type> typeStore;

    public PresentationResourceEngine(Configuration templateEngine, Store<String, Type> typeStore) {
        this.templateEngine = templateEngine;
        this.typeStore = typeStore;
    }

    @Override
    public Resource resolve(Address target) {
        Path path = target.getPath();
        if (path.getName() == null) {
            Resource res = new TemplateResource(templateEngine, typeStore, target, "list");
            return res;
        } else {
            Resource res = new TemplateResource(templateEngine, typeStore, target, "edit");
            return res;
        }
    }

}
