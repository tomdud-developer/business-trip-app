<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tomdud.businesstripapp.businesstripapp.util.Role" %>
<%@ page import="java.util.Set" %>

<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="stylesheet" href="styles/bootstrap.min.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container border p-3" id="dashboardContainer">
        <div class="text-center">
            <h1>Dashboard</h1>
        </div>
        <div class="text-center">
            <h3>You are logged in as ${username} and have ${roles} role</h3>
        </div>
        <div class="border p-3">
            <div class="mb-3">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>Reimbursement requests</h4>
                    <%
                        Set<Role> roles = (Set<Role>) session.getAttribute("roles");
                        if (roles != null && roles.contains(Role.USER)) {
                    %>
                        <a href="calculate-reimbursement-new-calculation" class="btn btn-success">Add New Reimbursement</a>
                    <%
                        }
                    %>
                </div>
                <table id="disabledDaysTable" class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col" class="border-end">UserId</th>
                        <th scope="col" class="border-end">Request time</th>
                        <th scope="col" class="border-end">Details</th>
                        <th scope="col">Trip Start date</th>
                        <th scope="col">Trip End date</th>
                        <th scope="col">Duration</th>
                        <th scope="col">Disabled days</th>
                        <th scope="col" class="border-end">Total allowance for trip duration</th>
                        <th scope="col">Receipts</th>
                        <th scope="col" class="border-end">Total for receipts</th>
                        <th scope="col">Distance</th>
                        <th scope="col" class="border-end">Total for car usage</th>
                        <th scope="col" class="border-end">Total reimbursement</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty reimbursementSummaryList}">
                            <c:forEach var="reimbursementSummary" items="${reimbursementSummaryList}">
                                <tr>
                                    <td class="border-end">${reimbursementSummary.userId}</td>
                                    <td class="border-end">
                                        <c:out value="${fn:substring(reimbursementSummary.creationDateTime, 0, 16)}" />
                                    </td>
                                    <td class="border-end">
                                        <div class="d-flex align-items-center">
                                            <button class="btn btn-link ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-reimbursement-details-${reimbursementSummary.id}" aria-expanded="false" aria-controls="collapse-reimbursement-details-${reimbursementSummary.id}">
                                                Show details
                                            </button>
                                        </div>
                                        <div class="collapse" id="collapse-reimbursement-details-${reimbursementSummary.id}">
                                            <ul>
                                                <li>Daily allowance rate: ${reimbursementSummary.reimbursementDetails.perDay} $/day</li>
                                                <li>Car usage rate: ${reimbursementSummary.reimbursementDetails.perKilometer} $/km</li>
                                                <li>Distance limit:
                                                    <c:choose>
                                                        <c:when test="${reimbursementSummary.reimbursementDetails.enableMileageLimit}">
                                                            ${reimbursementSummary.reimbursementDetails.mileageLimit}km
                                                        </c:when>
                                                        <c:otherwise>
                                                            Limit disabled
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li>Total allowance limit:
                                                    <c:choose>
                                                        <c:when test="${reimbursementSummary.reimbursementDetails.enableTotalReimbursementLimit}">
                                                            ${reimbursementSummary.reimbursementDetails.totalReimbursementLimit}$
                                                        </c:when>
                                                        <c:otherwise>
                                                            Limit disabled
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>

                                            </ul>
                                        </div>
                                    </td>
                                    <td>${reimbursementSummary.tripDuration.startDate}</td>
                                    <td>${reimbursementSummary.tripDuration.endDate}</td>
                                    <td>${reimbursementSummary.tripDuration.duration}</td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <p class="mb-0">${reimbursementSummary.tripDuration.disabledDays.size()} days</p>
                                            <button class="btn btn-link ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-disabled-days-${reimbursementSummary.id}" aria-expanded="false" aria-controls="collapse-disabled-days-${reimbursementSummary.id}">
                                                Show Dates
                                            </button>
                                        </div>
                                        <div class="collapse" id="collapse-disabled-days-${reimbursementSummary.id}">
                                            <ul>
                                                <c:forEach var="disabledDay" items="${reimbursementSummary.tripDuration.disabledDays}">
                                                    <li>${disabledDay}</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td class="border-end">
                                        <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForTripDuration}" pattern="0.00" var="formattedTotalAllowanceForTripDuration" />
                                        ${formattedTotalAllowanceForTripDuration}$
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <p class="mb-0">${reimbursementSummary.receiptList.size()} Receipts </p>
                                            <button class="btn btn-link ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-receipts-${reimbursementSummary.id}" aria-expanded="false" aria-controls="collapse-receipts-${reimbursementSummary.id}">
                                                Show receipts
                                            </button>
                                        </div>
                                        <div class="collapse" id="collapse-receipts-${reimbursementSummary.id}">
                                            <ul>
                                                <c:forEach var="receipt" items="${reimbursementSummary.receiptList}">
                                                    <fmt:formatNumber value="${receipt.value}" pattern="0.00" var="formattedReceiptValue" />
                                                    <fmt:formatNumber value="${receipt.reimbursement}" pattern="0.00" var="formattedReceiptReimbursement" />
                                                    <li>Value: ${formattedReceiptValue}$ - ${receipt.receiptType.name} -
                                                        <c:choose>
                                                            <c:when test="${receipt.receiptType.enableLimit}">
                                                                <fmt:formatNumber value="${receipt.receiptType.limit}" pattern="0.00" var="formattedReceiptLimit" />
                                                                Limit ${formattedReceiptLimit}$ -
                                                            </c:when>
                                                            <c:otherwise>
                                                                Limit disabled -
                                                            </c:otherwise>
                                                        </c:choose>
                                                        Reimburse ${formattedReceiptReimbursement}$</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td class="border-end">
                                        <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForTripExpenses}" pattern="0.00" var="formattedTotalAllowanceForTripExpenses" />
                                            ${formattedTotalAllowanceForTripExpenses}$
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${reimbursementSummary.carUsage.distance}" pattern="0.00" var="formattedDistance" />
                                            ${formattedDistance}km
                                    </td>
                                    <td class="border-end">
                                        <fmt:formatNumber value="${reimbursementSummary.totalAllowanceForCarUsage}" pattern="0.00" var="formattedTotalAllowanceForCarUsage" />
                                            ${formattedTotalAllowanceForCarUsage}$
                                    </td>
                                    <td class="border-end">
                                        <fmt:formatNumber value="${reimbursementSummary.totalAllowance}" pattern="0.00" var="formattedTotalAllowance" />
                                            ${formattedTotalAllowance}$
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <jsp:include page="reimbursement-details-history-table.jsp" />

    </div>

    <script src="scripts/bootstrap.bundle.min.js"></script>
</body>
</html>
