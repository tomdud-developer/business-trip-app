<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles.css">
</head>
<body>
  <h1>Admin Panel</h1>
  <div class="container" id="adminFormContainer">
      <form action="admin-panel/update" method="post">
          <div class="border p-3">
              <h3 class="form-label">Reimbursement details</h3>
              <div class="mb-3">
                  <div class="input-group">
                      <label for="perKilometer" class="input-group-text">Car mileage</label>
                      <input type="number" step="0.01" class="form-control" id="perKilometer" name="perKilometer" value="${reimbursement.perKilometer}" />
                      <label for="perKilometer" class="input-group-text">$/km</label>

                      <label for="perDay" class="input-group-text">Daily allowance</label>
                      <input type="number" step="0.01" class="form-control" id="perDay" name="perDay" value="${reimbursement.perDay}" />
                      <label for="perDay" class="input-group-text">$/day</label>
                  </div>
              </div>
              <div class="mb-3">
                  <div class="input-group">
                      <div class="input-group-text">
                          <label style="margin-right: 5px">Maximum mileage limit enable/disable</label>
                          <input class="form-check-input mt-0" type="checkbox" name="enableMileageLimit" id="enableMileageLimit" value="${reimbursement.enableMileageLimit}">
                      </div>
                      <input type="number" class="form-control" step="0.01" id="mileageLimit" name="mileageLimit" value="${reimbursement.mileageLimit}" />
                      <label for="perKilometer" class="input-group-text">km</label>
                  </div>
                  <div class="input-group">
                      <div class="input-group-text">
                          <label style="margin-right: 5px">Total reimbursement limit enable/disable</label>
                          <input class="form-check-input mt-0" type="checkbox" name="enableTotalReimbursementLimit" id="enableTotalReimbursementLimit" value="${reimbursement.enableTotalReimbursementLimit}">
                      </div>
                      <input type="number" step="0.01" class="form-control" id="totalReimbursementLimit" name="totalReimbursementLimit" value="${reimbursement.totalReimbursementLimit}" />
                      <label for="perKilometer" class="input-group-text">$</label>
                  </div>
              </div>

              <button type="submit" class="btn btn-success save-button-long">Save</button>
          </div>
      </form>

      <div class="border p-3">
          <h3 class="form-label">Reimbursement details modification history</h3>
          <table id="reimbursementHistoryModificationList" class="table table-striped">
              <thead class="thead-dark">
                  <tr>
                      <th scope="col">Modification date</th>
                      <th scope="col">Car mileage, $/km</th>
                      <th scope="col">Daily allowance, $/day</th>
                      <th scope="col">Mileage limit status</th>
                      <th scope="col">Mileage limit, km</th>
                      <th scope="col">Total reimbursement status</th>
                      <th scope="col">Total reimbursement limit, $</th>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach var="modification" items="${reimbursementModificationList}">
                      <tr>
                          <td>
                              <c:out value="${fn:substring(modification.settingDate, 0, 16)}" />
                          </td>
                          <td>${modification.perKilometer}</td>
                          <td>${modification.perDay}</td>
                          <td>${modification.enableMileageLimit}</td>
                          <td>${modification.mileageLimit}</td>
                          <td>${modification.enableTotalReimbursementLimit}</td>
                          <td>${modification.totalReimbursementLimit}</td>
                      </tr>
                  </c:forEach>
              </tbody>
          </table>
      </div>

      <div class="border p-3">
          <div class="row">
              <div class="col">
                  <h3 class="form-label">Receipt Types</h3>
              </div>
              <div class="col-auto">
                  <a href="receipt-type/insert" class="btn btn-primary">Add new Receipt type</a>
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
                          <a href="receipt-type/edit?name=<c:out value='${receiptType.name}' />">Edit</a>
                          <a href="receipt-type/delete?name=<c:out value='${receiptType.name}' />">Delete</a>
                      </td>
                  </tr>
              </c:forEach>
              </tbody>
          </table>
      </div>

  </div>

  <script src="jquery-3.7.0.min.js"></script>
  <script src="scripts2.js"></script>
</body>
</html>
