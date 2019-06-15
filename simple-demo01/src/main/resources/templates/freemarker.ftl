<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<h1>Freemarker模板文件</h1>
	<#if name??>
		Welcome ${name}
	<#else>
		Hello world!
	</#if>
</body>
</html> 