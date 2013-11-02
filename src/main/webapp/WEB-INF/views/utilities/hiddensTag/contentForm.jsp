<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Content Form Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Create Content Form Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="createForm" action="${pageContext.request.contextPath}/utilities/hiddensTag/create"
                method="post" modelAttribute="contentForm">
                <div>
                    <form:label path="content">Content</form:label>
                    :
                    <form:textarea path="content" />
                    <span>(10,000 characters or less)</span>
                    <form:errors path="content" />
                </div>
                <tform:hiddens modelAttributes="titleForm, overviewForm, authorForm" />
                <div class="formButtonArea">
                    <form:button name="redoOverview" class="btn btn-primary">Back</form:button>
                    <form:button name="moveAuthor" class="btn btn-primary">Next</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
