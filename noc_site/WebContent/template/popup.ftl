[#ftl/]
<table class="popup"> 
        <caption>${type.displayName}</caption>
    <thead>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && inf.key]
        <th scope="col">${inf.displayName}</th>[/#if][/#list]</tr>
    </thead>
    
    <tbody>
    
	[#list type.fields as rF]
		[#if rF.key]
			[#assign keyField=rF/]
		[/#if]
	[/#list]		
					
    <#list data as item>
    <tr>
    [#list type.fields as inf ][#if inf.refer == "Scala" && inf.key]
        <td><a href="#"  onclick="returnValue('${r"${item." + keyField.name + "}"}')">${r"${item."+ inf.name + "}"}</a></td>
        [/#if] [/#list]</tr>   
	</#list>
    </tbody>
</table>