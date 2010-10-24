package httpd.engine;

import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.resource.Resource;

import frame.Engine;

public class ResourceContainer implements Container {   
   private final Engine<Address,Resource> engine;

   public ResourceContainer(){
       this(new ResourceEngine());
   }
   
   public ResourceContainer(Engine<Address,Resource> engine){
      this.engine = engine;
   }

   public void handle(Request req, Response resp){
      engine.resolve(req.getAddress()).handle(req,resp);
   }
}
