<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>File Upload Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">

        <%-- screen name --%>
        <h1>File Upload Confirm Screen</h1>

        <form:form action="${pageContext.request.contextPath}/upload/flow" method="post" modelAttribute="fileUploadForm">

            <%-- upload confirm form --%>
            Upload File : 
            <a target="${fileUploadForm.fileId}"
                href="${pageContext.request.contextPath}/download/tmp/${f:u(fileUploadForm.fileId)}">
                ${f:h(fileUploadForm.fileName)} </a>
            <form:hidden path="fileName" />
            <form:hidden path="fileId" />
            <br>

            Description : ${f:h(fileUploadForm.description)}
            <form:hidden path="description" />
            <br>

            <%-- submit buttons --%>
            <form:button name="redo">Back</form:button>
            <form:button>Upload</form:button>
        </form:form>
    </div>
</body>
</html>
