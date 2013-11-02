<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Confirm Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
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
                <label>Title</label> : ${f:h(titleForm.title)}
                <br>
                <label>Overview</label> : ${f:h(overviewForm.overview)}
                <br>
                <label>Content</label> : ${f:h(contentForm.content)}
                <br>
                <label>Author</label> : ${f:h(authorForm.author)}
                <br>
                <form:label path="consent">consent</form:label> : <form:checkbox path="consent" />
                <form:errors path="consent" />
                <br>
                <div class="formButtonArea">
                    <form:button name="redoAuthor" class="btn btn-primary">Back</form:button>
                    <form:button class="btn btn-primary">Create</form:button>
                </div>
                <tform:hiddens modelAttribute="titleForm" />
                <tform:hiddens modelAttribute="overviewForm" />
                <tform:hiddens modelAttribute="contentForm" />
                <tform:hiddens modelAttribute="authorForm" />
            </form:form>
        </div>
    </div>
</body>
</html>
