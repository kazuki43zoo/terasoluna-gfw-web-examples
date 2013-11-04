<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Ajax Simple Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
    $.ajaxSetup({
        type : "POST",
        dataType : "xml",
        beforeSend : function(xhr) {
            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
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
        $.ajax("${pageContext.request.contextPath}/ajax/simple?test", {
            contentType : "application/json;charset=utf-8",
            data : toJson($("#form"))
        }).done(function(response) {
            console.log(response);
        });
    }
    function requestXxe() {
        $.ajax("${pageContext.request.contextPath}/ajax/xxe", {
            data : $("#data").val(),
            contentType : "application/xml;charset=utf-8",
        }).done(function(response) {
            console.log(response);
        });
    }
    function requestXxes() {
        $.ajax("${pageContext.request.contextPath}/ajax/xxes", {
            contentType : "application/xml;charset=utf-8",
            data : $("#data").val(),
        }).done(function(response) {
            console.log(response);
        });
    }
    function requestXxeForSax() {
        $.ajax("${pageContext.request.contextPath}/ajax/xxe?sax", {
            contentType : "text/xml;charset=utf-8",
            data : $("#data").val(),
        }).done(function(response) {
            console.log(response);
        });
    }
</script>
</head>
<body>
    <form id="form">
        <input name="a" value="b"> <input name="c" value="d"><br>
        <textarea id="data" rows="10" cols="80"></textarea>
    </form>
    <button onclick="request()">Test</button>
    <button onclick="requestXxe()">TestXxe</button>
    <button onclick="requestXxes()">TestXxes</button>
    <button onclick="requestXxeForSax()">TestXxeForSax</button>
</body>
</html>
