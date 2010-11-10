package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Currency;
import noc.lang.Date;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.Status;
import data.master.BusinessPartner;
import data.master.Department;
import data.master.inv.BrandLabel;
import data.master.inv.PackageType;
import data.master.product.Brand;
import data.master.product.Factory;
import data.master.product.GoodsFan;
import data.master.product.ProductForm;
import data.master.product.ProductType;

@DisplayName("定价单")
public class Pricing {
    ID 定价单编号;
    Department 部门;
    BusinessPartner 客户;
    Attr 行业;
    Attr 二次销售对象;
    BusinessPartner 最终用户;
    Attr 用途;
    Date 生效日期;
    Date 到期日期;
    Attr 计算类型;
    Attr 明细类型;
    Attr 运输方式;
    Attr 币别;
    Attr 制单人;
    Date 制单日期;
    Attr 優先度;
    Status 状态;
    List<Attr> 相关价差;

    class Item {

        Factory 工厂;
        ProductType 品种;
        Brand 品牌;
        BrandLabel 标签;
        GoodsFan 品番;
        Attr 克重;
        Attr 尺寸;
        ProductForm 商品形态;

        // Attr 入令数卷长;
        Attr 纸心;
        PackageType 包装形态;
        // Attr 抄月;
        // Attr MC;
        // Attr 格外;
        Attr 等级;
        Attr 国内出口;

        Currency 单价;
    }

}
