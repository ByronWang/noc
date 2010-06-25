package noc.run;

import java.util.List;

import noc.frame.FacadeProvider;
import noc.frame.FreeVo;
import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.Vo;
import noc.frame.persister.LocalFileSystemPersisterProvider;
import noc.frame.provider.MemoryStoreProvider;
import noc.frame.provider.PersisterStoreProvider;

public class Startup {
	public static void main(String[] args) {

		FacadeProvider<Persister<Vo>> facadeP = new FacadeProvider<Persister<Vo>>();

		facadeP.register(".*", new LocalFileSystemPersisterProvider("d:\\"));
		// this.register(".*", new DerbyPersisterProvider("TestDb001"));

		FacadeProvider<Store<Vo>> storeProvider = new FacadeProvider<Store<Vo>>();

		storeProvider.register(".*", new MemoryStoreProvider());
		storeProvider.register(".*", new PersisterStoreProvider(facadeP));

		
		storeProvider.setup();

		Store<Vo> personStore = storeProvider.get("Person");

		Vo vo = new FreeVo();
		vo.put("name", "wangshilian");
		vo.put("nakename", "nakename");
		vo.put("de", "de");
		// freeStore.update(vo);

		// vo = freeStore.get("wangshilian");
		//
		// System.out.println(vo.get("name"));
		// System.out.println(vo.get("nakename"));
		// System.out.println(vo.get("de"));

		// personStore.update(vo);

		List<Vo> voList = personStore.list();
		for (Vo v : voList) {
			System.out.println(v);
		}

		System.out.println(personStore);

		storeProvider.cleanup();
	}
}
