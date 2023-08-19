<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navbar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg bg-primary" style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <span class="logo">Business Trip Reimbursement Calculation Application</span>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="dashboard.jsp">Dashboard</a>
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
