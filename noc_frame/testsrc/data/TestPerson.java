package data;

import noc.annotation.DisplayName;
import noc.annotation.Inline;
import noc.annotation.PrimaryKey;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.List;
import noc.lang.Literal;
import noc.lang.Name;

public class TestPerson {
	public @PrimaryKey @Inline Code 工号;
	public @PrimaryKey @DisplayName("姓名") Name 名称;
	public Literal[] 其他名称;
	
	public TestCompany 公司;
	public @Inline	TestCompany 上级公司;
	
	public List<TestCompany> 下属公司;
	public java.util.List<TestCompany> 关联公司;

	public TestDepartment 部门;	
	public @Inline TestDepartment 上级部门;
	
	public List<TestDepartment> 下属部门;
	public java.util.List<TestDepartment> 关联部门;
	
	public TestPerson 妻子;
	
	
	public Contact 联系方式;
	class Contact {
		Attr 公司电话;
		Attr 传真;
		Attr 手机;
		Attr EMail;
	}

}
