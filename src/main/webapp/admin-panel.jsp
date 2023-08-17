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
          <div class="mb-3">
              <label class="form-label">Reimbursement details</label>
              <div class="input-group">
                  <label for="perKilometer" class="input-group-text">Car mileage</label>
                  <input type="number" step="0.01" id="perKilometer" name="perKilometer" value="${reimbursement.perKilometer}" />
                  <label for="perKilometer" class="input-group-text">$/km</label>

                  <label for="perDay" class="input-group-text">Daily allowance</label>
                  <input type="number" step="0.01" id="perDay" name="perDay" value="${reimbursement.perDay}" />
                  <label for="perDay" class="input-group-text">$/day</label>
              </div>
          </div>

          <div class="mb-3">
              <label class="form-label">Reimbursement limits</label>
              <div class="input-group">
                  <div class="input-group-text">
                      <label style="margin-right: 5px">Maximum mileage limit enable/disable</label>
                      <input class="form-check-input mt-0" type="checkbox" value="${reimbursement.enableMileageLimit}">
                  </div>
                  <input type="number" step="0.01" id="mileageLimit" name="mileageLimit" value="${reimbursement.mileageLimit}" />
                  <label for="perKilometer" class="input-group-text">km</label>
              </div>
              <div class="input-group">
                  <div class="input-group-text">
                      <label style="margin-right: 5px">Total reimbursement limit enable/disable</label>
                      <input class="form-check-input mt-0" type="checkbox" value="${reimbursement.enableTotalReimbursementLimit}">
                  </div>
                  <input type="number" step="0.01" id="totalReimbursementLimit" name="totalReimbursementLimit" value="${reimbursement.totalReimbursementLimit}" />
                  <label for="perKilometer" class="input-group-text">$</label>
              </div>
          </div>

          <button type="submit" class="btn btn-success">Save</button>
      </form>

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

  <script src="jquery-3.7.0.min.js"></script>
  <script src="scripts2.js"></script>
</body>
</html>