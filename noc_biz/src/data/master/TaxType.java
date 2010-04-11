package data.master;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Percent;

@DisplayName("税率分组")
public class TaxType {
	Code 代码;
	Literal 名称;
	Attr 税基;
	Attr 税率种类;
	Attr 业务种类;
	Percent 税率;
	Company 税务机关;
	Attr 是否价内税;

}
