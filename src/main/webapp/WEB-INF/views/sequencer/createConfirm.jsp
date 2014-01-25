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
            <form:form id="createForm" action="${pageContext.request.contextPath}/sequencer/create" method="post"
                modelAttribute="createForm">
                <div>
                    <form:label path="title">Title</form:label>
                    :
                    <form:input path="title" readonly="true" />
                </div>
                <div>
                    <form:label path="overview">Overview</form:label>
                    :
                    <form:textarea path="overview" readonly="true" />
                </div>
                <div>
                    <form:label path="content">Content</form:label>
                    :
                    <form:textarea path="content" readonly="true" />
                </div>
                <div>
                    <form:label path="author">Author</form:label>
                    :
                    <form:input path="author" readonly="true" />
                </div>
                <div>
                    <form:label path="usingSequencer">Using Sequencer</form:label>
                    :
                    <form:checkbox path="usingSequencer" disabled="true" />
                </div>
                <form:hidden path="usingSequencer" />
                <div class="formButtonArea">
                    <form:button name="redo" class="btn btn-primary">Back</form:button>
                    <form:button class="btn btn-primary">Create</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
