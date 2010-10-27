package httpd.engine;

import httpd.resource.NullResource;
import httpd.resource.StaticResource;

import java.io.File;

import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;

public class StaticResourceEngine  implements Engine<Address, Resource>  {

    protected final File homeDir;    
    protected final Resource unknownResource;

    public Resource getUnknownResource() {
        return unknownResource;
    }

    public StaticResourceEngine(File root) {
        this.homeDir = root;
        unknownResource = new NullResource(new File(this.homeDir,"404.htm"));
    }

    @Override
    public Resource resolve(Address target) {
        File f = new File(homeDir,target.getPath().getPath());
        if(f.exists()){
            return new StaticResource(f, target);
        }else{
            return unknownResource;
        }
    }

}
