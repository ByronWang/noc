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
    public Code 代码;
    public Literal 名称;
    public TestDepartment 所属;
    public Status 状态;

    public ContactHasnotKey 联系方式HasnotKey;
    public ContactHasKey 联系方式HasKey;

    @Standalone
    public class ContactHasnotKey {
        Attr 公司电话;
        Attr 传真;
        Attr 手机;
        Attr EMail;
    }

    @Standalone
    public class ContactHasKey {
        Code 公司电话;
        Attr 传真;
        Attr 手机;
        Attr EMail;
    }

}
