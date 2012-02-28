package httpd.engine;

import java.io.IOException;

import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.resource.Resource;

import util.PrintObejct;

import com.google.inject.Inject;

import frame.Engine;

public class RestHttpContainer implements Container {
    private final Engine<Address, Resource> engine;

    @Inject
    public RestHttpContainer(ResourceEngine engine) {
        this.engine = engine;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            PrintObejct.print(Address.class, req.getAddress());
            engine.resolve(req.getAddress()).handle(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
            resp.setCode(404);
            try {
                resp.close();
            } catch (IOException e1) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode(404);
            try {
                resp.close();
            } catch (IOException e1) {
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resp.setCode(404);
            try {
                resp.close();
            } catch (IOException e1) {
            }
        }
    }
}
