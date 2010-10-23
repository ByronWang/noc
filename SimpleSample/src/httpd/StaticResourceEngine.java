package httpd;

import java.io.File;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.AbstractEngine;

public class StaticResourceEngine extends AbstractEngine {

    protected final File homeDir;    
    protected final Resource unknownResource;

    public Resource getUnknownResource() {
        return unknownResource;
    }

    public StaticResourceEngine() {
        this(new File("./htdocs"));
    }

    public StaticResourceEngine(String root) {
        this(new File(root));
    }

    public StaticResourceEngine(File root) {
        this.homeDir = root;
        unknownResource = new NullResource(new File(this.homeDir,"404.html"));
    }

    @Override
    public Resource make(Address target) {
        File f = new File(homeDir,target.getPath().getPath());
        if(f.exists()){
            return new StaticResource(f, target);
        }else{
            return unknownResource;
        }
    }

}
