<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Receipt-type-edit-form</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container border p-3" id="receiptFormContainer">
        <c:if test="${receiptType != null}"><form action="receipt-type-update" method="post"></c:if>
        <c:if test="${receiptType == null}"><form action="receipt-type-insert" method="post"></c:if>
            <div class="border p-3">
                <div class="text-center">
                    <c:if test="${receiptType != null}"><h1>Receipt Type (${receiptType.name}) Edit Form</h1></c:if>
                    <c:if test="${receiptType == null}"><h1>Receipt Type Insert Form</h1></c:if>
                </div>
                <h3 class="form-label">Receipt details</h3>
                <div class="mb-3">
                    <div class="input-group">
                        <label for="name" class="input-group-text">Name</label>
                        <input type="text" id="name" name="name" value="${receiptType.name != null ? receiptType.name : ''}" />
                        <c:if test="${receiptType != null}">
                            <input type="text" id="orgName" name="orgName" value="${receiptType.name}" style="display: none;"/>
                        </c:if>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">
                            <label style="margin-right: 5px">Limit status</label>
                            <input class="form-check-input mt-0 form-check-input-lg" type="checkbox" id="enableLimit" name="enableLimit" <c:if test="${receiptType.enableLimit}">checked</c:if>>
                            <label for="limit" class="input-group-text">Limit value</label>
                            <input type="number" step="0.01" id="limit" name="limit" value="${receiptType.limit != null ? receiptType.limit : ''}" />
                            <label for="limit" class="input-group-text">$</label>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-success save-button-long">Save</button>
            </div>
        </form>
    </div>
</body>
</html>
