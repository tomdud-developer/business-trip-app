<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles2.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <h1>Dashboard</h1>

    <h1>Business Trip Reimbursement Calculator</h1>
    <div class="container border p-3" id="calculatorFormContainer">
        <div class="border p-3">
            <div class="mb-3">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>Reimbursement requests</h4>
                    <button class="btn btn-success">Add New Reimbursement</button>
                </div>
                <table id="disabledDaysTable" class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Trip Start date</th>
                        <th scope="col">Trip End date</th>
                        <th scope="col">Duration</th>
                        <th scope="col" class="border-end">Disabled days</th>
                        <th scope="col">Receipts</th>
                        <th scope="col" class="border-end">Total for receipts</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty reimbursementSummaryList}">
                            <c:forEach var="summary" items="${reimbursementSummaryList}">
                                <tr>
                                    <td>${summary.tripDuration.startDate}</td>
                                    <td>${summary.tripDuration.endDate}</td>
                                    <td>${summary.tripDuration.duration}</td>
                                    <td class="border-end">
                                        <div class="d-flex align-items-center">
                                            <p class="mb-0">${summary.tripDuration.disabledDays.size()} days</p>
                                            <button class="btn btn-link ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${summary.id}" aria-expanded="false" aria-controls="collapse${summary.id}">
                                                Show Dates
                                            </button>
                                        </div>
                                        <div class="collapse" id="collapse${summary.id}">
                                            <ul>
                                                <c:forEach var="disabledDay" items="${summary.tripDuration.disabledDays}">
                                                    <li>${disabledDay}</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td class="border-end">
                                        <div class="d-flex align-items-center">
                                            <p class="mb-0">${summary.receiptList.size()} Receipts </p>
                                            <button class="btn btn-link ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${summary.id}" aria-expanded="false" aria-controls="collapse${summary.id}">
                                                Show receipts
                                            </button>
                                        </div>
                                        <div class="collapse" id="collapse${summary.id}">
                                            <ul>
                                                <c:forEach var="receipt" items="${summary.receiptList}">
                                                    <li>Value: ${receipt.value}$ - ${receipt.receiptType}$ - ${receipt.reimbursement}</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td>${summary.sumReimbursementFromReceipts()}$</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</body>
</html>
