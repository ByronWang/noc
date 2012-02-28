package httpd.server;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import httpd.engine.DataResourceEngine;
import httpd.engine.DynamicResourceEngine;
import httpd.engine.PresentationResourceEngine;
import httpd.engine.ResourceEngine;
import httpd.engine.RestHttpContainer;
import httpd.engine.StaticResourceEngine;
import httpd.io.ClassPathLoader;
import httpd.io.FileSystemLoader;
import httpd.io.Loader;
import httpd.io.MultiLoader;
import httpd.io.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Properties;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DbConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.DataCenter;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

import org.simpleframework.http.core.Container;

import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

    static final String APP_DEFINE_PATH = "app_define_path";
    static final String DB_DRIVERCLASS = "db_driverclass";
    static final String DB_URL = "db_url";
    static final String DB_USERNAME = "db_username";
    static final String DB_PASSWORD = "db_password";

    @Override
    protected void configure() {
        try {
            // app home
            String appHome = "htdocs";

            // home folder
            File home = new File(appHome);
            this.bind(File.class).toInstance(home);
                        
            // loader
            final Loader loader = new MultiLoader(new FileSystemLoader(home), new ClassPathLoader(
                    ResourceEngine.class.getClassLoader(), appHome));
            this.bind(Loader.class).toInstance(loader);

            // Properties
            Properties props = new Properties();
            props.load(loader.findSource("/WEB-INF/web.properties").getInputStream());
            this.bind(Properties.class).toInstance(props);

            // typeStore
            TypeReadonlyStore typeStore;
            typeStore = new TypeReadonlyStore();
            typeStore.setUp();

            // load jar config
            String definePath = props.getProperty(APP_DEFINE_PATH);
            if (definePath.charAt(1) == ':' || definePath.charAt(0) == '/') { // absolute path
                typeStore.load(new File(props.getProperty(APP_DEFINE_PATH)));
            } else {
                typeStore.load(new File(appHome, props.getProperty(APP_DEFINE_PATH)));
            }

            //Store<String, Type>
            this.bind(TypeReadonlyStore.class).toInstance(typeStore);

            // template engine
            Configuration templateEngine = new Configuration();
            templateEngine.setEncoding(Locale.CHINA, "UTF-8");
            templateEngine.setTemplateUpdateDelay(10);
            templateEngine.setTemplateLoader(new TemplateLoader() {
                @Override
                public Object findTemplateSource(String name) throws IOException {
                    return loader.findSource("/template/" + name);
                }

                @Override
                public Reader getReader(Object templateSource, String encoding) throws IOException {
                    return new InputStreamReader(((Source) templateSource).getInputStream(), encoding);
                }

                @Override
                public long getLastModified(Object templateSource) {
                    return ((Source) templateSource).getLastModified();
                }

                @Override
                public void closeTemplateSource(Object templateSource) throws IOException {
                    ((Source) templateSource).close();
                }
            });

//            templateEngine.setDirectoryForTemplateLoading(new File(
//                    "D:\\noc\\noc-engine\\target\\classes\\htdocs"));
            this.bind(Configuration.class).toInstance(templateEngine);

            // data center
            final DbConfiguration dbEngine = DbConfiguration.getEngine(props.getProperty(DB_DRIVERCLASS),
                    props.getProperty(DB_URL), props.getProperty(DB_USERNAME), props.getProperty(DB_PASSWORD));
            DataCenter storeEngine = new DataCenter(typeStore) {
                @Override
                protected Store<String, ?> find(String typeName) {
                    Type type = types.readData(typeName);
                    Persister<String, Vo> p = dbEngine.getPersister(Vo.class, type);
                    p.setUp();
                    Store<String, ?> store = new VoPersistableStore(this, type, p);
                    store.setUp();
                    return store;
                }
            };
            this.bind(DataCenter.class).toInstance(storeEngine);

            this.bind(PresentationResourceEngine.class);//.to(PresentationResourceEngine.class);
            this.bind(DataResourceEngine.class);//.to(DataResourceEngine.class);
            this.bind(StaticResourceEngine.class);//.to(StaticResourceEngine.class);
            this.bind(DynamicResourceEngine.class);//.to(DynamicResourceEngine.class);
            this.bind(ResourceEngine.class);//.to(ResourceEngine.class);
            this.bind(Container.class).to(RestHttpContainer.class);//.to(ResourceContainer.class);
            
            //this.bind(Engine.class).annotatedWith(Names.named("Impl1Test")).to(ResourceEngine.class);
            //Names.named("Impl1Test")
            //@Named("Checkout")

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
