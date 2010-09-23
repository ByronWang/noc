package noc.http;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class TypeTemplateLoader extends FileTemplateLoader implements
		TemplateLoader {
	private final static Log log = LogFactory.getLog(TypeTemplateLoader.class);

	final Store<String,Type> typeStore;
	final File templateFolder;
	final File templateWorkFolder;

	final Configuration preEngine;
	final TemplateLoader preTemplateLoad;
	final ServletContext context;

	TypeTemplateLoader(ServletContext context, Store<String,Type> typeStore,
			File templateFolder, File workFolder) throws IOException {
		super(workFolder);
		try {
			this.context = context;
			this.typeStore = typeStore;
			this.templateFolder = templateFolder;
			this.templateWorkFolder = workFolder;
			
			preEngine = new Configuration();
			preEngine.setTemplateUpdateDelay(1);
			preEngine.setDirectoryForTemplateLoading(templateFolder);
			preEngine
					.setSharedVariable("contextPath", context.getContextPath());
			preTemplateLoad = preEngine.getTemplateLoader();

		} catch (TemplateModelException e) {
			throw new RuntimeException(e);
		}
	}

	private static class TypeTemplateSource {
		private final String precompilerName;
		private final String name;
		private final String typeName;
		private final Object precompilerSource;

		TypeTemplateSource(String name, String precompileName, String typeName,
				Object precompilerSource) {
			if (name == null) {
				throw new IllegalArgumentException("name == null");
			}
			if (precompilerSource == null) {
				throw new IllegalArgumentException("source == null");
			}
			if (precompileName == null) {
				throw new IllegalArgumentException("precompileName == null");
			}
			this.name = name;
			this.precompilerSource = precompilerSource;
			this.precompilerName = precompileName;
			this.typeName = typeName;
		}

		public boolean equals(Object obj) {
			if (obj instanceof TypeTemplateSource) {
				return name.equals(((TypeTemplateSource) obj).name);
			}
			return false;
		}

		public int hashCode() {
			return name.hashCode();
		}
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		log.debug("START findTemplateSource: " + name);

		int pos = name.indexOf('_');
		int pos2 = name.indexOf('_', pos + 1);
		if (pos2 > 0)
			return null;

		String typeName = name.substring(0, pos);
		String precompileName = name.substring(pos + 1);

		log
				.debug("START preTemplateLoad.findTemplateSource: "
						+ precompileName);
		Object o = preTemplateLoad.findTemplateSource(precompileName);
		log.debug("FINISH preTemplateLoad.findTemplateSource: "
				+ o.getClass().getName());
		return new TypeTemplateSource(name, precompileName, typeName, o);
	}

	@Override
	public long getLastModified(Object templateSource) {
		return preTemplateLoad
				.getLastModified(((TypeTemplateSource) templateSource).precompilerSource);
	}

	@Override
	public Reader getReader(Object templateSource, String encoding)
			throws IOException {

		TypeTemplateSource source = (TypeTemplateSource) templateSource;

		try {
			log.debug("before getTemplate : " + source.precompilerName);
			Template preTemplate = preEngine
					.getTemplate(source.precompilerName);

			log.debug("START create Template : " + source.name);

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("type", typeStore.readData(source.typeName));

			File f = new File(templateWorkFolder, source.name);
			FileWriter fw = new FileWriter(f);
			preTemplate.process(root, fw);
			fw.close();

			log.debug("FINISH create Template : " + source.name);

			Object s = super.findTemplateSource(source.name);
			return super.getReader(s, encoding);

		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
}
