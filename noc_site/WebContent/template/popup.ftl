[#ftl/]
<table class="popup"> 
        <caption>${type.displayName}</caption>
    <thead>
    <tr>
    [#list type.fields as inf ][#if inf.type.scala && inf.primaryKey]
        <th scope="col">${inf.displayName}</th>[/#if][/#list]</tr>
    </thead>
    
    <tbody>
    
    <#list list?sort_by("${type.primaryKeyField.name}") as data>
    <tr>
    [#list type.fields as inf ][#if inf.type.scala && inf.primaryKey]
        <td><a href="#"  onclick="returnValue('${r"${data."+ type.primaryKeyField.name + "}"}')">${r"${data."+ inf.name + "}"}</a></td>
        [/#if] [/#list]</tr>   
	</#list>
    </tbody>
</table>