/**
 * 
 */
package old;

import javassist.ClassPool;
import javassist.CtClass;
import noc.frame.Store;

public class StoreFactory extends Factory {

	Class<?> clazz;

	public StoreFactory(String name) {
		try {
			name = "";
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(name);

			clazz = cc.toClass();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override public <T> Store<T> getStore(Class<T> clazz) {
		// return (Store<T>) new MemStore();
		return null;
	}

	@Override public <T> TimeSequenceStore<T> getTimeSequenceStore(Class<T> clazz) {
		return null;
	}
}