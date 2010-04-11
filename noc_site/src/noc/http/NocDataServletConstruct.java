package noc.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;

/**
 * Servlet implementation class NocDataServlet
 */
public class NocDataServletConstruct extends NocDataServlet {
	private static final long serialVersionUID = 1L;

	@Override public void init() throws ServletException {
		super.init();
		//ObejctHelper.show(this.getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			String path = request.getPathInfo();

			int last = path.lastIndexOf('/');
			String typeName = path.substring(1, last).replace('/', '.');

			if (last + 1 == path.length()) { // Path
				Template template = fact.getListTemplate(typeName);
				if (template == null) {
					response.sendError(400);
					return;
				}

				
				response.getOutputStream().print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");				
				response.getOutputStream().print("<pre>");				
				response.getOutputStream().write(template.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").getBytes("UTF-8"));
				response.getOutputStream().print("</pre></body></html>");
				
				
			} else {// Object
				Template template = fact.getDataTemplate(typeName);
				if (template == null) {
					response.sendError(400);
					return;
				}
				response.getOutputStream().print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");				
				response.getOutputStream().print("<pre>");				
				response.getOutputStream().write(template.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").getBytes("UTF-8"));
				response.getOutputStream().print("</pre></body></html>");
			
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
