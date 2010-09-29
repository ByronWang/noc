package kao.transaction;

import kao.master.Customer;
import kao.master.CustomerGroup;
import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Currency;
import noc.lang.Date;
import noc.lang.Day;
import noc.lang.Desc;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.NumberValue;

@DisplayName("订单")
public class Order {
	ID id;
	Date 订货日期;
	Date 发货日期;
	Date 定价日期;
	Day 送货天数;
	Customer 售达方;
	Customer 送达方;
	Customer 付款方;
	CustomerGroup 物料组;
	@DisplayName("PO")
	ID po;
	Desc 备注;
	@DisplayName("明细")
	List<OrderItem> items;
	
	class OrderItem{
		Code 客户产品号;
		Code 花王产品号;
		NumberValue 数量;
		Attr 单位;
		Currency 单价;
		Currency  金额;		
	}
}
