<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin panel</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container border p-3" id="adminFormContainer">
        <div class="text-center">
            <h1>Admin Panel</h1>
        </div>
        <form action="admin-panel/update" method="post">
          <div class="border p-3">
              <h3 class="form-label">Reimbursement details</h3>
              <div class="mb-3">
                  <div class="input-group">
                      <label for="perKilometer" class="input-group-text">Car mileage</label>
                      <input type="number" step="0.01" class="form-control" id="perKilometer" name="perKilometer" value="${reimbursementDetails.perKilometer}" />
                      <label for="perKilometer" class="input-group-text">$/km</label>

                      <label for="perDay" class="input-group-text">Daily allowance</label>
                      <input type="number" step="0.01" class="form-control" id="perDay" name="perDay" value="${reimbursementDetails.perDay}" />
                      <label for="perDay" class="input-group-text">$/day</label>
                  </div>
              </div>
              <div class="mb-3">
                  <div class="input-group">
                      <div class="input-group-text">
                          <label style="margin-right: 5px">Maximum mileage limit enable/disable</label>
                          <input class="form-check-input mt-0" type="checkbox" name="enableMileageLimit" id="enableMileageLimit" <c:if test="${reimbursementDetails.enableMileageLimit}">checked</c:if>>
                      </div>
                      <input type="number" class="form-control" step="0.01" id="mileageLimit" name="mileageLimit" value="${reimbursementDetails.mileageLimit}" />
                      <label for="perKilometer" class="input-group-text">km</label>
                  </div>
                  <div class="input-group">
                      <div class="input-group-text">
                          <label style="margin-right: 5px">Total reimbursementDetails limit enable/disable</label>
                          <input class="form-check-input mt-0" type="checkbox" name="enableTotalReimbursementLimit" id="enableTotalReimbursementLimit" <c:if test="${reimbursementDetails.enableTotalReimbursementLimit}">checked</c:if>>
                      </div>
                      <input type="number" step="0.01" class="form-control" id="totalReimbursementLimit" name="totalReimbursementLimit" value="${reimbursementDetails.totalReimbursementLimit}" />
                      <label for="perKilometer" class="input-group-text">$</label>
                  </div>
              </div>

              <button type="submit" class="btn btn-success save-button-long">Save</button>
          </div>
        </form>

        <jsp:include page="reimbursement-details-history-table.jsp" />

        <div class="border p-3">
          <div class="row">
              <div class="col">
                  <h3 class="form-label">Receipt Types</h3>
              </div>
              <div class="col-auto">
                  <a href="receipt-type-insert" class="btn btn-primary">Add new Receipt type</a>
              </div>
          </div>
          <table id="receiptTypesTable" class="table table-striped">
              <thead class="thead-dark">
              <tr>
                  <th scope="col">Receipt type name</th>
                  <th scope="col">Limit enable status</th>
                  <th scope="col">Limit for single receipt</th>
                  <th scope="col">Action</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="receiptType" items="${receiptTypes}">
                  <tr>
                      <td>${receiptType.name}</td>
                      <td>${receiptType.enableLimit}</td>
                      <td>${receiptType.limit}</td>
                      <td>
                          <a href="receipt-type-edit?name=<c:out value='${receiptType.name}' />">Edit</a>
                          <a href="receipt-type-delete?name=<c:out value='${receiptType.name}' />">Delete</a>
                      </td>
                  </tr>
              </c:forEach>
              </tbody>
          </table>
        </div>

    </div>

    <script src="scripts/jquery-3.7.0.min.js"></script>
    <script src="scripts/scripts.js"></script>
</body>
</html>
