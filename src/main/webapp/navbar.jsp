<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tomdud.businesstripapp.util.Role" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>



<head>
    <title>Navbar</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg bg-primary" style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <span class="logo" style="margin-right: 50px">Business Trip Reimbursement Calculation Application</span>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a href="dashboard" class="btn btn-success px-4 py-2 me-2">
                            <h5 class="mb-0">Dashboard</h5>
                        </a>
                    </li>
                    <%
                        Role role = (Role) session.getAttribute("role");
                        if (role != null && role.equals(Role.USER)) {
                    %>
                        <li class="nav-item">
                            <a href="calculate-reimbursement" class="btn btn-success px-4 py-2 me-2">
                                <h5 class="mb-0">Calculator</h5>
                            </a>
                        </li>
                    <%
                        }
                    %>
                    <%
                        if (role != null && role.equals(Role.ADMIN)) {
                    %>
                        <li class="nav-item">
                            <a href="admin-panel" class="btn btn-success px-4 py-2">
                                <h5 class="mb-0">Admin Panel</h5>
                            </a>
                        </li>
                    <%
                        }
                    %>
                </ul>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <span class="nav-link">You are logged in as: ${username} and have ${role} role</span>
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

    <%
        String error = request.getParameter("error");
        String errorMessage = request.getParameter("errorMessage");
        if ("true".equals(error)) {
            %>
                <div class="alert alert-danger text-center">
                    Error:
                    <%
                        out.println(errorMessage);
                    %>
                </div>
            <%
        }
    %>
</body>

