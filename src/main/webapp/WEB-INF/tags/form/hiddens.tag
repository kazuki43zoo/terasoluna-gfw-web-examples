<%@ tag body-content="scriptless" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://terasoluna.org/form-functions" prefix="ff"%>

<%-- attributes.--%>
<%@ attribute name="modelAttributes" type="java.lang.String" required="true"%>

<%-- remove current nestedPath.--%>
<c:set var="backupNestedPath" value="${nestedPath}" />
<c:remove var="nestedPath" scope="request" />

<%-- output hidden tags. --%>
<c:forEach var="modelAttribute" items="${modelAttributes}">
    <c:set var="modelAttribute" value="${fn:trim(modelAttribute)}" />
    <spring:eval var="object" expression="${modelAttribute}" />

    <c:choose>

        <c:when test="${object == null}">
            <%-- if object is null, do nothing. --%>
        </c:when>

        <c:when test="${ff:isCollection(object)}">
            <%-- if object is collection --%>
            <c:forEach var="elementObject" items="${object}" varStatus="rowStatus">
                <c:choose>

                    <c:when test="${elementObject == null}">
                        <%-- if element object is null, do nothing. --%>
                    </c:when>

                    <c:when test="${ff:isSimpleValueType(elementObject)}">
                        <%-- if element object in collection is simple value --%>
                        <form:hidden path="${modelAttribute}[${rowStatus.index}]" />
                    </c:when>

                    <c:otherwise>
                        <%-- if element object in collection is java bean --%>
                        <spring:nestedPath path="${modelAttribute}[${rowStatus.index}]">
                            <c:forEach var="path" items="${ff:paths(elementObject)}">
                                <form:hidden path="${path}" />
                            </c:forEach>
                        </spring:nestedPath>
                    </c:otherwise>

                </c:choose>
            </c:forEach>
        </c:when>

        <c:when test="${ff:isMap(object)}">
            <%-- if object is map --%>
            <c:forEach var="elementEntry" items="${object}">
                <c:choose>

                    <c:when test="${elementEntry.value == null}">
                        <%-- if element object is null, do nothing. --%>
                    </c:when>

                    <c:when test="${ff:isSimpleValueType(elementEntry.value)}">
                        <%-- if element object in map is simple value --%>
                        <form:hidden path="${modelAttribute}[${elementEntry.key}]" />
                    </c:when>

                    <c:otherwise>
                        <%-- if element object in map is java bean --%>
                        <spring:nestedPath path="${modelAttribute}[${elementEntry.key}]">
                            <c:forEach var="path" items="${ff:paths(elementEntry.value)}">
                                <form:hidden path="${path}" />
                            </c:forEach>
                        </spring:nestedPath>
                    </c:otherwise>

                </c:choose>
            </c:forEach>
        </c:when>

        <c:when test="${ff:isSimpleValueType(object)}">
            <%-- if object is simple value --%>
            <form:hidden path="${modelAttribute}" />
        </c:when>

        <c:otherwise>
            <%-- if object is java bean --%>
            <spring:nestedPath path="${modelAttribute}">
                <c:forEach var="path" items="${ff:paths(object)}">
                    <form:hidden path="${path}" />
                </c:forEach>
            </spring:nestedPath>
        </c:otherwise>

    </c:choose>

</c:forEach>

<%-- redo nestedPath.--%>
<c:if test="${backupNestedPath!=null}">
    <c:set var="nestedPath" value="${backupNestedPath}" scope="request" />
</c:if>
<c:remove var="backupNestedPath" />
