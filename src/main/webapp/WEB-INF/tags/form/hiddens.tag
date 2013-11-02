<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://terasoluna.org/form-functions" prefix="ff"%>
<%@ attribute name="modelAttribute" type="java.lang.String" required="true"%>

<%-- remove current nestedPath.--%>
<c:set var="backupNestedPath" value="${nestedPath}" scope="page" />
<c:remove var="nestedPath" scope="request" />

<%-- output hidden tags. --%>
<spring:nestedPath path="${modelAttribute}">
    <c:forEach var="path" items="${ff:paths(requestScope[modelAttribute])}">
        <form:hidden path="${path}" />
    </c:forEach>
</spring:nestedPath>

<%-- redo nestedPath.--%>
<c:if test="${backupNestedPath!=null}">
    <c:set var="nestedPath" value="${backupNestedPath}" scope="request" />
</c:if>
<c:remove var="backupNestedPath" scope="page" />
