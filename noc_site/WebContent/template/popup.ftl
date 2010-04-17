[#ftl/]
<table class="popup"> 
        <caption>${type.displayName}</caption>
    <thead>
    <tr>
    [#list type.fields as inf ][#if inf.type.scala && inf.primaryKey]
        <th scope="col">${inf.displayName}</th>[/#if][/#list]</tr>
    </thead>
    
    <tbody>
    
    <#list data?sort_by("${type.primaryKeyField.name}") as item>
    <tr>
    [#list type.fields as inf ][#if inf.type.scala && inf.primaryKey]
        <td><a href="#"  onclick="returnValue('${r"${item."+ type.primaryKeyField.name + "}"}')">${r"${item."+ inf.name + "}"}</a></td>
        [/#if] [/#list]</tr>   
	</#list>
    </tbody>
</table>