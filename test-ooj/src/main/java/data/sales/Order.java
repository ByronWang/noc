package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Comment;
import noc.lang.Count;
import noc.lang.Date;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.NumberValue;
import noc.lang.Status;
import noc.lang.Time;
import data.master.BusinessPartner;
import data.master.Department;
import data.master.Employee;
import data.master.inv.BrandLabel;
import data.master.inv.PackageType;
import data.master.product.Brand;
import data.master.product.Factory;
import data.master.product.GoodsFan;
import data.master.product.ProductType;

/**
 * 订单
 * 
 * @author wangshilian
 * 
 */
@DisplayName("订单")
public class Order {
    BusinessPartner 供货方;
    BusinessPartner 购货方;
    Status 状态;
    ID id;
    ID 外部参考号;
    Attr 订单类型;
    Attr 库存来源;
    Attr 二次销售对象;
    BusinessPartner 最终用户;
    Attr 用途;
    Date 单据日期;
    Department 所属部门;
    Employee 业务员;
    Date 收货日期;
    Attr 按时到货;
    Time 时间指定;
    Attr 相关单类型;
    Attr 相关单号;
    BusinessPartner 发货单位;
    Attr 收货单位;
    BusinessPartner 结算单位;
    Attr 交货方式;
    BusinessPartner 承运人;
    Attr 运输方式;
    Attr 运输费负担;
    Date 出库预定日;
    NumberValue 纳期;
    Attr 付款方式;
    Attr 付款条款;
    Attr 仓库;
    Attr 对方订单号;

    Employee 制单人;

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
