<html><head>
<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
</head><body class="nav" >
<div class="navbox" > 
<ul class="nav"><#list list?sort_by("name") as data><#if !data.declaringType?? && data.standalone>
<li><a href="${contextPath}/${data.name?replace(".","/")}/" target="oEdit">${data.displayName}</a></li>
</#if>
</#list></ul>   
</div >
</body></html>