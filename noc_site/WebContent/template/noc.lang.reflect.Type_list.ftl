
<html><head>
<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
<title>noc.lang.reflect.Type</title>
</head>
<body>
<h1>noc.lang.reflect.Type</h1>
		
<a style="float:right;" href="/noc/noc/lang/reflect/Type/noc.lang.reflect.Type">Type</a>

<table class="list"> 
	<thead>	
		<tr>
			<th scope="col"> displayName </th>
			<th scope="col"> name </th>
			<th scope="col"> scala </th>
		</tr>
	</thead>
	
	
	<tbody>
		<#list list?sort_by("name") as data><#t><#if !data.declaringType??>
		<tr>
			<td> ${data.displayName}</td>
			<td> <a href="${data.name}">${data.name}</a></td>
			<td> <#if data.scala>True<#else>false</#if></td>
		</tr>   
		</#if>
	</#list>	
	</tbody>
</table>

<br/>

<div class="action">
<a href="/noc/noc/lang/reflect/Type/?new">Add New<a>
</div>
</body></html>
