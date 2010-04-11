package data.master;

import noc.annotation.DisplayName;
import noc.lang.Code;
import noc.lang.Literal;

@DisplayName("业务区域")
public class BusinessArea {
	Code 代码;
	Literal 名称;
	BusinessArea 所属;	
}
