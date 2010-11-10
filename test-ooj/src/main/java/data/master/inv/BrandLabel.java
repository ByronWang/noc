package data.master.inv;

import noc.annotation.DisplayName;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;
import data.master.product.Brand;
import data.master.product.Factory;

@DisplayName("标签")
public class BrandLabel {
    Brand 品牌;
    Factory 工厂;
    Code 代码;
    Literal 名称;
    Literal 简称;
    Status 状态;
}
