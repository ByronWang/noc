package noc.reflect;

import java.io.IOException;

import javassist.CannotCompileException;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

public class TestCatalogType {
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			CannotCompileException {

		TypeReadonlyStore store = new TypeReadonlyStore();
		//store.loadFolder("..\\noc_Biz\\bin");
		//
		store.readData("data.AttrRuler");

		for (Type type : store.list()) {
			System.out.println(type.getName());
			for (Field f : type.getFields()) {
				if (f.getRefer() == Field.Inline) {
					System.out.println("[Inline] " + f.getDisplayName() + " : "
							+ f.getType().getName());
				} else {
					System.out.println("  " + f.getDisplayName() + " : " + f.getType().getName());

				}
			}
		}

	}
}
