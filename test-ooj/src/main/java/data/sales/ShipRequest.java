package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Comment;
import noc.lang.Count;
import noc.lang.Date;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.Status;
import noc.lang.Weight;
import data.master.BusinessPartner;
import data.master.Warehouse;
import data.master.inv.BrandLabel;
import data.master.inv.PackageType;
import data.master.product.Brand;
import data.master.product.Factory;
import data.master.product.GoodsFan;
import data.master.product.ProductType;
/**
 * 发货指示
 * @author wangshilian
 *
 */
@DisplayName("发货指示")
public class ShipRequest {
	ID 发货指示编号;
	BusinessPartner 供货方;
	BusinessPartner 购货方;
	BusinessPartner 结算单位;
	Status 状态;
	ID 订单编号;
	ID Oid;
	Attr 库存来源;
	Attr 二次销售对象;
	Attr 最终用户;
	Date 收货日期;
	Attr 按时到货;
	Attr 时间指定;
	BusinessPartner	 收货地址收货单位;
	Attr 交货方式;
	BusinessPartner 承运人;
	Attr 运输方式;
	Date 出库预定日;
	BusinessPartner 发货单位;
	Warehouse 仓库;
	Attr 制单人;
	Date 制单日期;
	Comment 备注;

	
	List<Item> items;

	List<BusinessPartner.Address> 地址;
	List<BusinessPartner.ContactPerson> 联系人;

	class Item {
		Factory 工厂;
		ProductType 品种;
		Brand 品牌;
		BrandLabel 标签;
		GoodsFan 品番;
		Attr 克重;
		Attr 尺寸;
		Attr 入令数卷长;
		Attr 纸心;
		PackageType 包装形态;
		Attr 抄月;
		Attr MC;
		Attr 格外;
		Attr 等级;

		@DisplayName("L#") Attr LSharp;

		Count 个数;
		Count 令数;

		Count 订购个数;
		Weight 订购重量;
	}
}
