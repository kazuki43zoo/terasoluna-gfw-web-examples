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
        <h1>File Upload Complete Screen</h1>

        <%-- global message area --%>
        <t:messagesPanel />

        <%-- uploaded file information --%>
        Upload File : <a target="downloadUploadFile" href="${pageContext.request.contextPath}/download/${f:u(uploadFileInfo.fileId)}">${f:h(uploadFileInfo.fileName)}</a><br>
        Description : ${f:h(uploadFileInfo.description)}<br>

        <br>
        
        <%-- link for to be continued --%>
        <a href="${pageContext.request.contextPath}/upload/flow">Continue</a>

    </div>
</body>
</html>
