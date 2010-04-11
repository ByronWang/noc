package noc.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DbConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.VoPersisiterStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypePersister;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Fact {

	final String DEFINE_PATH = "define_path";
	final String BIZ_PATH = "biz_path";

	final String DATABASE_NAME = "database_name";
	final String USER_NAME = "user_name";
	final String USER_PASSWORD = "user_password";

	final String TEMPLATE_PATH = "template_path";
	final String TEMPLATE_WORK_PATH = "template_work_path";

	private TypePersister typeStore;
	private Configuration templateEngine;

	private DbConfiguration dbEngine;
	private Map<String, Store<?>> stores;
	// Extension
	final static String TEMPLATE_EXTENSION = ".ftl";

	public Fact(ServletContext context) {

		try {

			typeStore = new TypePersister(context.getRealPath("WEB-INF/lib"));
			// typeStore.appendClassPath(BIZ_PATH)
			typeStore.loadFolder(context.getInitParameter(DEFINE_PATH));
			typeStore.loadFolder(context.getInitParameter(BIZ_PATH));

			stores = new HashMap<String, Store<?>>();

			stores.put(Type.class.getName(), typeStore);

			TemplateLoader[] loaders = new TemplateLoader[] {
					new WebappTemplateLoader(context, context.getInitParameter(TEMPLATE_PATH)),
					// new WebappTemplateLoader(context,
					// context.getInitParameter(TEMPLATE_WORK_PATH)),
					new TypeTemplateLoader(context, typeStore, context
							.getInitParameter(TEMPLATE_PATH), context
							.getInitParameter(TEMPLATE_WORK_PATH)) };
			
			/* Create and adjust the configuration */
			templateEngine = new Configuration();
			templateEngine.setTemplateUpdateDelay(0);			
			templateEngine.setTemplateLoader(new MultiTemplateLoader(loaders));
			templateEngine.setSharedVariable("contextPath", context.getContextPath());
			templateEngine.setObjectWrapper(new DefaultObjectWrapper());

			dbEngine = new DbConfiguration(context.getInitParameter(DATABASE_NAME), context
					.getInitParameter(USER_NAME), context.getInitParameter(USER_PASSWORD));
			dbEngine.init();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Template getDataTemplate(String typeName) {
		return getTemplate(typeName, "type");
	}

	public Template getListTemplate(String typeName) {
		return getTemplate(typeName, "list");
	}

	public Template getTemplate(String typeName, String name) {
		try {
			String workTemplateName = typeName + "_" + name + TEMPLATE_EXTENSION;
			return templateEngine.getTemplate(workTemplateName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public Store<?> getStore(String typeName) {
		Store<?> store = stores.get(typeName);
		if (store == null) {

			Type de = typeStore.get(typeName);

			Persister<Vo> p = dbEngine.getPersister(Vo.class, de);
			p.prepare();
			stores.put(typeName, new VoPersisiterStore(de, p));
			store = stores.get(typeName);

			// prepareData(store, de, "sample");
		}
		return store;
	}

	public Store<Type> getTypeStore() {
		return this.typeStore;
	}

	// private void prepareData(Store<Vo> store, Type type, String key) {
	// Vo v = new VOImp();
	// for (Field f : type.getFields()) {
	// if (f.getType().isScala()) {
	// v.put(f.getName(), f.getName() + "+");
	// }
	// }
	//
	// v.put(type.getKeyField().getName(), key);
	//
	// v = store.put(v);
	// }

	public void destroy() {
		dbEngine.destroy();
	}
}
