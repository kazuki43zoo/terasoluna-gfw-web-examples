<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Title Form Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Create Title Form Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">

            <form:form id="createForm" action="${pageContext.request.contextPath}/utilities/hiddensTag/create"
                method="post" modelAttribute="titleForm">
                <ul class="breadcrumb">
                    <li class="active">Title</li>
                    <li class="after">Overview</li>
                    <li class="after">Content</li>
                    <li class="after">Author</li>
                    <li class="after">Confirm</li>
                    <li class="after">Complete</li>
                </ul>
                <div>
                    <form:label path="title">Title</form:label>
                    :
                    <form:input path="title" />
                    <span>(100 characters or less)</span>
                    <form:errors path="title" />
                </div>
                <tform:hiddens modelAttributes="overviewForm, contentForm, authorForm" />
                <div class="formButtonArea">
                    <form:button name="moveOverview" class="btn btn-primary">Next</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
