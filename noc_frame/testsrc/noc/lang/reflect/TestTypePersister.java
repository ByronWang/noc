package noc.lang.reflect;

import junit.framework.TestCase;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Name;
import data.TestCompany;
import data.TestDepartment;

public class TestTypePersister extends TestCase {

	TypePersister store = null;

	@Override protected void setUp() throws Exception {
		super.setUp();
		store = new TypePersister();
	}

	@Override protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public void testLoad() {

		// store.loadFolder("..\\noc_Biz\\bin");
		//
		// store.get("data.sales.Order");

		Type p = store.get("data.TestPerson");

		assertEquals(6, store.list().size());

		// Code 工号;
		Field f = p.fields.get(0);
		assertEquals("工号", f.name);
		assertEquals("工号", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(Code.class.getName(), f.type.name);

		// @PrimaryKey @DisplayName("姓名") Name 名称;
		f = p.fields.get(1);
		assertEquals("名称", f.name);
		assertEquals("姓名", f.displayName);
		assertEquals(true, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(Name.class.getName(), f.type.name);

		// Literal[] 其他名称;
		f = p.fields.get(2);
		assertEquals("其他名称", f.name);
		assertEquals("其他名称", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(Literal.class.getName(), f.type.name);

		// TestCompany 公司;
		f = p.fields.get(3);
		assertEquals("公司", f.name);
		assertEquals("公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);

		// List<TestCompany> 下属公司;
		f = p.fields.get(4);
		assertEquals("下属公司", f.name);
		assertEquals("下属公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);

		// java.util.List<TestCompany> 关联公司;
		f = p.fields.get(5);
		assertEquals("关联公司", f.name);
		assertEquals("关联公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);

		// TestDepartment 部门;
		f = p.fields.get(6);
		assertEquals("部门", f.name);
		assertEquals("部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);

		// List<TestDepartment> 下属部门;
		f = p.fields.get(7);
		assertEquals("下属部门", f.name);
		assertEquals("下属部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);

		// java.util.List<TestDepartment> 关联部门;
		f = p.fields.get(8);
		assertEquals("关联部门", f.name);
		assertEquals("关联部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);

		//		
		//
		// int i = 0;
		// for (Type type : store.listScala()) {
		// System.out.println("" + (i++) + " > " + type.getName());
		// for (Field f : type.getFields()) {
		// if (f.isInline()) {
		// System.out.println("[Inline] " + f.getDisplayName() + " : " +
		// f.getType().getName());
		// } else {
		// System.out.println("  " + f.getDisplayName() + " : " +
		// f.getType().getName());
		//
		// }
		// }
		// }
		//
		// i=0;
		// for (Type type : store.list()) {
		// System.out.println("" + (i++) + " > " + type.getName());
		//
		// for (Field f : type.getFields()) {
		// if (f.isInline()) {
		// System.out.println("[Inline] " + f.getDisplayName() + " : " +
		// f.getType().getName());
		// } else {
		// System.out.println("  " + f.getDisplayName() + " : " +
		// f.getType().getName());
		//
		// }
		// }
		// }

	}
}
