<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login form</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title text-center">
                            <span class="logo">Business Trip Reimbursement Calculation Application</span>
                        </h4>
                    </div>
                    <div class="card-body">
                        <form action="login" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="mb-3 text-danger">
                                ${error}
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary" style="width: 200px">Login</button>
                            </div>
                        </form>
                        <div class="alert-success">
                            Available users:
                            <ul>
                                <li>Login: user    Password: user     Role: USER</li>
                                <li>Login: user2   Password: user2    Role: USER</li>
                                <li>Login: admin   Password: admin    Role: ADMIN</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
