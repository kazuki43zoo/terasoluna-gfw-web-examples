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
		// bind event to size item.
		$("#size").on("change", function() {
			$("#paginationSearchForm").submit();
		});
		// bind event to sort item.
		$("#sort").on("change", function() {
			$("#paginationSearchForm").submit();
		});
		// disabled csrf item.
		$("input[name='_csrf']").attr("disabled", "disabled");
	});
</script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Article Search Screen</h1>

        <%-- global message area --%>
        <t:messagesPanel />

        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="paginationSearchForm" action="${pageContext.request.contextPath}/pagination/search"
                method="get" modelAttribute="searchForm">
                <div>
                    <form:label path="title">Title</form:label>
                    :
                    <form:input path="title" />
                    <span>(30 characters or less)</span>
                    <form:errors path="title" />
                </div>
                <div>
                    <form:label path="publishedDate">Published Date</form:label>
                    :
                    <form:input path="publishedDate" />
                    <span>(yyyyMMdd)</span>
                    <form:errors path="publishedDate" />
                </div>
                <div>
                    <form:label path="size">Max display</form:label>
                    :
                    <form:select path="size" items="${CL_ARTICLE_SEARCH_MAX_DISPLAY_NUMBER}" />
                </div>
                <div>
                    <form:label path="sort">Default sort</form:label>
                    :
                    <form:select path="sort" items="${CL_ARTICLE_SEARCH_DEFAULT_SORT}" />
                </div>
                <div class="formButtonArea">
                    <form:button class="btn btn-primary">Search</form:button>
                </div>
            </form:form>
        </div>

        <%-- Search Result area --%>
        <div class="subArea">
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
