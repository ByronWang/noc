package noc.reflect;

import java.io.IOException;

import javassist.CannotCompileException;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypePersister;

public class TestCatalogType {
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			CannotCompileException {

		TypePersister store = new TypePersister();
		//store.loadFolder("..\\noc_Biz\\bin");
		//
		store.get("data.AttrRuler");

		for (Type type : store.list()) {
			System.out.println(type.getName());
			for (Field f : type.getFields()) {
				if (f.isInline()) {
					System.out.println("[Inline] " + f.getDisplayName() + " : "
							+ f.getType().getName());
				} else {
					System.out.println("  " + f.getDisplayName() + " : " + f.getType().getName());

				}
			}
		}

	}
}
