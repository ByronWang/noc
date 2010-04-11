package data;

import noc.annotation.DisplayName;
import noc.annotation.Inline;
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
}


