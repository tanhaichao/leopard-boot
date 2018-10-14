<!DOCTYPE html>
<html>
<head lang="en">
<title>Leopard Boot Web自动测试</title>
<style type="text/css">
.result_success {
	background-color: green;
}

.result_error {
	background-color: red;
}
</style>
</head>
<body>
	<h2>验证结果</h2>

	<table border="1">
		<tr>
			<th>Controller</th>
			<th>名称</th>
			<th>预期</th>
			<th>消息</th>
		</tr>
		<#list resultList as result>
		<tr class="result_${result.status}">
			<td>${result.controller}</td>
			<td>${result.name!}</td>
			<td>${result.expect?c}</td>
			<td>${result.message!}</td>
		</tr>
		</#list>
	</table>
</body>
</html>