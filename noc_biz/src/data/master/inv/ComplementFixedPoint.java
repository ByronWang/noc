package data.master.inv;
import noc.annotation.DisplayName;
import noc.lang.Attr;
import data.master.Warehouse;
import data.master.product.Brand;
import data.master.product.GoodsFan;
import data.master.product.ProductType;

@DisplayName("定点补货")
public class ComplementFixedPoint {
	Warehouse 仓库;
	Brand 品种;
	BrandLabel 标签;
	GoodsFan 品番;
	Attr 克重;
	Attr 尺寸;
	ProductType 商品形态;
	Attr 入令数卷长;
	PackageType 包装形态;
	Attr 日销售量;
	Attr 安全系数;
	Attr 提前期;
	Attr 定点库存量;
	

	
}
