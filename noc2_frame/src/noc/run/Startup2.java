package noc.run;

import java.io.File;

import noc.frame.Vo;
import noc.frame.persister.CsvFilePersister;
import noc.frame.store.PersistableStore;

public class Startup2 {
	public static void main(String[] args) {

		PersistableStore s = new PersistableStore();
		CsvFilePersister p = new CsvFilePersister(new File("d:\\Person.csv"));

		s.setPersistre(p);
		s.setup();

//		List<Vo> voList = s.list();
//		for (Vo v : voList) {
//			System.out.println(v);
//		}

		Vo v = s.get("wangshilian9");

		System.out.println(v.get("name")  + "@[" + v + "]" );
		
		v.put("nakename", "newvalue");
		
		v = s.update(v);
		
		System.out.println(v.get("nakename")  + "@[" + v + "]" );

		s.cleanup();

	}
}
