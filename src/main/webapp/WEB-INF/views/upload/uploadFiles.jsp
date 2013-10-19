<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Files Upload Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">

        <%-- screen name --%>
        <h1>Files Upload Screen</h1>

        <%-- global message area --%>
        <t:messagesPanel />

        <form:form action="${pageContext.request.contextPath}/upload/multiple" method="post" enctype="multipart/form-data"
            modelAttribute="filesUploadForm">
            
            <%-- global validation error message area --%>
            <form:errors element="div" path="uploadUploadForms" />

            <%-- file upload form --%>
            <%-- file 1 --%>
            <form:label path="uploadUploadForms[0].file">Upload File</form:label> : 
            <form:input type="file" path="uploadUploadForms[0].file" />
            <form:errors path="uploadUploadForms[0].file" /><br>
            
            <form:label path="uploadUploadForms[0].description">Description</form:label> : 
            <form:input path="uploadUploadForms[0].description" />
            <form:errors path="uploadUploadForms[0].description" /><br>

            <br>

            <%-- file 2 --%>
            <form:label path="uploadUploadForms[1].file">Upload File</form:label> : 
            <form:input type="file" path="uploadUploadForms[1].file" />
            <form:errors path="uploadUploadForms[1].file" /><br>

            <form:label path="uploadUploadForms[1].description">Description</form:label> : 
            <form:input path="uploadUploadForms[1].description" />
            <form:errors path="uploadUploadForms[1].description" /><br>

            <%-- submit button --%>
            <br>
            <form:button>Upload</form:button>
            <br>
        </form:form>
    </div>
</body>
</html>
