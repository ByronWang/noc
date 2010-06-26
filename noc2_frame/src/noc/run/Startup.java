package noc.run;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.lang.Field;
import noc.frame.lang.Type;
import noc.frame.model.FreeVo;
import noc.frame.model.Vo;
import noc.frame.persister.TablePersister;
import noc.frame.provider.DerbyPersisterProvider;
import noc.frame.store.MemoryStore;

public class Startup {
	public static void main(String[] args) {

		Store<Type> types = new MemoryStore<Type>();
		Type stringType = new Type("String", "String", true, true, null);

		Type personType = new Type("Person", "员工", false, false, null);
		personType.getFields().add(new Field("Name", "姓名", stringType, true, false, false, false));
		personType.getFields().add(new Field("Age", "年龄", stringType));
		personType.getFields().add(new Field("Sex", "性别", stringType));
		personType.getFields().add(new Field("Height", "身高", stringType));
		personType.getFields().add(new Field("Fad", "身高", stringType));
		personType.getFields().add(new Field("Weight", "体重", stringType));
		personType.setPrimaryKeyField(personType.getFields().get(0));
		types.update(personType);

		DerbyPersisterProvider dp = new DerbyPersisterProvider(types, "tedsfst1");
		dp.setup();

		Persister<Vo> personPersister = dp.get("Person");
		
		((TablePersister)personPersister).prepare();

		Vo person = new FreeVo();
		person.put("Name", "Wangshilian");

		person = personPersister.update(person);
		
		person = personPersister.get(person.getReferID());
		
		System.out.println(person);

		dp.cleanup();
		
		//		
		//		
		// persisterProvider.register("noc[.]frame[.].*", new
		// LocalFileSystemPersisterProvider("d:\\"));
		// persisterProvider.register(".*", new
		// DerbyPersisterProvider("TestDb001"));
		//
		// FacadeProvider<Store<Vo>> storeProvider = new
		// FacadeProvider<Store<Vo>>();
		//
		// storeProvider.register(".*", new MemoryStoreProvider());
		// storeProvider.register(".*", new
		// PersisterStoreProvider(persisterProvider));
		//
		//		
		// storeProvider.setup();
		//
		// Store<Vo> personStore = storeProvider.get("Person");
		//
		// Vo vo = new FreeVo();
		// vo.put("name", "wangshilian");
		// vo.put("nakename", "nakename");
		// vo.put("de", "de");
		// // freeStore.update(vo);
		//
		// // vo = freeStore.get("wangshilian");
		// //
		// // System.out.println(vo.get("name"));
		// // System.out.println(vo.get("nakename"));
		// // System.out.println(vo.get("de"));
		//
		// // personStore.update(vo);
		//
		// List<Vo> voList = personStore.list();
		// for (Vo v : voList) {
		// System.out.println(v);
		// }
		//
		// System.out.println(personStore);
		//
		// storeProvider.cleanup();
	}
}
