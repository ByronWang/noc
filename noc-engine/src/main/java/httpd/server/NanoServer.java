package httpd.server;

import httpd.engine.ResourceContainer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class NanoServer {
    private static final Log log = LogFactory.getLog(NanoServer.class);

    public static void main(String[] list) throws Exception {
        int port = 80;
        Container container = new ResourceContainer();
        Connection connection = new SocketConnection(container);
        SocketAddress address1 = new InetSocketAddress(port);
        connection.connect(address1);
        log.info("== listen at " + port);
    }
}
