<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Files Upload Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>Files Upload Screen</h1>
        <t:messagesPanel />
        <form:form action="${pageContext.request.contextPath}/upload/files" method="post" enctype="multipart/form-data"
            modelAttribute="filesUploadForm">
            <form:errors element="div" path="uploadUploadForms" />
            Upload File : <form:input type="file" path="uploadUploadForms[0].file" /><form:errors path="uploadUploadForms[0].file" /><br>
            Description : <form:input path="uploadUploadForms[0].description" /><form:errors path="uploadUploadForms[0].description" /><br>
            <br>
            Upload File : <form:input type="file" path="uploadUploadForms[1].file" /><form:errors path="uploadUploadForms[1].file" /><br>
            Description : <form:input path="uploadUploadForms[1].description" /><form:errors path="uploadUploadForms[1].description" /><br>
            <br>
            <form:button>Upload</form:button>
            <br>
        </form:form>
    </div>
</body>
</html>
