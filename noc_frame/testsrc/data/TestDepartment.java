package data;

import noc.annotation.DisplayName;
import noc.annotation.Inline;
import noc.annotation.Standalone;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

@DisplayName("部门")
@Inline
public class TestDepartment {
	Code 代码;
	Literal 名称;
	TestDepartment 所属;
	Status 状态;

	ContactHasnotKey 联系方式HasnotKey;
	ContactHasKey 联系方式HasKey;
	@Standalone
	class ContactHasnotKey {
		Attr 公司电话;
		Attr 传真;
		Attr 手机;
		Attr EMail;
	}
	@Standalone
	class ContactHasKey {
		Code 公司电话;
		Attr 传真;
		Attr 手机;
		Attr EMail;
	}
	
}


