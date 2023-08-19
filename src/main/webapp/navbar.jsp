<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navbar</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg bg-primary" style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <span class="logo">Business Trip Reimbursement Calculation Application</span>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a href="dashboard" class="btn btn-success"><h5>Dashboard</h5></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="">Info</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <span class="nav-link">You are logged in as: ${username} and have ${roles} role</span>
                    </li>
                    <li class="nav-item">
                        <form action="logout" method="post">
                            <button type="submit" class="btn btn-secondary">Logout</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
