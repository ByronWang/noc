<html><head>
<link rel='stylesheet' href='/noc/css/menu.css' type='text/css'/>
</head><body class="nav" >
<div class="navbox" >
 
<ul class="nav"><#list data?sort_by("master") as item><#if item.standalone>
<li><a href="${contextPath}/${item.name?replace(".","/")}/"  title="${item.master}" target="oEdit">${item.displayName}</a></li>
</#if>
</#list></ul>  


 
</div >
</body></html>