package httpd.engine;

import httpd.resource.CachableResource;
import httpd.resource.TemplateResource;
import noc.frame.Store;
import noc.lang.reflect.Type;

import org.simpleframework.http.Address;
import org.simpleframework.http.Path;

import frame.Engine;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class PresentationResourceEngine implements Engine<Address, CachableResource<Template>> {

    Configuration templateEngine;
    Store<String, Type> typeStore;

    public PresentationResourceEngine(Configuration templateEngine, Store<String, Type> typeStore) {
        this.templateEngine = templateEngine;
        this.typeStore = typeStore;
    }

    @Override
    public CachableResource<Template> resolve(Address target) {
        Path path = target.getPath();
        if (path.getName() == null) {
            CachableResource<Template> res = new TemplateResource(templateEngine, typeStore, target, "list");
            return res;
        } else {
            CachableResource<Template> res = new TemplateResource(templateEngine, typeStore, target, "edit");
            return res;
        }
    }

}
