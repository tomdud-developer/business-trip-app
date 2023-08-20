<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>

<head>
    <title>Business Trip Reimbursement Calculation Page</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
        <div class="container border p-3" id="calculatorFormContainer">
            <div class="text-center">
                <h1>Calculator</h1>
            </div>
            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Business trip duration</h3>
                    <form action="calculate-reimbursement/modify-duration" id="calculate-reimbursement/modify-duration" method="post">
                        <div class="input-group">
                            <label for="tripStartDate" class="input-group-text" id="basic-addon1">Trip start date</label>
                            <input type="date" class="form-control" id="tripStartDate" name="tripStartDate" value="${reimbursementSummary.tripDuration.startDate}">
                            <span class="input-group-text">Current rate: ${reimbursementSummary.reimbursementDetails.perDay}$/day</span>
                        </div>
                        <div class="input-group">
                            <input type="date" class="form-control" id="tripEndDate" name="tripEndDate" value="${reimbursementSummary.tripDuration.endDate}">
                            <span class="input-group-text">Choose end date or trip duration in days</span>
                            <input type="number" class="form-control" id="numberOfDays" name="numberOfDays" min="1" value="${reimbursementSummary.tripDuration.duration}">
                        </div>
                        <input type="hidden" name="tripFieldChanged" id="tripFieldChanged">
                    </form>
                    <form action="calculate-reimbursement/addDisabledDay" method="post">
                        <div class="input-group">
                            <label for="disabledDayDate" class="input-group-text">Disabled day date</label>
                            <input type="date" class="form-control" id="disabledDayDate" name="disabledDayDate" value="${reimbursementSummary.tripDuration.startDate}">
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
                        <c:forEach var="date" items="${reimbursementSummary.tripDuration.disabledDays}">
                            <tr>
                                <td>${date}</td>
                                <td>
                                    <form action="calculate-reimbursement/deleteDisabledDay" method="post">
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
                        <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForTripDuration}" pattern="0.00" var="formattedTotalAllowanceForTripDuration" />
                        Total allowance = ${formattedTotalAllowanceForTripDuration}$
                    </h5>
                </div>
            </div>

            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Business trip expenses</h3>
                    <form action="calculate-reimbursement/add-receipt" method="post">
                        <div class="input-group">
                                <label for="receiptValue" class="input-group-text" >Receipt value</label>
                                <input class="form-control" type="number" step="0.01" id="receiptValue" name="receiptValue" min="0.01" value="0.01">
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
                        <c:forEach var="receipt" items="${reimbursementSummary.receiptList}" varStatus="loop">
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
                                <td>
                                    <form action="calculate-reimbursement/delete-receipt" method="post">
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
                            <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForTripExpenses}" pattern="0.00" var="formattedTotalAllowanceForTripExpenses" />
                            Total expenses reimbursement = ${formattedTotalAllowanceForTripExpenses}$
                        </h5>
                    </div>
                </div>
            </div>

            <div class="border p-3">
                <div class="mb-3">
                    <h3 class="form-label">Car usage</h3>
                    <form action="calculate-reimbursement/modify-car-usage" id="calculate-reimbursement/modify-car-usage" method="post">
                        <div class="input-group">
                            <span class="input-group-text">Distance</span>
                            <input type="number" step="0.001" class="form-control" id="distance" name="distance" min="1" value="${reimbursementSummary.carUsage.distance}">
                            <span class="input-group-text">km</span>
                            <span class="input-group-text">
                                Limit of distance:
                                <c:choose>
                                    <c:when test="${reimbursementSummary.reimbursementDetails.enableMileageLimit}">
                                        ${reimbursementSummary.reimbursementDetails.mileageLimit}km
                                    </c:when>
                                    <c:otherwise>
                                        Disabled
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <span class="input-group-text">Current rate: ${reimbursementSummary.reimbursementDetails.perKilometer}$/km</span>
                        </div>
                    </form>
                </div>
                <div class="d-flex align-items-center">
                    <div class="flex-grow-1"></div>
                    <h5 class="mb-0 ms-3 pb-1 border-bottom">
                        <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForCarUsage}" pattern="0.00" var="formattedTotalAllowanceForCarUsage" />
                        Total car usage reimburse = ${formattedTotalAllowanceForCarUsage}$
                    </h5>
                </div>
            </div>

            <div style="height: 30px"></div>
            <div class="d-flex align-items-center">
                <div class="flex-grow-1"></div>
                <h6 class="mb-0 ms-3 pb-1 border-bottom">
                    <c:choose>
                        <c:when test="${reimbursementSummary.reimbursementDetails.enableTotalReimbursementLimit}">
                            Total reimbursement limit - ${reimbursementSummary.reimbursementDetails.totalReimbursementLimit}$
                        </c:when>
                        <c:otherwise>
                            Total reimbursement limit is disabled
                        </c:otherwise>
                    </c:choose>
                </h6>
            </div>
            <div class="d-flex align-items-center">
                <div class="flex-grow-1"></div>
                <h2 class="mb-0 ms-3 pb-1 border-bottom">
                    <fmt:formatNumber value="${reimbursementSummary.totalAllowance}" pattern="0.00" var="formattedTotalAllowance" />
                    Total reimbursement = ${formattedTotalAllowance}$
                </h2>
            </div>
            <div class="d-flex align-items-center">
                <div class="flex-grow-1"></div>
                <form action="calculate-reimbursement/send-to-consideration" method="post">
                    <input type="number" id="checkSum" name="checkSum" value="${reimbursementSummary.totalAllowance}" style="display: none">
                    <button type="submit" class="btn btn-success">Send request for reimbursement to payroll department</button>
                </form>
            </div>

        </div>
    <script src="scripts/jquery-3.7.0.min.js"></script>
    <script src="scripts/scripts2.js"></script>
</body>
</html>
