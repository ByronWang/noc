[#ftl/]
<table class="popup"> 
        <caption>${type.displayName}</caption>
    <thead>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && (inf.importance == "PrimaryKey" || inf.importance == "Core")]
        <th scope="col">${inf.displayName}</th>[/#if][/#list]</tr>
    </thead>
    
    <tbody>
    
	[#list type.fields as rF]
		[#switch rF.importance]
			[#case "PrimaryKey"]
				[#assign primaryKeyField=rF]
				[#break]
			[#case "Core"]
				[#assign coreField=rF]
				[#break]
		[/#switch]
	[/#list]	
					
    <#list data as item>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && (inf.importance == "PrimaryKey" || inf.importance == "Core")]
        <td><a href="#"  onclick="returnValue('${r"${item." + primaryKeyField.name + "}"}','${r"${item." + coreField.name + "}"}')">${r"${item."+ inf.name + "}"}</a></td>
        [/#if] [/#list]</tr>   
	</#list>
    </tbody>
</table>