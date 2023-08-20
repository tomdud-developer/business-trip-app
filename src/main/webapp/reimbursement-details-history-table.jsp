<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>Reimbursement details modification history table</title>
      <link rel="stylesheet" href="styles/bootstrap.min.css">
      <link rel="stylesheet" href="styles/styles.css">
  </head>
  <body>
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
            <th scope="col">Total reimbursement limit status</th>
            <th scope="col">Total reimbursementDetails limit, $</th>
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
  </body>
</html>
