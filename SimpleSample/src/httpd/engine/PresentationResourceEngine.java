package httpd.engine;

import httpd.resource.TemplateResource;

import java.io.File;
import java.io.IOException;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;
import freemarker.template.Configuration;

public class PresentationResourceEngine implements Engine<Address, Resource> {

    Configuration templateEngine;
    Store<String, Type> typeStore;

    public PresentationResourceEngine(File root, Store<String, Type> typeStore) {
        try {
            /* Create and adjust the configuration */
            templateEngine = new Configuration();
            templateEngine.setTemplateUpdateDelay(10);
            templateEngine.setDirectoryForTemplateLoading(root);
            this.typeStore = typeStore;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    

    @Override
    public Resource resolve(Address target) {
        // target.getPath().
//        String typeName = target.getPath().getSegments()[1];
        Resource res = new TemplateResource(templateEngine, typeStore, target, "edit");
        return res;
    }

}
