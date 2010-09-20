package noc.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import noc.frame.Store;
import noc.lang.reflect.Type;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TypeTemplateLoader implements TemplateLoader {
	private final static Log log = LogFactory.getLog(TypeTemplateLoader.class);

	final Store<Type> typeStore;
	final String path;
	final String workPath;
	final File templateWorkFolder;

	final Configuration preEngine;
	final ServletContext context;

	TypeTemplateLoader(ServletContext context, Store<Type> typeStore, String path, String workPath) {

		try {
			this.context = context;
			this.typeStore = typeStore;
			this.path = context.getRealPath(path);

			preEngine = new Configuration();
			preEngine.setTemplateUpdateDelay(0);
			preEngine.setDirectoryForTemplateLoading(new File(this.path));

			this.workPath = context.getRealPath(workPath);
			templateWorkFolder = new File(this.workPath);
			templateWorkFolder.delete();
			templateWorkFolder.mkdir();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override public void closeTemplateSource(Object templateSource) throws IOException {
	// Do nothing
	}

	@Override public Object findTemplateSource(String name) throws IOException {

		try {
			log.debug("findTemplateSource: " + name);

			int pos = name.indexOf('_');
			int pos2 = name.indexOf('_', pos + 1);
			if (pos2 > 0)
				return null;

			String typeName = name.substring(0, pos);
			String predefineName = name.substring(pos + 1);

			Template template = preEngine.getTemplate(predefineName);

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("type", typeStore.get(typeName));
			root.put("contextPath", context.getContextPath());

			File f = new File(templateWorkFolder, name);
			FileWriter fw = new FileWriter(f);
			template.process(root, fw);
			fw.close();

			return f;
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}

	}

	@Override public long getLastModified(Object templateSource) {
		return System.currentTimeMillis();
	}

	@Override public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new InputStreamReader(new FileInputStream((File) templateSource), encoding);
	}

}
