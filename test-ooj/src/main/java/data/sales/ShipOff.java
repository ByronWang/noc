package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Comment;
import noc.lang.Count;
import noc.lang.Date;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.NumberValue;
import data.master.BusinessPartner;
import data.master.Department;
import data.master.inv.BrandLabel;
import data.master.inv.PackageType;
import data.master.product.Brand;
import data.master.product.Factory;
import data.master.product.GoodsFan;
import data.master.product.ProductType;

/**
 * 发货单
 * 
 * @author wangshilian
 * 
 */
@DisplayName("发货单")
public class ShipOff {
    ID 发货单编号;
    Date 发货日期;
    Date 预计到货日期;
    Date 实际到货日期;

    ID 发货指示编号;
    BusinessPartner 结算单位;

    ID 外部参考号;
    Department 部门;

    Attr 收货地址;

    BusinessPartner 承运人;
    BusinessPartner 发货单位;
    Attr 币别;
    Attr 运输条款;
    Attr 运输方式;
    Attr 车牌号;
    Attr 总金额;

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

        @DisplayName("L#")
        Attr LSharp;

        Count 个数;
        Count 令数;

        Count 订购个数;
        NumberValue 订购重量;
    }
}
