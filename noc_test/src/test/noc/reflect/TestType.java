package noc.reflect;

import java.io.IOException;

import javassist.CannotCompileException;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypePersister;

public class TestType {
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			CannotCompileException {

		TypePersister store = new TypePersister();
		// store.loadFolder("..\\noc_Biz\\bin");
		//
		// store.get("data.sales.Order");

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
		for (Type type : store.listScala()) {
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
