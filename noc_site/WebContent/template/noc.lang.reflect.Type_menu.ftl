<html><head>
<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
</head><body class="nav" >
<div class="navbox" > 
<ul class="nav"><#list data?sort_by("name") as item><#if item.standalone>
<li><a href="${contextPath}/${item.name?replace(".","/")}/" target="oEdit">${item.displayName}</a></li>
</#if>
</#list></ul>   
</div >
</body></html>