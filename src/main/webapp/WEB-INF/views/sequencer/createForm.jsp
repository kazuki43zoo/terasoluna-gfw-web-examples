<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Form Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Create Form Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="createForm" action="${pageContext.request.contextPath}/sequencer/create"
                method="post" modelAttribute="createForm">
                <form:label path="title">Title</form:label> : <form:input path="title" />
                <span>(100 characters or less)</span>
                <form:errors path="title" />
                <br>
                <form:label path="overview">Overview</form:label> : <form:textarea path="overview"/>
                <span>(1,000 characters or less)</span>
                <form:errors path="overview" />
                <br>
                <form:label path="content">Content</form:label> : <form:textarea path="content" />
                <span>(10,000 characters or less)</span>
                <form:errors path="content" />
                <br>
                <form:label path="author">Author</form:label> : <form:input path="author" />
                <span>(100 characters or less)</span>
                <form:errors path="author" />
                <br>
                <form:label path="usingSequencer">Using Sequencer</form:label> : <form:checkbox path="usingSequencer"
                    value="true" />
                <form:errors path="usingSequencer" />
                <br>
                <div class="formButtonArea">
                    <form:button name="confirm" class="btn btn-primary">Confirm</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
