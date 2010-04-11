package noc.lang.reflect;

import junit.framework.TestCase;
import noc.annotation.Inline;
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
		super.tearDown();
	}

	public void testLoad() {

		// store.loadFolder("..\\noc_Biz\\bin");
		//
		// store.get("data.sales.Order");

		Type p = store.get("data.TestPerson");

		assertEquals(6, store.list().size());

		int index = 0;
		
		// @Inline Code 工号;
		Field f = p.fields.get(index++);
		assertEquals("工号", f.name);
		assertEquals("工号", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(Code.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(false, f.refer);

		// @PrimaryKey @DisplayName("姓名") Name 名称;
		f = p.fields.get(index++);
		assertEquals("名称", f.name);
		assertEquals("姓名", f.displayName);
		assertEquals(true, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(Name.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(false, f.refer);

		// Literal[] 其他名称;
		f = p.fields.get(index++);
		assertEquals("其他名称", f.name);
		assertEquals("其他名称", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(Literal.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(false, f.refer);

		// TestCompany 公司;
		f = p.fields.get(index++);
		assertEquals("公司", f.name);
		assertEquals("公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(true, f.refer);

//		@Inline	TestCompany 上级公司;
		f = p.fields.get(index++);
		assertEquals("上级公司", f.name);
		assertEquals("上级公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);
		assertEquals(true, f.inline);
		assertEquals(false, f.refer);

		// List<TestCompany> 下属公司;
		f = p.fields.get(index++);
		assertEquals("下属公司", f.name);
		assertEquals("下属公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(true, f.refer);

		// java.util.List<TestCompany> 关联公司;
		f = p.fields.get(index++);
		assertEquals("关联公司", f.name);
		assertEquals("关联公司", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestCompany.class.getName(), f.type.name);
		assertEquals(false, f.inline);
		assertEquals(true, f.refer);

		// TestDepartment 部门;
		f = p.fields.get(index++);
		assertEquals("部门", f.name);
		assertEquals("部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);
		assertEquals(true, f.inline);
		assertEquals(false, f.refer);
		
		// TestDepartment 上级部门;
		f = p.fields.get(index++);
		assertEquals("上级部门", f.name);
		assertEquals("上级部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(false, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);
		assertEquals(true, f.inline);
		assertEquals(false, f.refer);

		// List<TestDepartment> 下属部门;
		f = p.fields.get(index++);
		assertEquals("下属部门", f.name);
		assertEquals("下属部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);
		assertEquals(true, f.inline);
		assertEquals(false, f.refer);

		// java.util.List<TestDepartment> 关联部门;
		f = p.fields.get(index++);
		assertEquals("关联部门", f.name);
		assertEquals("关联部门", f.displayName);
		assertEquals(false, f.primaryKey);
		assertEquals(true, f.array);
		assertEquals(TestDepartment.class.getName(), f.type.name);
		assertEquals(true, f.inline);
		assertEquals(false, f.refer);

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
