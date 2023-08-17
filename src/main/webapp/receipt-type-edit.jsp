<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Receipt-type-edit-form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <c:if test="${receiptType != null}"><h1>Receipt Type (${receiptType.name}) Edit Form</h1></c:if>
    <c:if test="${receiptType == null}"><h1>Receipt Type Insert Form</h1></c:if>
    <div class="container" id="receiptFormContainer">
        <c:if test="${receiptType != null}"><form action="update" method="post"></c:if>
        <c:if test="${receiptType == null}"><form action="insert" method="post"></c:if>
            <div class="border p-3">
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
                            <input class="form-check-input mt-0 form-check-input-lg" type="checkbox" id="enableLimit" name="enableLimit" value="${receiptType.enableLimit != null ? receiptType.enableLimit : false}">
                            <label for="limit" class="input-group-text">Name</label>
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
