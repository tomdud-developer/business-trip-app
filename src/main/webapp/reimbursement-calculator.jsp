<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tomdud.businesstripapp.businesstripapp.entity.Reimbursement" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>

<head>
    <title>Business Trip Reimbursement Calculation Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <c:if test="true">
        <p>true</p>
    </c:if>
    <c:if test="false">
        <p>false</p>
    </c:if>

    <% response.getWriter().println(((Reimbursement)request.getAttribute("reimbursement")).getPerKilometer()); %>
    <h1>Business Trip Reimbursement Calculator</h1>
        <div class="container" id="calculatorFormContainer">
            <div class="mb-3">
                <h3 class="form-label">Business trip duration</h3>
                <div class="input-group">
                    <label for="tripStartDate" class="input-group-text" id="basic-addon1">Trip start date</label>
                    <input type="date" class="form-control" id="tripStartDate" name="tripStartDate">
                </div>
                <div class="input-group">
                    <input type="date" class="form-control" id="tripEndDate" name="tripEndDate">
                    <span class="input-group-text">Choose end date or trip duration in days</span>
                    <input type="number" id="numberOfDays" name="numberOfDays" min="1">
                </div>
            </div>

            <div class="mb-3">
                <h3 class="form-label">Business trip expenses</h3>
                <form action="calculate-reimbursement/add-receipt" method="post">
                    <div class="input-group">
                            <label for="receiptValue" class="input-group-text">Receipt value</label>
                            <input type="number" step="0.01" id="receiptValue" name="receiptValue">
                            <label for="receiptValue" class="input-group-text">$</label>
                            <select id="receiptType" name="receiptType" class="form-select" aria-label="taxi">
                                <c:forEach items="${receiptTypes}" var="type">
                                    <option value="${type.name}">
                                            ${type.name} -
                                            <c:choose>
                                                <c:when test="${type.enableLimit}">
                                                    limit - ${type.limit}$
                                                </c:when>
                                                <c:otherwise>
                                                    limit disabled
                                                </c:otherwise>
                                            </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                            <button type="submit" id="addNewReceiptButton">Add new Receipt</button>
                    </div>
                </form>

                <table id="receiptList" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Receipt name</th>
                            <th scope="col">Receipt value</th>
                            <th scope="col">Receipt limit</th>
                            <th scope="col">Reimbursement</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="receipt" items="${receiptList}">
                        <tr>
                            <td>${receipt.receiptType.name}</td>
                            <td>${receipt.value}</td>
                            <c:choose>
                                <c:when test="${receipt.receiptType.enableLimit}">
                                    <td>${receipt.receiptType.limit}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>Limit disabled</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${receipt.reimbursement}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


            <label for="inputPassword" class="form-label">Password</label>
            <input type="password" id="inputPassword" class="form-control" aria-describedby="passwordHelpBlock">
            <div id="passwordHelpBlock" class="form-text">
                Your password must be 8-20 characters long, contain letters and numbers, and must not contain spaces, special characters, or emoji.
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    <script src="jquery-3.7.0.min.js"></script>
    <script src="scripts2.js"></script>
</body>
</html>
