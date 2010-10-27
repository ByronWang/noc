package data.master;

import noc.annotation.PrimaryKey;
import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Date;
import noc.lang.Literal;
import noc.lang.Name;

@DisplayName("员工") 
public class Employee {
	Code 工号;
	@PrimaryKey @DisplayName("姓名") Name 名称;
	Literal 英文名;
	Attr 性别;
	Company 公司;
	Department 部门;
	Attr 职务;
	Date 入职日期;
	Date 离职日期;
	Attr 成本中心;
	Contact 联系方式;

	class Contact {
		Attr 公司电话;
		Attr 传真;
		Attr 手机;
		Attr EMail;
	}

	Employee() {}

	void computer() {}
}
