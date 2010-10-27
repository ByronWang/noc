package data.master.inv;
import noc.annotation.DisplayName;
import noc.lang.Length;
import noc.lang.Status;
import data.master.product.Brand;
import data.master.product.Factory;
import data.master.product.GoodsFan;
import data.master.product.ProductType;

/**
 * 规格品
 * @author wangshilian
 *
 */
@DisplayName("规格品")
public class StandardProduct {
	Brand 品牌;
	Factory 工厂;
	GoodsFan 品番;
	ProductType 商品形态;
	PackageType 包装形态;
	BrandLabel 标签;
	/**
	 * 入R数/巻長
	 */
	Length 入令数卷长;
	Status 状态;	

}
