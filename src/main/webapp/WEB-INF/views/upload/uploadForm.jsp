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
        <h1>File Upload Form Screen</h1>

        <%-- global message area --%>
        <t:messagesPanel />

        <form:form action="${pageContext.request.contextPath}/upload/flow" method="post" enctype="multipart/form-data"
            modelAttribute="fileUploadForm">

            <%-- file upload form --%>
            <c:choose>
                <%-- if file already selected --%>
                <c:when test="${ !empty fileUploadForm.fileName}">
                    Upload File : <form:button name="delete">Delete</form:button>
                    ${f:h(fileUploadForm.fileName)}
                    <form:hidden path="fileName" />
                    <form:hidden path="fileId" />
                    <br>
                </c:when>
                <%-- if file not selected --%>
                <c:otherwise>
                    <form:label path="file">Upload File</form:label> : 
                    <form:input type="file" path="file" />
                    <form:errors path="file" /><br>
                </c:otherwise>
            </c:choose>
            <form:label path="description">Description</form:label> : 
            <form:input path="description" />
            <form:errors path="description" /><br>

            <%-- submit buttons --%>
            <c:choose>
                <c:when test="${ !empty fileUploadForm.fileName}">
                   <form:button name="confirm">Confirm</form:button>
                </c:when>
                <c:otherwise>
                   <form:button name="upload">Upload</form:button>
                </c:otherwise>
            </c:choose>
            
        </form:form>
    </div>
</body>
</html>
