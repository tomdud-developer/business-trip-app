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
                  <label for="perKilometer" class="input-group-text">Per km</label>
                  <input type="number" step="0.01" id="perKilometer" name="perKilometer" value="${reimbursement.perKilometer}" />
              </div>
          </div>
          <button type="submit" class="btn btn-success">Save</button>
      </form>
  </div>

  <script src="jquery-3.7.0.min.js"></script>
  <script src="scripts2.js"></script>
</body>
</html>
