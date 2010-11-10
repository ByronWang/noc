package data.master.inv;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

@DisplayName("包装形态")
public class PackageType {
    Code 编号;
    Literal 名称;
    Attr 类别;
    Status 状态;
}
