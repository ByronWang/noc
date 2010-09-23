package noc.lang.reflect;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import util.VoHelper;

public class TestTypeReflect extends TestCase {

	TypeReadonlyStore store = null;

	@Override protected void setUp() throws Exception {
		super.setUp();
		store = new TypeReadonlyStore();
		store.setUp();
	}

	@Override protected void tearDown() throws Exception {
	}

	protected void showType(Type type) {

		StringBuffer sb = new StringBuffer();

		final String newline = "\n";
		final String indent = "\t";

		for (Field f : type.getFields()) {
			if (f.array) {
				if (f.refer == Field.Inline) {
					sb.append(f.name);
					sb.append(":");
					sb.append("inline list");
					sb.append(newline);
					for (Field fin : f.type.fields) {
						sb.append(indent);
						sb.append(fin.name);
						sb.append(indent);
						sb.append("< ");
						sb.append(fin.type.name);
						sb.append(newline);
					}
					sb.append(newline);
				} else if (f.refer == Field.Reference || f.refer == Field.Cascade) {
					sb.append(f.name);
					sb.append(":");
					sb.append("list");
					sb.append(newline);
					for (Field fin : f.type.fields) {
						if (fin.getImportance() == Field.PrimaryKey) {
							sb.append(indent);
							sb.append(f.name);
							sb.append("");
							sb.append(fin.name);
							sb.append(newline);
						}
					}
					sb.append(newline);
				} else if (f.refer == Field.Scala) {
					sb.append(f.name);
					sb.append(":");
					sb.append("list");
					sb.append(newline);
					sb.append(indent);
					sb.append(f.name);
					sb.append(newline);
				} else {

				}

			} else {
				if (f.refer == Field.Inline) {

					sb.append(f.name);
					sb.append(":");
					sb.append("inline");
					sb.append(newline);
					for (Field fin : f.type.fields) {
						sb.append(indent);
						sb.append(fin.name);
						sb.append(newline);
					}
				} else if (f.refer == Field.Reference || f.refer == Field.Cascade) {
					sb.append(f.name);

					sb.append(": hide");
					sb.append(newline);

					for (Field fin : f.type.fields) {
						if (fin.getImportance() == Field.PrimaryKey) {
							sb.append(indent);
							sb.append(f.name);
							sb.append("");
							sb.append(fin.name);
							sb.append(newline);
						}
					}
				} else if (f.refer == Field.Scala) {
					sb.append(f.name);
					if (f.getImportance() == Field.PrimaryKey) {
						sb.append(": readonly");
					}
					sb.append(indent);
					sb.append("< ");
					sb.append(f.type.name);
					sb.append(newline);
				} else {

				}
			}
		}
		System.out.println(sb.toString());

	}

//	public void testPerson() {
//
//		Type p = store.get("data.TestPerson");
//		showType(p);
//	}
	
	public void testType() {

		Type p = store.readData("noc.lang.reflect.Type");
		showType(p);
	}

	//	
	// if (f.array && f.inline) {
	// } else if (f.array && f.refer) {
	// } else if (f.array) {
	// } else if (f.inline) {
	// } else if (f.refer) {
	// } else if (f.type.scala) {
	// }
	public void testSetValue() {

		Type person = store.readData("data.TestPerson");
		
		Vo v = new VOImp(person);

		Map<String, String[]> params = new HashMap<String, String[]>();

		// TestPerson tp;
		// public @PrimaryKey @Inline Code 工号;
		// public @PrimaryKey @DisplayName("姓名") Name 名称;
		// public Literal[] 其他名称;
		//		
		// public TestCompany 公司;
		// public @Inline TestCompany 上级公司;
		//		
		// public List<TestCompany> 下属公司;
		// public java.util.List<TestCompany> 关联公司;
		//
		// public TestDepartment 部门;
		// public @Inline TestDepartment 上级部门;

		params.put("工号", new String[] { "工号V" });
		params.put("名称", new String[] { "名称V" });
		params.put("公司_名称", new String[] { "名称" });

		params.put("部门_代码", new String[] { "部门_代码 V" });
		params.put("部门_名称", new String[] { "部门_名称V" });
		params.put("部门_状态", new String[] { "部门_状态V" });

		VoHelper.putAll(params, v, person);

		System.out.println(v.getCanonicalForm());

		params = new HashMap<String, String[]>();
		params.put("工号", new String[] { "工号V" });
		params.put("名称", new String[] { "名称V" });
		params.put("公司_名称", new String[] { "名sdsf称" });
		params.put("部门_代码", new String[] { "代sdfdsf码" });

		VoHelper.putAll(params, v, person);

		System.out.println(v.getCanonicalForm());
	}

}
