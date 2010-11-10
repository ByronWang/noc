package data.master;

import noc.annotation.DisplayName;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

@DisplayName("部门")
public class Department {
    Code 代码;
    Literal 名称;
    Department 所属;
    Status 状态;
}
