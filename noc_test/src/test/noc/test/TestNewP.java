package noc.test;
import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DerbyConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.frame.vostore.VoPersisiterStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypePersister;

public class TestNewP {
	public static void main(String[] args) {
		try {

			String definePath = "d:\\JavaDev\\noc_Define\\bin";
			String bizPath = "d:\\JavaDev\\noc_Biz\\bin";

			TypePersister typeStore = new TypePersister();
			typeStore.loadFolder(definePath);
			typeStore.loadFolder(bizPath);
			Type de =typeStore.get("data.master.Employee");


			DerbyConfiguration conf = new DerbyConfiguration("testdb","user","password");
			conf.init();
			Persister<Vo> p = conf.getPersister(Vo.class, de);
			p.prepare();			
			Store<Vo> store = new VoPersisiterStore(de, p);
			
			
			Vo v = new VOImp();
			v.put("工号","工号");
			v.put("名称","名称");
			v.put("英文名","英文名");
			v.put("性别","性别");
			v.put("公司","公司");
			v.put("部门","部门");
			v.put("职务","职务");
			v.put("入职日期","入职日期");
			v.put("离职日期","离职日期");
			v.put("成本中心","成本中心");

			v = store.update(v);
			
			
			v = store.get("工号");
			
			System.out.println(v);
			
		
			conf.destroy();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
