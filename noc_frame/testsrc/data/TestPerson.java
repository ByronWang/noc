package data;

import noc.annotation.DisplayName;
import noc.annotation.PrimaryKey;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.List;
import noc.lang.Literal;
import noc.lang.Name;

public class TestPerson {
	Code 工号;
	@PrimaryKey @DisplayName("姓名") Name 名称;
	Literal[] 其他名称;
	TestCompany 公司;
	List<TestCompany> 下属公司;
	java.util.List<TestCompany> 关联公司;
	
	TestDepartment 部门;
	List<TestDepartment> 下属部门;
	java.util.List<TestDepartment> 关联部门;
	
	Contact 联系方式;
	class Contact {
		Attr 公司电话;
		Attr 传真;
		Attr 手机;
		Attr EMail;
	}

}
