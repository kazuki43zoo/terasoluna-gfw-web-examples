<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://terasoluna.org/form-functions" prefix="ff"%>

<%-- attributes.--%>
<%@ attribute name="modelAttributes" type="java.lang.String" required="true"%>

<%-- remove current nestedPath.--%>
<c:set var="backupNestedPath" value="${nestedPath}" scope="page" />
<c:remove var="nestedPath" scope="request" />

<%-- output hidden tags. --%>
<c:forEach var="modelAttribute" items="${modelAttributes}">
    <spring:nestedPath path="${fn:trim(modelAttribute)}">
        <c:forEach var="path" items="${ff:paths(requestScope[fn:trim(modelAttribute)])}">
            <form:hidden path="${path}" />
        </c:forEach>
    </spring:nestedPath>
</c:forEach>

<%-- redo nestedPath.--%>
<c:if test="${backupNestedPath!=null}">
    <c:set var="nestedPath" value="${backupNestedPath}" scope="request" />
</c:if>
<c:remove var="backupNestedPath" scope="page" />
