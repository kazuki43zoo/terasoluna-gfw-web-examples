<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Complete Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Create Complete Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form action="${pageContext.request.contextPath}/utilities/hiddensTag/create" method="get">
                <button name="form" class="btn btn-link">Continue to create ...</button>
            </form>
            <c:if test="${article != null}">
                <form action="${pageContext.request.contextPath}/pagination/search" method="get">
                    <input type="hidden" name="title" value="${article.title}">
                    <button class="btn btn-link">Search the created article</button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>
