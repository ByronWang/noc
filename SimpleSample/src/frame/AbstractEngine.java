package frame;

import httpd.resource.StaticResource;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

public abstract class AbstractEngine implements Engine<Address, Resource> {
    private static final Log log = LogFactory.getLog(StaticResource.class);

    final Map<String, Resource> map;

    public AbstractEngine() {
        map = new HashMap<String, Resource>();
    }

    @Override
    public Resource resolve(Address target) {
        String path = target.getPath().getPath();
        log.debug("ADDRESS : " + target.toString() + "  -- " + target.hashCode());

        Resource o = map.get(path);
        if (o == null) {
            o = this.make(target);
            map.put(path, o);
            o = map.get(path);
        }
        return o;
    }

    public abstract Resource make(Address target);

}
