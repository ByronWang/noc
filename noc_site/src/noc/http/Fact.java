package noc.http;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import noc.frame.Findable;
import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DerbyConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.DataCenterConfiguration;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Fact extends Findable<String,Fact.Rule>{

	static final String DEFINE_PATH = "define_path";
	static final String BIZ_PATH = "biz_path";

	static final String DATABASE_NAME = "db_name";
	static final String USER_NAME = "db_username";
	static final String USER_PASSWORD = "db_password";
	
	static final String DEBUG_MODE = "debug";

	static final String TEMPLATE_PATH = "template_path";
	static final String TEMPLATE_WORK_PATH = "template_work_path";

	private TypeReadonlyStore typeStore;
	private Configuration templateEngine;
	private DataCenterConfiguration stores;
	private DerbyConfiguration dbEngine;
	// Extension
	final static String TEMPLATE_EXTENSION = ".ftl";
	final boolean debugMode;

	public Fact(ServletContext context, boolean debugMode) {
		this.debugMode =  "true".equals(context.getInitParameter(DEBUG_MODE));
		try {

			typeStore = new TypeReadonlyStore();
			typeStore.appendClassPath(context.getRealPath("WEB-INF/lib/noc_define.jar"));
			typeStore.appendClassPath(context.getRealPath("WEB-INF/lib/noc_frame.jar"));
			typeStore.setUp();
			typeStore.loadJar(context.getRealPath(context.getInitParameter(DEFINE_PATH)));
			typeStore.loadJar(context.getRealPath(context.getInitParameter(BIZ_PATH)));

			stores = new DataCenterConfiguration(typeStore){
				@Override
				protected Store<String,?> find(String typeName) {
					Type type = types.readData(typeName);
					Persister<String,Vo> p = dbEngine.getPersister(Vo.class, type);
					p.setUp();
					Store<String,?> store = new VoPersistableStore(this,type, p);
					store.setUp();
					return store;
				}
			};

			File tempateFolder = new File(context.getRealPath(context.getInitParameter(TEMPLATE_PATH)));
			File templateWorkFolder = new File(context.getRealPath(context.getInitParameter(TEMPLATE_WORK_PATH)));

			if (templateWorkFolder.exists())
				templateWorkFolder.delete();
			templateWorkFolder.mkdir();

			TemplateLoader[] loaders = new TemplateLoader[] {
					new WebappTemplateLoader(context, context.getInitParameter(TEMPLATE_PATH)),
					new TypeTemplateLoader(context, typeStore, tempateFolder, templateWorkFolder) };

			/* Create and adjust the configuration */
			templateEngine = new Configuration();
			templateEngine.setTemplateUpdateDelay(10);
			templateEngine.setTemplateLoader(new MultiTemplateLoader(loaders));
			templateEngine.setSharedVariable("contextPath", context.getContextPath());
			templateEngine.setObjectWrapper(new DefaultObjectWrapper());

			dbEngine = new DerbyConfiguration(context.getInitParameter(DATABASE_NAME), context
					.getInitParameter(USER_NAME), context.getInitParameter(USER_PASSWORD));
			dbEngine.init();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Template getTemplate(String typeName, String name) {
		try {
			String workTemplateName = typeName + "_" + name + TEMPLATE_EXTENSION;
			return templateEngine.getTemplate(workTemplateName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private Store<String,?> getStore(String typeName) {
		return stores.get(typeName);
	}

	public class Rule {
		protected String typeName;
		private Type type;
		private Store<String,?> store;
		private Template listTemplate;
		private Template editTemplate;
		private Template newTemplate;
		private Template menuTemplate;
		private Template popupTemplate;

		private Rule(String typeName, Type type, Store<String,?> store, Template listTemplate, Template editTemplate,
				Template newTemplate, Template menuTemplate, Template popupTemplate) {
			super();
			this.typeName = typeName;
			this.type = type;
			this.store = store;
			this.listTemplate = listTemplate;
			this.editTemplate = editTemplate;
			this.newTemplate = newTemplate;
			this.menuTemplate = menuTemplate;
			this.popupTemplate = popupTemplate;
		}

		public Type getType() {
			return type;
		}

		public Store<String,?> getStore() {
			return store;
		}

		public Template getListTemplate() {
			return listTemplate;
		}

		public Template getEditTemplate() {
			return editTemplate;
		}

		public Template getNewTemplate() {
			return newTemplate;
		}

		public Template getMenuTemplate() {
			return menuTemplate;
		}

		public Template getPopupTemplate() {
			return popupTemplate;
		}
	}

	public class DebugRule extends Rule {
		final Fact fact;

		private DebugRule(Fact fact, String typeName) {
			super(typeName, null, null, null, null, null, null, null);
			this.fact = fact;
		}

		public Type getType() {
			return typeStore.readData(typeName);
		}

		public Store<String,?> getStore() {
			return fact.getStore(typeName);
		}

		public Template getListTemplate() {
			return fact.getTemplate(typeName, "list");
		}

		public Template getEditTemplate() {
			return fact.getTemplate(typeName, "edit");
		}

		public Template getNewTemplate() {
			return fact.getTemplate(typeName, "edit");
		}

		public Template getMenuTemplate() {
			return fact.getTemplate(typeName, "menu");
		}

		public Template getPopupTemplate() {
			return fact.getTemplate(typeName, "popup");
		}
	}

	public void destroy() {
		dbEngine.destroy();
	}

	@Override
	protected Rule find(String typeName) {
		Rule rule = null;
		
		if (debugMode) {
			rule = new DebugRule(this, typeName);
		} else {
			rule = new Rule(typeName, 
					typeStore.readData(typeName), 
					getStore(typeName), 
					getTemplate(typeName, "list"),
					getTemplate(typeName, "edit"), 
					getTemplate(typeName, "edit"), 
					getTemplate(typeName, "menu"),
					getTemplate(typeName, "popup"));
		}
		return rule;
	}
}
