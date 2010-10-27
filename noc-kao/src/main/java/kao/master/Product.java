package kao.master;

import noc.annotation.DisplayName;
import noc.annotation.PrimaryKey;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Name;
import noc.lang.NumberValue;

@DisplayName("产品")
public class Product {
	@PrimaryKey
	Code 代码;
	Name 名称;
	Attr 单位;
	NumberValue 箱装;
}
