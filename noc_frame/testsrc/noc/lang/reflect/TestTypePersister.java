package noc.lang.reflect;

import junit.framework.TestCase;

public class TestTypePersister extends TestCase {

	public void testLoad() {

		TypePersister store = new TypePersister();
		store.loadFolder("..\\noc_Biz\\bin");
		//
		// store.get("data.sales.Order");

		for (Type type : store.listScala()) {
			System.out.println(type.getName());
			for (Field f : type.getFields()) {
				if (f.isInline()) {
					System.out.println("[Inline] " + f.getDisplayName() + " : " + f.getType().getName());
				} else {
					System.out.println("  " + f.getDisplayName() + " : " + f.getType().getName());

				}
			}
		}

		for (Type type : store.list()) {
			System.out.println(type.getName());
			for (Field f : type.getFields()) {
				if (f.isInline()) {
					System.out.println("[Inline] " + f.getDisplayName() + " : " + f.getType().getName());
				} else {
					System.out.println("  " + f.getDisplayName() + " : " + f.getType().getName());

				}
			}
		}

	}
}
