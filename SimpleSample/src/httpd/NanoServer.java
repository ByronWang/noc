package httpd;

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
                
        log.info("start at 8081");
        Container container = new ResourceContainer();
        log.info("container at 8081");
        Connection connection = new SocketConnection(container);
        SocketAddress address1 = new InetSocketAddress(80);
        connection.connect(address1);
        log.info("listing at 80");
        SocketAddress address2 = new InetSocketAddress(8081);
        connection.connect(address2);
        log.info("listing at 8081");
        SocketAddress address3 = new InetSocketAddress(8083);
        connection.connect(address3);
        log.info("listing at 8081");
    }
}
