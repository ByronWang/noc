package noc.lang.reflect;

import junit.framework.TestCase;

public class TestTypeReflect extends TestCase {

	TypePersister store = null;

	@Override protected void setUp() throws Exception {
		super.setUp();
		store = new TypePersister();
	}

	@Override protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLoad() {

		// store.loadFolder("..\\noc_Biz\\bin");
		//
		// store.get("data.sales.Order");

		Type p = store.get("data.TestPerson");

		StringBuffer sb = new StringBuffer();

		final String newline = "\n";
		final String indent = "\t";

		for (Field f : p.getFields()) {
			if (f.array && f.inline) {
				sb.append(f.displayName);
				sb.append(":");
				sb.append("inline list");
				sb.append(newline);
				sb.append(indent);
				for (Field fin : f.type.fields) {
					sb.append(fin.name);
					sb.append(" : ");
				}
				sb.append(newline);
			} else if (f.array && f.refer) {
				sb.append(f.displayName);
				sb.append(":");
				sb.append("list");
				sb.append(newline);
				sb.append(indent);
				sb.append(f.displayName);
				sb.append("_");
				sb.append(f.type.primaryKeyField.name);
				sb.append(newline);
			} else if (f.array) {
				sb.append(f.displayName);
				sb.append(":");
				sb.append("list");
				sb.append(newline);
				sb.append(indent);
				sb.append(f.displayName);
				sb.append(newline);
			} else if (f.inline) {
				sb.append(f.displayName);
				sb.append(":");
				sb.append("inline");
				sb.append(newline);
				for (Field fin : f.type.fields) {
					sb.append(indent);
					sb.append(fin.name);
					sb.append(newline);
				}
			} else if (f.refer) {
				sb.append(f.displayName);
				sb.append("_");
				sb.append(f.type.primaryKeyField.name);
				sb.append(newline);
			} else if (f.type.scala) {
				sb.append(f.displayName);
				if(f.key){
					sb.append(": readonly");
				}
				sb.append(newline);
			}
		}
		System.out.println(sb.toString());

	}
}
