package noc.run;

import java.util.List;

import noc.frame.FreeVo;
import noc.frame.Provider;
import noc.frame.Store;
import noc.frame.Vo;

public class Startup {
	public static void main(String[] args) {
		Provider<Store<Vo>> storeProvider= new FacadeStoreProvider<Vo>();
		storeProvider.setup();

		Store<Vo> personStore = storeProvider.get("Person");
		
		Vo vo = new FreeVo();
		vo.put("name", "wangshilian");	
		vo.put("nakename", "nakename");	
		vo.put("de", "de");		
		//freeStore.update(vo);
		
		//vo = freeStore.get("wangshilian");
//
//		System.out.println(vo.get("name"));
//		System.out.println(vo.get("nakename"));
//		System.out.println(vo.get("de"));
		
//		personStore.update(vo);
		
		List<Vo> voList =  personStore.list();
		for(Vo v : voList){
			System.out.println(v);
		}
		
		System.out.println(personStore);
				
		storeProvider.cleanup();	
	}
}
