package noc.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.freemarker.DefaultModel;
import noc.freemarker.FlexModel;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;
import util.PrintObejct;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NocDataServlet
 */
public class NocDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected Fact fact;

	@Override public void init() throws ServletException {
		super.init();
		fact = (Fact) this.getServletContext().getAttribute("fact");
		PrintObejct.print(this.getServletContext());

		System.out.println("IN__" + this.getClass().getName());
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NocDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");

			PrintObejct.print(request);

			String path = request.getServletPath() + request.getPathInfo();

			int last = path.lastIndexOf('/');
			String typeName = path.substring(1, last).replace('/', '.');

			if (last + 1 == path.length()) { // Path
				if ("new".equalsIgnoreCase(request.getQueryString())) {
					Template template = fact.getDataTemplate(typeName);
					if (template == null) {
						response.sendError(400);
						return;
					}
					doGetNewObejctHtml(path, template, typeName, "", request, response);
				} else if ("menu".equalsIgnoreCase(request.getQueryString())) {
					Template template = fact.getTemplate(typeName, "menu");
					if (template == null) {
						response.sendError(400);
						return;
					}
					doGetTypeHtml(path, template, typeName, request, response);
				} else if ("popup".equalsIgnoreCase(request.getQueryString())) {
					Template template = fact.getTemplate(typeName, "popup");
					if (template == null) {
						response.sendError(400);
						return;
					}
					doGetTypeHtml(path, template, typeName, request, response);
				} else {
					Template template = fact.getListTemplate(typeName);
					if (template == null) {
						response.sendError(400);
						return;
					}
					doGetTypeHtml(path, template, typeName, request, response);
				}
			} else {// Object
				Template template = fact.getDataTemplate(typeName);
				if (template == null) {
					response.sendError(400);
					return;
				}
				doGetObejctHtml(path, template, typeName, path.substring(last + 1), request, response);
			}

			PrintObejct.print(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doGetTypeHtml(String urlPath, Template temp, String typename, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Store<?> store = fact.getStore(typename);
		List<?> list = store.list();

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("list", list);
		root.put("urlPath", urlPath);
		root.put("_path_new", request.getContextPath() + urlPath + "?mode=new");
		temp.process(root, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		response.getOutputStream().flush();
	}

	protected void doGetNewObejctHtml(String urlPath, Template temp, String typename, String key,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("data", new DefaultModel(key));

		root.put("_action", request.getContextPath() + urlPath);
		root.put("_method", "POST");

		temp.process(root, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		response.getOutputStream().flush();
	}

	protected void doGetObejctHtml(String urlPath, Template temp, String typename, String key,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			TemplateException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		Map<String, Object> root = new HashMap<String, Object>();
		Store<?> store = fact.getStore(typename);
		Object v = store.get(key);
		if (v != null) {
			root.put("data", v);
		} else {
			root.put("data", new FlexModel(key));

		}

		root.put("_action", request.getContextPath() + urlPath);
		root.put("_method", "POST");
		temp.process(root, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		response.getOutputStream().flush();
	}

	@SuppressWarnings("unchecked") @Override protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			PrintObejct.print(request);

			String path = request.getServletPath() + request.getPathInfo();

			int last = path.lastIndexOf('/');
			String typeName = path.substring(1, last).replace('/', '.');

			Template template = fact.getDataTemplate(typeName);
			if (template == null) {
				response.sendError(400);
				return;
			}

			response.setContentType("text/html; charset=UTF-8");
			Store<Vo> store = (Store<Vo>) fact.getStore(typeName);

			String key = path.substring(last + 1);
			Vo v = store.get(key);
			putAll(request.getParameterMap(), v, fact.getTypeStore().get(typeName));

			v = store.put(v);

			String toPath = request.getContextPath() + path.substring(0, last) + "/" + URLEncoder.encode(key, "UTF-8");
			System.out.println("Redrectto > " + toPath);
			response.sendRedirect(toPath);

			PrintObejct.print(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked") protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			PrintObejct.print(request);

			String path = request.getServletPath() + request.getPathInfo();

			int last = path.lastIndexOf('/');
			String typeName = path.substring(1, last).replace('/', '.');

			if (last + 1 == path.length()) { // Path
				response.setContentType("text/html; charset=UTF-8");
				Store<Vo> store = (Store<Vo>) fact.getStore(typeName);
				Type type = fact.getTypeStore().get(typeName);

				Vo v = new VOImp();
				putAll(request.getParameterMap(), v, type);
				v = store.put(v);

				String toPath = request.getContextPath() + path
						+ URLEncoder.encode(v.S(type.getPrimaryKeyField().getName()), "UTF-8");

				System.out.println("Redrectto > " + toPath);
				response.sendRedirect(toPath);

				System.out.println("Redrectto > " + request.getContextPath() + path);
				PrintObejct.print(response);
			} else {
				doPut(request, response);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void putAll(Map<?, ?> params, Vo v, Type type) {

		for (Field f : type.getFields()) {
			if (f.isArray()) {
				// TODO
			} else if (f.getType().isScala()) {
				String key = f.getName();
				if (params.containsKey(key)) {
					String value = ((String[]) params.get(key))[0];
					if (f.isKey() && v.S(key) != null) {
						if (value.equals(v.S(key))) {
							throw new RuntimeException("Primary key not match");
						}
					} else {
						v.put(key, value);
					}
				}
			} else if (f.isInline()) {
				// TODO fs.add(f.getName() + "_" +
				// f.getType().getKeyField().getName());
			} else if (f.isRefer()) {
				if (f.getType().getPrimaryKeyField() != null) {
					String key = f.getName() + "_" + f.getType().getPrimaryKeyField().getName();
					if (params.containsKey(key)) {
						String[] va = (String[]) params.get(key);
						v.put(key, va[0]);
					}
				} else {
					String key = f.getName() + "_" + "key";
					if (params.containsKey(key)) {
						String[] va = (String[]) params.get(key);
						v.put(key, va[0]);
					}
				}
			}
		}

		if (type.getKeyFields().size() > 1) {
			String primaryKeyNewValue = "";

			String primaryKeyName = type.getPrimaryKeyField().getName();
			String primaryKeyValue = type.getPrimaryKeyField().getName();

			for (Field f : type.getKeyFields()) {
				if (params.containsKey(f.getName())) {
					throw new RuntimeException("Not key field value");
				}
				primaryKeyNewValue += "_" + ((String[]) params.get(f.getName()))[0];
			}
			primaryKeyNewValue = primaryKeyNewValue.substring(1);

			if (params.containsKey(primaryKeyName)
					&& !(primaryKeyValue = ((String[]) params.get(primaryKeyName))[0]).equals("")) {
				if ( primaryKeyValue.equals(primaryKeyNewValue)) {
					throw new RuntimeException("Primary key not match");
				}
			} else {
				v.put(primaryKeyName, primaryKeyNewValue);
			}

		}
	}

}
