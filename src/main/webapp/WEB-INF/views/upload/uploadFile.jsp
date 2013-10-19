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
        <h1>File Upload Screen</h1>

        <%-- global message area --%>
        <t:messagesPanel />

        <form:form action="${pageContext.request.contextPath}/upload/single" method="post" enctype="multipart/form-data"
            modelAttribute="fileUploadForm">
            
            <%-- upload form --%>
            <form:label path="file">Upload File</form:label> : 
            <form:input type="file" path="file" />
            <form:errors path="file" /><br>

            <form:label path="description">Description</form:label> : 
            <form:input path="description" />
            <form:errors path="description" /><br>

            <%-- submit button --%>
            <form:button>Upload</form:button>

        </form:form>
    </div>
</body>
</html>
