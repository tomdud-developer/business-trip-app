<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tomdud.businesstripapp.businesstripapp.model.ReimbursementDetails" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>

<head>
    <title>Business Trip Reimbursement Calculation Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles2.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <h1>Business Trip Reimbursement Calculator</h1>
        <div class="container border p-3" id="calculatorFormContainer">
            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Business trip duration</h3>
                    <form action="calculate-reimbursementDetails/modify-duration" id="calculate-reimbursementDetails/modify-duration" method="post">
                        <div class="input-group">
                            <label for="tripStartDate" class="input-group-text" id="basic-addon1">Trip start date</label>
                            <input type="date" class="form-control" id="tripStartDate" name="tripStartDate" value="${tripDuration.startDate}">
                        </div>
                        <div class="input-group">
                            <input type="date" class="form-control" id="tripEndDate" name="tripEndDate" value="${tripDuration.endDate}">
                            <span class="input-group-text">Choose end date or trip duration in days</span>
                            <input type="number" class="form-control" id="numberOfDays" name="numberOfDays" min="1" value="${tripDuration.duration}">
                        </div>
                        <input type="hidden" name="tripFieldChanged" id="tripFieldChanged">
                    </form>
                    <form action="calculate-reimbursementDetails/addDisabledDay" method="post">
                        <div class="input-group">
                            <label for="disabledDayDate" class="input-group-text">Disabled day date</label>
                            <input type="date" class="form-control" id="disabledDayDate" name="disabledDayDate" value="${tripDuration.startDate}">
                            <button type="submit" class="btn btn-primary" id="addNewDisabledDay">Add new disabled day</button>
                        </div>
                    </form>
                    <h5>Disabled days list</h5>
                    <table id="disabledDaysTable" class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">Date</th>
                            <th scope="col">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="date" items="${tripDuration.disabledDays}">
                            <tr>
                                <td>${date}</td>
                                <td>
                                    <form action="calculate-reimbursementDetails/deleteDisabledDay" method="post">
                                        <input type="date" class="form-control" id="disabledDayDateToDelete" name="disabledDayDateToDelete" value="${date}" style="display: none">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="d-flex align-items-center">
                    <div class="flex-grow-1"></div>
                    <h5 class="mb-0 ms-3 pb-1 border-bottom">
                        Total allowance = ${totalAllowance}$
                    </h5>
                </div>
            </div>

            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Business trip expenses</h3>
                    <form action="calculate-reimbursementDetails/add-receipt" method="post">
                        <div class="input-group">
                                <label for="receiptValue" class="input-group-text">Receipt value</label>
                                <input class="form-control" type="number" step="0.01" id="receiptValue" name="receiptValue" min="0.01">
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
                                <button type="submit" class="btn btn-primary" id="addNewReceiptButton">Add new Receipt</button>
                        </div>
                    </form>

                    <table id="receiptList" class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Receipt name</th>
                                <th scope="col">Receipt value</th>
                                <th scope="col">Receipt limit</th>
                                <th scope="col">Reimbursement</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="receipt" items="${receiptList}" varStatus="loop">
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
                                <td>${receipt.reimbursementDetails}</td>
                                <td>
                                    <form action="calculate-reimbursementDetails/delete-receipt" method="post">
                                        <input type="number" class="form-control" id="receiptIndexToDelete" name="receiptIndexToDelete" value="${loop.index}" style="display: none">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="d-flex align-items-center">
                        <div class="flex-grow-1"></div>
                        <h5 class="mb-0 ms-3 pb-1 border-bottom">
                            Total expenses reimbursementDetails = ${expensesTotalReimbursement}$
                        </h5>
                    </div>
                </div>
            </div>

            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Car usage</h3>
                    <form action="calculate-reimbursementDetails/modify-car-usage" id="calculate-reimbursementDetails/modify-car-usage" method="post">
                        <div class="input-group">
                            <span class="input-group-text">Distance</span>
                            <input type="number" step="0.001" class="form-control" id="distance" name="distance" min="1" value="${carUsage.distance}">
                            <span class="input-group-text">km</span>
                            <span class="input-group-text">
                                Limit of distance:
                                <c:choose>
                                    <c:when test="${carUsage.reimbursementDetails.enableMileageLimit}">
                                        ${carUsage.reimbursementDetails.mileageLimit}km
                                    </c:when>
                                    <c:otherwise>
                                        Disabled
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <span class="input-group-text">Current rate: ${carUsage.reimbursementDetails.perKilometer}$/km</span>
                        </div>
                    </form>
                </div>
                <div class="d-flex align-items-center">
                    <div class="flex-grow-1"></div>
                    <h5 class="mb-0 ms-3 pb-1 border-bottom">
                        Total car usage reimburse = ${carUsage.totalReimburse}$
                    </h5>
                </div>
            </div>

            <div style="height: 30px"></div>
            <div class="d-flex align-items-center">
                <div class="flex-grow-1"></div>
                <h2 class="mb-0 ms-3 pb-1 border-bottom">
                    Total reimbursementDetails = ${reimbursementSummary}$
                </h2>
            </div>
            <div class="d-flex align-items-center">
                <div class="flex-grow-1"></div>
                <form action="calculate-reimbursementDetails/send-to-consideration" method="post">
                    <input type="number" id="checkSum" name="checkSum" value="${reimbursementSummary}" style="display: none">
                    <button type="submit" class="btn btn-success">Send request for reimbursementDetails to payroll department</button>
                </form>
            </div>

        </div>
    <script src="jquery-3.7.0.min.js"></script>
    <script src="scripts2.js"></script>
</body>
</html>
