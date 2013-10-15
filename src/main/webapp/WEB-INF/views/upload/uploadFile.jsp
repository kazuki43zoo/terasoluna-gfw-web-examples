<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>File Upload Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>File Upload Screen</h1>
        <t:messagesPanel />
        <form:form action="${pageContext.request.contextPath}/upload/file" method="post" enctype="multipart/form-data"
            modelAttribute="fileUploadForm">
            Upload File : <form:input type="file" path="file" /><form:errors path="file" /><br>
            Description : <form:input path="description" /><form:errors path="description" /><br>
            <form:button>Upload</form:button>
            <br>
        </form:form>
    </div>
</body>
</html>
