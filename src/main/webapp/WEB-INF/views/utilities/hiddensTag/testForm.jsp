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
                    <div>
                        <form:label path="simpleMap[key1]">Key1</form:label>
                        :
                        <form:input path="simpleMap[key1]" />
                        <form:errors path="simpleMap[key1]" />
                    </div>
                    <div>
                        <form:label path="simpleMap[key2]">Key2</form:label>
                        :
                        <form:input path="simpleMap[key2]" />
                        <form:errors path="simpleMap[key2]" />
                    </div>
                </div>
                <div id="nestedSubFormMap">
                    <h3>Nested Sub Form Map</h3>
                    <h4>Nested Sub Form 1</h4>
                    <spring:nestedPath path="nestedSubFormMap[subForm1]">
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
                    <h4>Nested Sub Form 2</h4>
                    <spring:nestedPath path="nestedSubFormMap[subForm2]">
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
                    <h4>Nested Sub Form 1</h4>
                    <spring:nestedPath path="nestedSubForms[0]">
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
                    <h4>Nested Sub Form 2</h4>
                    <spring:nestedPath path="nestedSubForms[1]">
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
                <div class="formButtonArea">
                    <form:button name="confirm" class="btn btn-primary">Confirm</form:button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
