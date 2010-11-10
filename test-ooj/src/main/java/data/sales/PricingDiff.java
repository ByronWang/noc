package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Comment;
import noc.lang.Currency;
import noc.lang.Date;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.Status;
import data.master.Company;
import data.master.product.Brand;
import data.master.product.ProductForm;
import data.master.product.ProductType;

@DisplayName("价差单")
public class PricingDiff {
    ID 价差编号;
    Company 公司;

    Date 生效日期;
    Date 到期日期;

    ProductType 品种;
    Brand 品牌;
    Attr 币别;
    Status 状态;

    Attr 制单人;
    Date 制单日期;

    Comment 备注;

    List<ProductFormItem> 卷平价差;
    List<WeightItem> 克重价差;
    List<SizeItem> 寸法价差;
    List<SendDirect> 直送价差;
    List<TakeSelfItem> 自提价差;

    class ProductFormItem {
        ProductForm 商品形态;
        Currency 价差;
    }

    class WeightItem {
        Attr 克重范围;
        Currency 价差;
    }

    class SizeItem {
        Attr 寸法;
        Attr 范围;
        Currency 价差;
    }

    class SendDirect {
        Attr 工厂直送;
        Currency 价差;
    }

    class TakeSelfItem {
        Attr 客户自提;
        Currency 价差;
    }

}
