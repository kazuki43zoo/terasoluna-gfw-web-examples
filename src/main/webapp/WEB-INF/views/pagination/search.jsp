<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Search Screen</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$("#size").on("change", function() {
			$("#paginationSearchForm").submit();
		});
		$("input[name='_csrf']").remove();
	});
</script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Search Screen</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <div id="searchFormArea">
            <form:form id="paginationSearchForm" action="${pageContext.request.contextPath}/pagination/search"
                method="get" modelAttribute="searchForm">
                <form:label path="title">Title</form:label> : <form:input path="title" />
                <span>(Under 30 Chars)</span>
                <form:errors path="title" />
                <br>
                <form:label path="publishedDate">Published Date</form:label> : <form:input path="publishedDate" />
                <span>(yyyyMMdd)</span>
                <form:errors path="publishedDate" />
                <br>
                <form:button class="btn btn-primary">Search</form:button>
                    Maximum display number :
                    <form:select path="size">
                    <form:option value="">default</form:option>
                    <form:option value="1">1</form:option>
                    <form:option value="2">2</form:option>
                    <form:option value="5">5</form:option>
                </form:select>
            </form:form>
        </div>
        <div id="searchResultArea">
            <c:if test="${not empty page && 0 < page.numberOfElements}">
                <table class="table table-hover">
                    <tr>
                        <th>No</th>
                        <th>Title</th>
                        <th>Overview</th>
                        <th>Published Date</th>
                        <th>Author</th>
                    </tr>
                    <c:forEach var="article" items="${page.content}" varStatus="rowStatus">
                        <tr>
                            <td>${(page.number * page.size) + rowStatus.count}</td>
                            <td>${f:h(article.title)}</td>
                            <td>${f:h(article.overview)}</td>
                            <td><fmt:formatDate value="${article.publishedDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${f:h(article.author)}</td>
                        </tr>
                    </c:forEach>
                </table>
                <div id="paginationArea">
                    <t:pagination page="${page}" queryTmpl="page={page}&${f:query(searchForm)}"
                        outerElementClass="pagination" disabledHref="javascript:void(0);" />
                    <div>Total : ${f:h(page.totalPages)} Pages</div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>