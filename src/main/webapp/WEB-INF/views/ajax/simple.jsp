<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Search Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vender/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$.ajaxSetup({
		type : "POST",
		url : "${pageContext.request.contextPath}/ajax/simple?test",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			xhr.setRequestHeader("Content-Type",
					"application/json;charset=utf-8");
		}
	});
	function toJson(form) {
		var data = {};
		$(form.serializeArray()).each(function(i, v) {
			data[v.name] = v.value;
		});
		return JSON.stringify(data);
	}
	function request() {
		$.ajax({
			data : toJson($("#form"))
		}).done(function(responseJson) {
			console.log(responseJson);
		});
	}
</script>
</head>
<body>
    <form id="form">
        <input name="a" value="b"> <input name="c" value="d">
    </form>
    <button onclick="request()">Test</button>
</body>
</html>
