package data.master.product;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

/**
 * 品牌
 * 
 * @author wangshilian
 * 
 */
@DisplayName("品牌")
public class Brand {
    Code 代码;
    Literal 名称;
    Literal 简称;
    ProductType 品种;
    Attr 国内出口区分;
    Attr 数量单位;

    Factory 工厂;

    Status 状态;

}
