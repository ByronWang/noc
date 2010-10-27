package noc.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NocDataServlet
 */
public class NocDebugServlet extends NocDataServlet {
	private static final long serialVersionUID = 1L;

	@Override public void init() throws ServletException {
		super.init();
		//ObejctHelper.show(this.getServletContext());
	}

	protected String getPath(HttpServletRequest request){
		return request.getPathInfo();
	}

		
	@Override void processTemplate(Template template, Object data,
			HttpServletResponse response) throws UnsupportedEncodingException, TemplateException, IOException {
		response.getOutputStream().print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");				
		response.getOutputStream().print("<pre>");				
		response.getOutputStream().write(template.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").getBytes("UTF-8"));
		response.getOutputStream().print("</pre></body></html>");
	}
}
