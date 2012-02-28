package httpd.engine;

import httpd.resource.StaticResource;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import com.google.inject.Inject;

import frame.Engine;

public class ResourceEngine implements Engine<Address, Resource> {
    private static final Log log = LogFactory.getLog(StaticResource.class);
    // String appHome;
    Engine<Address, Resource> defaultEngine;
    Engine<Address, Resource> staticEngine;

    @Inject
    public ResourceEngine(StaticResourceEngine staticEngine, DynamicResourceEngine dynamicEngine) {
        this.staticEngine = staticEngine;
        this.defaultEngine = dynamicEngine;

        engines = new HashMap<String, Engine<Address, ? extends Resource>>();
        this.register("js", staticEngine);
        this.register("css", staticEngine);
        this.register("images", staticEngine);
        this.register("noc", staticEngine);
        this.register("tempalte", staticEngine);
        // this.register("p", dynamicEngine.getPresentationEngine());

        resources = new HashMap<String, Resource>(1024 * 4);

    }

    final Map<String, Resource> resources;

    Map<String, Engine<Address, ? extends Resource>> engines;

    public void register(String key, Engine<Address, ? extends Resource> engine) {
        engines.put(key, engine);
    }

    @Override
    public Resource resolve(Address target) {
        String path = target.getPath().getPath();
        log.debug("Client request : " + target.toString());

        Resource o = resources.get(path);
        if (o == null) {
            o = this.make(target);
            resources.put(path, o);
            o = resources.get(path);
        }
        return o;
    }

    public Resource make(Address target) {
        log.debug("Make resource : " + target.toString());

        // TODO 需要改善性能
        String[] se = target.getPath().getSegments();
        if (se.length == 1) {
            return staticEngine.resolve(target);
        }

        Engine<Address, ? extends Resource> engine = engines.get(se[0]);
        if (engine != null) {
            return engine.resolve(target);
        }

        return defaultEngine.resolve(target);
    }
}
