<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Examples of TERASOLUNA Global Framework</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/common/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Top Screen</h1>
<spring:message text=""></spring:message>
        <h2>Applications of example</h2>
        <div>
            <h3>Upload</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/upload/single">File Upload -Single file-</a></li>
                <li><a href="${pageContext.request.contextPath}/upload/multiple">File Upload -Multiple file-</a></li>
                <li><a href="${pageContext.request.contextPath}/upload/flow">File Upload -With in Screen flow-</a></li>
            </ul>
            <h3>Pagination</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/pagination/search?form">Pagination Search</a></li>
            </ul>
            <h3>FormHiddensTag(Prototype)</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/utilities/hiddensTag/createl">Multiple Screen in use case</a></li>
            </ul>
        </div>
        

        <br>

        <h2>Applications for functional Testing</h2>
        <div>
            <h3>FormHiddensTag(Prototype)</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/utilities/hiddensTag/test">Test Form</a></li>
            </ul>
        </div>

    </div>
</body>
</html>
