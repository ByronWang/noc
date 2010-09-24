package data.kao;

import noc.annotation.Core;
import noc.annotation.DisplayName;
import noc.annotation.Reference;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Name;

@DisplayName("客户")
public class Customer {
	Code 代码;
	@Core Name 名称;
	CustomerGroup 客户组;
	SalesOffice 销售办公室;
	Region 地区;
	Route 路线;
	Attr 冻结;
	@Reference Customer 送达方;
	@Reference Customer 付款方;
}
