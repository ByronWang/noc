package data.master.product;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Status;
import noc.lang.Weight;

/**
 * 品番
 * 
 * @author wangshilian
 * 
 */
@DisplayName("品番")
public class GoodsFan {
    /**
     * 品番
     */
    Code 代码;
    ProductForm 商品形态;

    Factory 工厂;
    ProductType 品种;
    Brand 品牌;

    Weight 克重;

    Attr 生产年月日管理区分;
    @DisplayName("L#管理区分")
    Attr LSharp管理区分;
    @DisplayName("M/C管理区分")
    Attr MC管理区分;

    Status 状态;
}
