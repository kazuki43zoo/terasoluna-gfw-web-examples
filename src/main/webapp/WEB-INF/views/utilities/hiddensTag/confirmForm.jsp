<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Confirm Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/common/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Create Confirm Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="createForm" action="${pageContext.request.contextPath}/utilities/hiddensTag/create"
                method="post" modelAttribute="confirmForm">
                <ul class="breadcrumb">
                    <li><form:button class="btn-link" name="redoTitle">Title</form:button></li>
                    <li><form:button class="btn-link" name="redoOverview">Overview</form:button></li>
                    <li><form:button class="btn-link" name="redoContent">Content</form:button></li>
                    <li><form:button class="btn-link" name="redoAuthor">Author</form:button></li>
                    <li class="active">Confirm</li>
                    <li class="after">Complete</li>
                </ul>
                <div>
                    <label>Title</label> : ${f:h(titleForm.title)}
                </div>
                <div>
                    <label>Overview</label> : ${f:h(overviewForm.overview)}
                </div>
                <div>
                    <label>Content</label> : ${f:h(contentForm.content)}
                </div>
                <div>
                    <label>Author</label> : ${f:h(authorForm.author)}
                </div>
                <div>
                    <form:label path="consent">Consent</form:label>
                    :
                    <form:checkbox path="consent" label="Yes" />
                    <form:errors path="consent" />
                </div>
                <tform:hiddens modelAttributes="titleForm, overviewForm, contentForm, authorForm" />
                <div class="formButtonArea">
                    <form:button name="redoAuthor" class="btn btn-primary">Back</form:button>
                    <form:button class="btn btn-primary">Create</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
