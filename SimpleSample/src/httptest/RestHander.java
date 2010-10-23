package httptest;

import java.io.PrintStream;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

public class RestHander implements Container {
    ResourceHander fileHandle = new ResourceHander();

    @SuppressWarnings("unchecked")
    public void handle(Request request, Response response) {
        try {
            PrintStream body = response.getPrintStream();
            long time = System.currentTimeMillis();

//            //System.out.println(request.getTarget() + " : " + request.getMethod());
//            String accept = "application/xhtml+xml";
//            List<String> allAccept = request.getValues("accept");
//            if (allAccept.size() == 1) {
//                accept = allAccept.get(0);
//            }

//            String target = request.getTarget();
//            if (target.endsWith(".html") || target.endsWith(".css")|| target.endsWith(".java")|| target.endsWith(".ico") || target.endsWith(".js")
//                    || target.endsWith(".png")) {
//                fileHandle.handle(request, response);
//                body.close();
//                return;
//            }

//            Session session = request.getSession();
//            String name = (String) session.get("name");
//            if (name == null) {
//                session.put("name", "wangshilian");
//                name = (String) session.get("name");
//            }
//            session.put("name", name + "+");

            response.set("Content-Type", "text/plain");
            response.set("Server", "HelloWorld/1.0 (Simple 4.0)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);

//            StringBuffer sb = new StringBuffer();
//
//            sb.append("\n ACCEPT: ");
//            sb.append(accept);
//            sb.append("\n NAME: ");
//            sb.append(name);
//            sb.append("\n Target: ");
//            sb.append(request.getTarget()); // "/index.html?a=b&c=d&e=f&g=h&a=1");
//            sb.append("\n Method: ");
//            sb.append(request.getMethod()); // "POST");
//            sb.append("\n Major: ");
//            sb.append(request.getMajor());// 1);
//            sb.append("\n Minor: ");
//            sb.append(request.getMinor());// 0);
//            sb.append("\n Host: ");
//            sb.append(request.getValue("Host")); // "some.host.com");
//            sb.append("\n Accept: ");
//
//            for (int i = 0; i < allAccept.size(); i++) {
//                sb.append(allAccept.get(i));
//                sb.append(" ");
//            }

//            body.println(sb);
            body.close();

//            System.out.println(dd++);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
