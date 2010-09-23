package noc.test;

import junit.framework.TestCase;
import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DerbyConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

public class TestNewP extends TestCase{
	public void testDB() {
		try {

			String definePath = "d:\\JavaDev\\noc_Define\\bin";
			String bizPath = "d:\\JavaDev\\noc_Biz\\bin";

			TypeReadonlyStore typeStore = new TypeReadonlyStore();
			typeStore.setUp();			
			typeStore.loadFolder(definePath);
			typeStore.loadFolder(bizPath);
			Type de =typeStore.readData("data.master.Employee");


			DerbyConfiguration conf = new DerbyConfiguration("testdb","user","password");
			conf.init();
			
			Persister<String,Vo> p = conf.getPersister(Vo.class, de);
			p.setUp();			
			
			Store<String,Vo> store = new VoPersistableStore(null,de, p);
			store.setUp();
			
			
			Vo v =  store.borrowData(null);
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
			v = store.returnData(v.getIndentify(), v);
			
			
			v = store.readData("工号");
			
			System.out.println(v);
			
		
			conf.destroy();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
