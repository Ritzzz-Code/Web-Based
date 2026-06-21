<%-- 
    Document   : login
    Created on : 21 Jun 2026, 9:19:24 am
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EcoLoop | Sign In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="text-center mb-4">
                    <h2 class="text-success fw-bold">🌱 EcoLoop</h2>
                    <p class="text-muted">Neighborhood Recycling Management System</p>
                </div>

                <%-- Dynamic Error Feedback Panel --%>
                <c:if test="${not empty sessionScope.loginError}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ⚠️ <strong>Login Failed:</strong> ${sessionScope.loginError}
                        <%-- Flush attribute instantly so it doesn't persist on manual refresh --%>
                        <c:remove var="loginError" scope="session"/>
                    </div>
                </c:if>

                <div class="eco-card bg-white p-4 shadow-sm">
                    <h4 class="mb-3 fw-bold text-success text-center">Account Login</h4>
                    
                    <%-- Targets your LoginServlet POST handler routing --%>
                    <form action="LoginServlet" method="POST">
                        <div class="mb-3">
                            <label class="form-label text-muted">Username</label>
                            <input type="text" name="username" class="form-control" placeholder="Enter username" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-muted">Password</label>
                            <input type="password" name="password" class="form-control" placeholder="Enter password" required>
                        </div>
                        <button type="submit" class="btn-eco-primary w-100 mt-2">Log In</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
