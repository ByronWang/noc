package data.master;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

@DisplayName("仓库")
public class Warehouse {
	Code 代码;
	Literal 名称;
	Attr 运输方式;
	Attr 收货方;
	Attr 地址;
	
	Company 物流公司;
	Employee 联系人; //TODO
	Attr 联系电话;
	Attr 传真;
	Attr EMail;
	Attr 手机号码;
	
		
	Attr 定点补货;//TODO
	Attr 系统接口;
	
	Status 状态;
}
