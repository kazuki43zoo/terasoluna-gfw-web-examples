<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Form Screen for testing FormHiddensTag</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/bootstrap-3.0.0.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
    <div id="wrapper">
        <%-- screen name --%>
        <h1>Form Screen for testing FormHiddensTag</h1>
        <%-- global message area --%>
        <t:messagesPanel />
        <%-- Search Form area --%>
        <div class="subArea">
            <form:form id="createForm" action="${pageContext.request.contextPath}/utilities/hiddensTag/test"
                method="post" modelAttribute="rootForm">
                <h2>Root Form</h2>
                <div>
                    <form:label path="stringItem">String Item</form:label>
                    :
                    <form:input path="stringItem" />
                    <form:errors path="stringItem" />
                </div>
                <div>
                    <form:label path="integerItem">Integer Item</form:label>
                    :
                    <form:input path="integerItem" />
                    <form:errors path="integerItem" />
                </div>
                <div>
                    <form:label path="dateItem">Date Item</form:label>
                    :
                    <form:input path="dateItem" />
                    <span>(yyyyMMdd)</span>
                    <form:errors path="dateItem" />
                </div>
                <div id="nestedSubForm">
                    <h3>Nested Sub Form</h3>
                    <spring:nestedPath path="nestedSubForm">
                        <div>
                            <form:label path="stringItem">String Item</form:label>
                            :
                            <form:input path="stringItem" />
                            <form:errors path="stringItem" />
                        </div>
                        <div>
                            <form:label path="integerItem">Integer Item</form:label>
                            :
                            <form:input path="integerItem" />
                            <form:errors path="integerItem" />
                        </div>
                        <div>
                            <form:label path="dateItem">Date Item</form:label>
                            :
                            <form:input path="dateItem" />
                            <span>(yyyy-MM-dd)</span>
                            <form:errors path="dateItem" />
                        </div>
                    </spring:nestedPath>
                </div>
                <div id="simpleMap">
                    <h3>Simple Map</h3>
                    <c:forTokens var="value" items="1,2" delims=",">
                        <div>
                            <form:label path="simpleMap[key${value}]">Key${f:h(value)}</form:label>
                            :
                            <form:input path="simpleMap[key${value}]" />
                            <form:errors path="simpleMap[key${value}]" />
                        </div>
                    </c:forTokens>
                </div>
                <div id="nestedSubFormMap">
                    <h3>Nested Sub Form Map</h3>
                    <c:forTokens var="value" items="1,2" delims=",">
                        <h4>Nested Sub Form ${f:h(value)}</h4>
                        <spring:nestedPath path="nestedSubFormMap[subForm${value}]">
                            <div>
                                <form:label path="stringItem">String Item</form:label>
                                :
                                <form:input path="stringItem" />
                                <form:errors path="stringItem" />
                            </div>
                            <div>
                                <form:label path="integerItem">Integer Item</form:label>
                                :
                                <form:input path="integerItem" />
                                <form:errors path="integerItem" />
                            </div>
                            <div>
                                <form:label path="dateItem">Date Item</form:label>
                                :
                                <form:input path="dateItem" />
                                <span>(yyyy-MM-dd)</span>
                                <form:errors path="dateItem" />
                            </div>
                        </spring:nestedPath>
                    </c:forTokens>
                </div>
                <div id="simpleList">
                    <h3>Simple List</h3>
                    <div>
                        <form:label path="simpleList">Hobby</form:label>
                        :
                        <form:checkboxes items="${CL_HOBBY}" path="simpleList" />
                        <form:errors path="simpleList" />
                    </div>
                </div>
                <div id="nestedSubForms">
                    <h3>Nested Sub Form List</h3>
                    <c:forTokens var="value" items="1,2" delims="," varStatus="rowStatus">
                        <h4>Nested Sub Form ${f:h(value)}</h4>
                        <spring:nestedPath path="nestedSubForms[${rowStatus.index}]">
                            <div>
                                <form:label path="stringItem">String Item</form:label>
                                :
                                <form:input path="stringItem" />
                                <form:errors path="stringItem" />
                            </div>
                            <div>
                                <form:label path="integerItem">Integer Item</form:label>
                                :
                                <form:input path="integerItem" />
                                <form:errors path="integerItem" />
                            </div>
                            <div>
                                <form:label path="dateItem">Date Item</form:label>
                                :
                                <form:input path="dateItem" />
                                <span>(yyyy-MM-dd)</span>
                                <form:errors path="dateItem" />
                            </div>
                        </spring:nestedPath>
                    </c:forTokens>
                </div>
                <div id="simpleMapList" class="inputTable">
                    <h3>Simple Map List</h3>
                    <c:set var="rows" value="1,2,3" />
                    <c:set var="cols" value="1,2,3" />
                    <table>
                        <tr>
                            <th>No</th>
                            <c:forTokens var="col" items="${cols}" delims=",">
                                <th>Key${f:h(col)}</th>
                            </c:forTokens>
                        </tr>
                        <c:forTokens var="row" items="${rows}" delims="," varStatus="rowStatus">
                            <tr>
                                <td>${f:h(rowStatus.count)}</td>
                                <c:forTokens var="col" items="${cols}" delims=",">
                                    <td><form:input path="simpleMapList[${rowStatus.index}][key${col}]" /></td>
                                </c:forTokens>
                            </tr>
                        </c:forTokens>
                    </table>
                </div>
                <div id="rowFormArray" class="inputTable">
                    <h3>Row Form Array</h3>
                    <c:set var="rows" value="1,2,3" />
                    <table class="valueTable">
                        <tr>
                            <th>No</th>
                            <th>Int Item</th>
                            <th>Long Item</th>
                            <th>Double Item</th>
                            <th>Date Time Item</th>
                            <th>Local Time Item</th>
                            <th>DateMidnight Item</th>
                        </tr>
                        <c:forTokens var="row" items="${rows}" delims="," varStatus="rowStatus">
                            <tr>
                                <spring:nestedPath path="rowFormArray[${rowStatus.index}]">
                                    <td>${f:h(rowStatus.count)}</td>
                                    <td><form:input path="intItem" /> <form:errors path="intItem" /></td>
                                    <td><form:input path="longItem" /> <form:errors path="longItem" /></td>
                                    <td><form:input path="doubleItem" /> <form:errors path="doubleItem" /></td>
                                    <td><form:input path="dateTimeItem" /> <form:errors path="dateTimeItem" /></td>
                                    <td><form:input path="localTimeItem" /> <form:errors path="localTimeItem" /></td>
                                    <td><form:input path="dateMidnightItem" /> <form:errors
                                            path="dateMidnightItem" /></td>
                                </spring:nestedPath>
                            </tr>
                        </c:forTokens>
                    </table>
                </div>
                <div class="formButtonArea">
                    <form:button name="confirm" class="btn btn-primary">Confirm</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
