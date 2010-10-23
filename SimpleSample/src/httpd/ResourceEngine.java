package httpd;

import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.Path;
import org.simpleframework.http.resource.Resource;

import frame.AbstractEngine;

public class ResourceEngine extends AbstractEngine {
    private static final Log log = LogFactory.getLog(StaticResource.class);
    final StaticResourceEngine staticEngine;

    public ResourceEngine() {
        this(new StaticResourceEngine());
    }

    public ResourceEngine(StaticResourceEngine staticEngine) {
        this.staticEngine = staticEngine;
    }

    @Override
    public Resource make(Address target) {
        log.debug("ADDRESS : " + target.toString());
        
        Path path = target.getPath();
        if (path.getDirectory().length() == 1 || staticPath.containsKey(path.getPath(0, 1))) {
            return staticEngine.make(target);
        } else {
            return staticEngine.unknownResource;
        }
    }

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static final Hashtable<String, String> staticPath = new Hashtable<String, String>();
    static {
        StringTokenizer st = new StringTokenizer(
                "/        root " 
                + "/js       js " 
                + "/css       css " 
                + "/template  template "
                + "/images    image ");
        while (st.hasMoreTokens())
            staticPath.put(st.nextToken(), st.nextToken());
    }
}
