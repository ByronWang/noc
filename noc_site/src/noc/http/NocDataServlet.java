package noc.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.freemarker.DefaultModel;
import noc.http.Fact.Rule;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.PrintObejct;
import util.VoHelper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NocDataServlet
 */
public class NocDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(NocDataServlet.class);

	protected Fact fact;

	// protected final String typeProfix;

	@Override
	public void init() throws ServletException {
		super.init();
		fact = (Fact) this.getServletContext().getAttribute("fact");
	}

	static long count = 0;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		log.info((++count) + "==    START    Request  :  " + request.getPathInfo());
		request.setCharacterEncoding("UTF-8");

		String path = request.getPathInfo();
		log.debug("Path: " + path);
		int last = path.lastIndexOf('/');
		String typeName = path.substring(1, last).replace('/', '.');
		Rule rule = fact.get(typeName);
		String key = path.substring(last + 1);
		log.debug("Key: " + key);
		request.setAttribute("_Rule", rule);
		request.setAttribute("_Key", key);

		if (log.isTraceEnabled()) {
			PrintObejct.print(request);
		}
		super.service(request, response);
		log.info(count + "==    FINISH    Request  :  " + request.getPathInfo());
	}

	// protected Template getTemplate(String typeName, String mode) {
	// return fact.getTemplate(typeName, mode);
	// }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Rule rule = (Rule) request.getAttribute("_Rule");
			String key = (String) request.getAttribute("_Key");
			if (rule == null) {
				response.sendError(400);
				return;
			}

			Template template = null;
			Object data = null;

			if (key.length() == 0) { // Path
				String mode = request.getQueryString();
				log.debug("mode: " + mode);

				if (mode == null) {
					template = rule.getListTemplate();
					data = rule.getStore().list();
				} else {
					if ("new".equalsIgnoreCase(mode)) {
						template = rule.getNewTemplate();
						data = new DefaultModel("");
					} else if ("menu".equalsIgnoreCase(mode)) {
						template = rule.getMenuTemplate();
						data = rule.getStore().list();
					} else if ("popup".equalsIgnoreCase(mode)) {
						template = rule.getPopupTemplate();
						data = rule.getStore().list();
					} else {
						// TODO ERROR
					}
				}
			} else {// Object
				template = rule.getEditTemplate();
				data = rule.getStore().readData(key);
			}

			if (template == null || data == null) {
				response.sendError(400);
				return;
			}

			log.debug("template: " + template.getName());
			log.debug("data: " + data);

			processTemplate(rule.getType(), template, data, request, response);

			if (log.isTraceEnabled()) {
				PrintObejct.print(response);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	void processTemplate(Type type, Template template, Object data, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("data", data);
		root.put("urlPath", "");
		root.put("_path_new", request.getContextPath() + "" + "?mode=new");
		root.put("_action", request.getContextPath() + "");
		root.put("_method", "POST");
		template.process(root, response.getWriter());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("path", "ddd");

		try {

			Rule rule = (Rule) request.getAttribute("_Rule");
			String key = (String) request.getAttribute("_Key");

			Store<String,Vo> store = (Store<String,Vo>) rule.getStore();

			Map<?,?> source = request.getParameterMap();
			
			Vo dest = store.borrowData(key);
			
			VoHelper.putAll(source, dest, rule.getType());
			
			store.returnData(key, dest);

			doGet(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		try {

			Rule rule = (Rule) request.getAttribute("_Rule");
			String key = (String) request.getAttribute("_Key");

			if (key.length() == 0) { // Path

				Store<String,Vo> store = (Store<String,Vo>) rule.getStore();
				Map<?,?> source = request.getParameterMap();
				Vo dest = store.borrowData(null);
				
				dest = VoHelper.putAll(source, dest, rule.getType());
				
				dest = store.returnData(dest.getIndentify(), dest);

				String toPath = request.getContextPath() + "/" + rule.typeName.replace('.', '/') + "/"
						+ URLEncoder.encode(dest.getIndentify(), "UTF-8");

				response.setContentType("text/html; charset=UTF-8");
				response.sendRedirect(toPath);
				log.debug("Redrectto > " + toPath);
			} else {
				doPut(request, response);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
