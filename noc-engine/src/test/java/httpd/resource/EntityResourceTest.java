package httpd.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DbConfiguration;
import noc.frame.dbpersister.SqlHelper;
import noc.frame.vo.Vo;
import noc.frame.vostore.DataCenterConfiguration;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;
import freemarker.template.Configuration;

public class EntityResourceTest extends TestCase {

    DataCenterConfiguration storeEngine;
    DbConfiguration dbEngine;

    Configuration templateEngine;
    TypeReadonlyStore typeStore;

    String typeName = "kao.master.Product50";
    Store<String, Object> store;
    Type type;

    @SuppressWarnings("unchecked")
    public EntityResourceTest() {

        try {
            // Prepare config
            String appHome = "htdocs";
            Properties props = new Properties();
            URL url = this.getClass().getResource("/" + appHome + "/WEB-INF/web.properties");
            assertNotNull(url);
            InputStream in = url.openStream();
            System.out.println(url.getPath());
            props.load(in);

            // Prepare type
            typeStore = new TypeReadonlyStore();
            typeStore.setUp();
            String definePath = props.getProperty(APP_DEFINE_PATH);
            typeStore.load(definePath);

            type = typeStore.readData(typeName);

            // Prepare persister
            dbEngine = DbConfiguration.getEngine(props.getProperty(DB_DRIVERCLASS), props.getProperty(DB_URL),
                    props.getProperty(DB_USERNAME), props.getProperty(DB_PASSWORD));

            String sqlDrop = new SqlHelper(type).builderDrop();
            try {
                Connection conn = dbEngine.getConntion();
                conn.createStatement().execute(sqlDrop);
                conn.commit();
            } catch (Exception e) {
            }

            // Prepare store engine
            storeEngine = new DataCenterConfiguration(typeStore) {
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
            store = (Store<String, Object>) storeEngine.get(typeName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private Map<String, String> fromString(String params) {
        StringTokenizer st = new StringTokenizer(params, "&=");
        Map<String, String> map = new HashMap<String, String>();
        while (st.hasMoreTokens())
            map.put(st.nextToken(), st.nextToken());
        return map;
    }

    public void testUpdate() {
        Map<String, String> form = fromString("code=codevalue&code02=code02value&code03=code03value&code04=code04value&code05=code05value&code06=code06value&code07=code07value&code08=code08value&code09=code09value&code10=code10value&code11=code11value&code12=code12value&code13=code13value&code14=code14value&code15=code15value&code16=code16value&code17=code17value&code18=code18value&code19=code19value&code20=code20value&code21=code21value&code22=code22value&code23=code23value&code24=code24value&code25=code25value&code26=code26value&code27=code27value&code28=code28value&code29=code29value&code30=code30value&code31=code31value&code32=code32value&code33=code33value&code34=code34value&code35=code35value&code36=code36value&code37=code37value&code38=code38value&code39=code39value&code40=code40value&code41=code41value&code42=code42value&code43=code43value&code44=code44value&code45=code45value&code46=code46value&code47=code47value&code48=code48value&code49=code49value&code50=code50value&code51=code51value&code52=code52value&code53=code53value&code54=code54value&code55=code55value&code56=code56value&code57=code57value&code58=code58value&code59=code59value&code60=code60value&code61=code61value&code62=code62value&code63=code63value&code64=code64value&code65=code65value&code66=code66value&code67=code67value&code68=code68value&code69=code69value&code70=code70value&code71=code71value&code72=code72value&code73=code73value&code74=code74value&code75=code75value&code76=code76value&code77=code77value&code78=code78value&code79=code79value&code80=code80value&code81=code81value&code82=code82value&code83=code83value&code84=code84value&code85=code85value&code86=code86value&code87=code87value&code88=code88value&code89=code89value&code90=code90value&code91=code91value&code92=code92value&code93=code93value&code94=code94value&code95=code95value&code96=code96value&code97=code97value&code98=code98value&code99=code99value&code100=code100value&Submit=submit");
        // Map<String, String> form =
        // fromString("代码=codevalue&名称=code02value&单位=code03value&箱装=code04value&Submit=submit");
        // 代码 名称 单位 箱装
        // form.put("code02", new Date().toString());

        System.out.println("Update START");

        int outloop = 1;
        int loop = 10;
        long start;
        long end;
        long costa;
        long costu;

        for (int j = 0; j < outloop; j++) {
            start = System.currentTimeMillis();
            form.put("code02", "insert at " + new Date().toString());

            for (int i = 0; i < loop; i++) {
                String key = "codevalue" + j + ":" + i;
                Vo dest = (Vo) store.borrowData(null);
                form.put("code", key);
                dest = VoHelper.putAll(form, dest, this.type);
                store.returnData(dest.getIndentify(), dest);
            }
            end = System.currentTimeMillis();
            costa = end - start;

            form.put("code02", "update at " + new Date().toString());

            start = System.currentTimeMillis();
            for (int i = 0; i < loop; i++) {
                String key = "codevalue" + j + ":" + i;
                form.put("code", key);
                Vo dest = (Vo) store.borrowData(key);
                dest = VoHelper.putAll(form, dest, this.type);
                store.returnData(dest.getIndentify(), dest);
            }
            end = System.currentTimeMillis();
            costu = end - start;
            System.out.println((j * loop) + "\t" + costa + "\t" + costu);
        }
    }

    static final String APP_DEFINE_PATH = "app_define_path";

    static final String DB_DRIVERCLASS = "db_driverclass";
    static final String DB_URL = "db_url";
    static final String DB_USERNAME = "db_username";
    static final String DB_PASSWORD = "db_password";
}
