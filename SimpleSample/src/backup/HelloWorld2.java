package backup;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.session.Session;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class HelloWorld2 implements Container {

	@SuppressWarnings("unchecked")
    public void handle(Request request, Response response) {
		try {
			PrintStream body = response.getPrintStream();
			long time = System.currentTimeMillis();

			String accept = "application/xhtml+xml";
			List<String> allAccept = request.getValues("accept");
			if (allAccept.size() == 1) {
				accept = allAccept.get(0);
			}
			
			System.out.println(request.getTarget());
						
			Session session = request.getSession();
			String name = (String)session.get("name");
			if(name==null){
				session.put("name", "wangshilian");
				name = (String)session.get("name");
			}
			session.put("name", name + "+");
			
			response.set("Content-Type", "text/plain");
			response.set("Server", "HelloWorld/1.0 (Simple 4.0)");
			response.setDate("Date", time);
			response.setDate("Last-Modified", time);
			

			StringBuffer sb = new StringBuffer();

            sb.append("\n ACCEPT: ");
            sb.append(accept);
            sb.append("\n NAME: ");
			sb.append(name);
			sb.append("\n Target: ");
			sb.append(request.getTarget()); // "/index.html?a=b&c=d&e=f&g=h&a=1");
			sb.append("\n Method: ");
			sb.append(request.getMethod()); // "POST");
			sb.append("\n Major: ");
			sb.append(request.getMajor());// 1);
			sb.append("\n Minor: ");
			sb.append(request.getMinor());// 0);
			// sb.append("\n ContentType Primary: ");
			// sb.append(request.getContentType().getPrimary()); //"multipart");
			// sb.append("\n ContentType Secondary: ");
			// sb.append(request.getContentType().getSecondary());
			// //"form-data");
			sb.append("\n Host: ");
			sb.append(request.getValue("Host")); // "some.host.com");
			sb.append("\n Accept: ");

			for (int i = 0; i < allAccept.size(); i++) {
				sb.append(allAccept.get(i));
				sb.append(" ");
			}

			// sb.append(request.getCookie("UID").getValue()); //"1234-5678");
			// sb.append(request.getCookie("UID").getPath()); //"/");
			// sb.append(request.getCookie("UID").getDomain()); //".host.com");
			// sb.append(request.getCookie("NAME").getValue());
			// //"Niall Gallagher");
			// sb.append(request.getCookie("NAME").getPath()); //"/");
			// sb.append(request.getCookie("NAME").getDomain());// null);
			//		      

			// sb.append(request.getContent());
			// response.getPrintStream().print(sb);

			body.println(sb);
			body.close();
			
			System.out.println(dd++);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int dd = 0;
	public static void main(String[] list) throws Exception {
		System.out.println("start at 8081");
		Container container = new HelloWorld2();
		System.out.println("container at 8081");
		Connection connection = new SocketConnection(container);
		System.out.println("connection at 8081");
		SocketAddress address = new InetSocketAddress(8081);
		SocketAddress address2 = new InetSocketAddress(8080);
		SocketAddress address3 = new InetSocketAddress(8083);
		System.out.println("listing at 8081");
		connection.connect(address);
		connection.connect(address2);
		connection.connect(address3);
	}
}
