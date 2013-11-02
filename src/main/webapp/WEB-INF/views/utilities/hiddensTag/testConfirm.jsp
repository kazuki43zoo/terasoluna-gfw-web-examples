<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Confirm Screen for testing FormHiddensTag</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Confirm Screen for testing FormHiddensTag</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="createForm" action="${pageContext.request.contextPath}/utilities/hiddensTag/test"
                method="post" modelAttribute="rootForm">
                <h2>Root Form</h2>
                <div>
                    <label>String Item</label> : ${f:h(rootForm.stringItem)}
                </div>
                <div>
                    <label>Integer Item</label> : ${f:h(rootForm.integerItem)}
                </div>
                <div>
                    <label>Date Item</label> :
                    <fmt:formatDate value="${rootForm.dateItem}" pattern="yyyy/MM/dd" />
                </div>
                <div id="nestedSubForm">
                    <h3>Nested Sub Form</h3>
                    <c:set var="subForm" value="${rootForm.nestedSubForm}" />
                    <div>
                        <label>String Item</label> : ${f:h(subForm.stringItem)}
                    </div>
                    <div>
                        <label>Integer Item</label> : ${f:h(subForm.integerItem)}
                    </div>
                    <div>
                        <label>Date Item</label> :
                        <fmt:formatDate value="${subForm.dateItem}" pattern="yyyy/MM/dd" />
                    </div>
                </div>
                <div id="simpleMap">
                    <h3>Simple Map</h3>
                    <c:forEach var="entry" items="${rootForm.simpleMap}">
                        <div>
                            <label>${f:h(entry.key)}</label> : ${f:h(entry.value)}
                        </div>
                    </c:forEach>
                </div>
                <div id="nestedSubFormMap">
                    <h3>Nested Sub Form Map</h3>
                    <c:forEach var="subFormEntry" items="${rootForm.nestedSubFormMap}" varStatus="rowStatus">
                        <h4>Nested Sub Form ${rowStatus.count}</h4>
                        <c:set var="subForm" value="${subFormEntry.value}" />
                        <div>
                            <label>String Item</label> : ${f:h(subForm.stringItem)}
                        </div>
                        <div>
                            <label>Integer Item</label> : ${f:h(subForm.integerItem)}
                        </div>
                        <div>
                            <label>Date Item</label> :
                            <fmt:formatDate value="${subForm.dateItem}" pattern="yyyy/MM/dd" />
                        </div>
                    </c:forEach>
                </div>
                <div id="simpleList">
                    <h3>Simple List</h3>
                    <div>
                        <form:label path="simpleList">Hobby</form:label>
                        :
                        <c:forEach var="hobbyCode" items="${rootForm.simpleList}" varStatus="rowStatus">
                            <span class="checkboxLabel">${f:h(CL_HOBBY[hobbyCode])}</span>
                        </c:forEach>
                    </div>
                </div>
                <div id="nestedSubFormList">
                    <h3>Nested Sub Form List</h3>
                    <c:forEach var="subForm" items="${rootForm.nestedSubForms}" varStatus="rowStatus">
                        <h4>Nested Sub Form ${rowStatus.count}</h4>
                        <div>
                            <label>String Item</label> : ${f:h(subForm.stringItem)}
                        </div>
                        <div>
                            <label>Integer Item</label> : ${f:h(subForm.integerItem)}
                        </div>
                        <div>
                            <label>Date Item</label> :
                            <fmt:formatDate value="${subForm.dateItem}" pattern="yyyy/MM/dd" />
                        </div>
                    </c:forEach>
                </div>
                <tform:hiddens modelAttributes="rootForm" />
                <div class="formButtonArea">
                    <form:button name="redo" class="btn btn-primary">Back</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
