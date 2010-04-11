package noc.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import noc.frame.vo.Vo;
import noc.freemarker.FlexModel;
import noc.lang.reflect.Type;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NocDataServlet
 */
public class NocTypeServlet extends NocDataServlet {
	private static final long serialVersionUID = 1L;

	Map<String, Type> types = new HashMap<String, Type>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NocTypeServlet() {
		super();
	}

	@Override protected void doGetTypeHtml(String urlPath, Template temp, String typename,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		List<Type> list = fact.getTypeStore().list();

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("list", list);
		root.put("_action", request.getContextPath() + "/" + urlPath);
		root.put("_method", "POST");
		temp.process(root, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		response.getOutputStream().flush();
	}

	@Override protected void doGetObejctHtml(String urlPath, Template temp, String typename,
			String key, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		Map<String, Object> root = new HashMap<String, Object>();
		Type v = fact.getTypeStore().get(key);
		if (v != null) {
			root.put("data", v);
		} else {
			root.put("data", new FlexModel(key));

		}
		root.put("urlPath", urlPath);
		temp.process(root, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		response.getOutputStream().flush();
	}

	public void putAll(Map<String, Object> params, Vo values) {
		for (String key : values.keys()) {
			if (params.containsKey(key)) {
				String[] va = (String[]) params.get(key);
				values.put(key, va[0]);

			}
		}
	}
}
