package data.master;

import noc.annotation.DisplayName;
import noc.lang.List;
import noc.lang.Name;
import noc.lang.Status;

@DisplayName("公司")
public class Company {
	Name 名称;
	Name 简称;
	Status 状态;
	List<Company> 子公司;	
}
