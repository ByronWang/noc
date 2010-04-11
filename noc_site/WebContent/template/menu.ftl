[#ftl/]
[#macro typeinfo tp]
    [#if tp.name?matches("noc.lang[.].*")]
       &lt; ${tp.name?replace("noc.lang.", "")}
    [#else]
        &lt; <a href="${contextPath}/${tp.name?replace(".", "/")}/">${tp.name}<a>
    [/#if]
[/#macro]

<html><head>
<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
</head><body class="nav">
<div class="navbox"> 
<ul class="nav">
    <#list list as data><li>
        <a href="${r"${data."+ type.keyField.name + "}"}">${r"${data."+ type.keyField.name + "}"}</a>
       </li>
    </#list>  
</ul>   
</div>
</body></html>