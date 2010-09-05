[#ftl/]
<table class="popup"> 
        <caption>${type.displayName}</caption>
    <thead>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && inf.key]
        <th scope="col">${inf.displayName}</th>[/#if][/#list]</tr>
    </thead>
    
    <tbody>
    
    <#list data as item>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && inf.key]
        <td><a href="#"  onclick="returnValue('${r"${item.indentify}"}')">${r"${item."+ inf.name + "}"}</a></td>
        [/#if] [/#list]</tr>   
	</#list>
    </tbody>
</table>