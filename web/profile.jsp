<%-- 
    Document   : profile
    Created on : 21 Jun 2026, 8:55:39 am
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EcoLoop | My Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-white mb-4 shadow-sm">
        <div class="container">
            <a class="navbar-brand text-success fw-bold" href="#">🌱 EcoLoop</a>
            <div class="d-flex gap-3">
                <a href="WasteLogServlet" class="nav-link">Waste Log</a>
                <a href="CollectionServlet" class="nav-link">Pick-ups</a>
                <a href="RewardsServlet" class="nav-link">Rewards</a>
                <a href="ProfileServlet" class="nav-link fw-bold text-success">Account Profile</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                
                <%-- Conditional Authorization Block: Only displays if user has ADMIN/STAFF permissions --%>
                <c:if test="${userObj.role == 'ADMIN' || userObj.role == 'STAFF'}">
                    <div class="alert alert-info d-flex justify-content-between align-items-center mb-4" role="alert">
                        <div>
                            ⚙️ <strong>Staff Control Panel:</strong> You are logged in as an Administrative Operator.
                        </div>
                        <a href="AdminDashboardServlet" class="btn btn-sm btn-info fw-bold">Manage Customer Schedules</a>
                    </div>
                </c:if>

                <div class="eco-card bg-white p-4">
                    <h4 class="mb-3 text-success fw-bold">Account Settings</h4>
                    <form action="ProfileServlet" method="POST">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label text-muted">Username</label>
                                <input type="text" class="form-control" value="${userObj.username}" disabled>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label text-muted">Access Level Role</label>
                                <input type="text" class="form-control" value="${userObj.role}" disabled>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-muted">Email Address</label>
                            <input type="email" name="email" class="form-control" value="${userObj.email}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-muted">Contact Number (For pick-up alerts)</label>
                            <input type="text" name="phone" class="form-control" value="${userObj.phoneNumber}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-muted">Physical Collection Address</label>
                            <textarea name="address" class="form-control" rows="3">${userObj.address}</textarea>
                        </div>
                        <button type="submit" class="btn-eco-primary w-100">Save Changes</button>
                    </form>
                </div>
                
            </div>
        </div>
    </div>
</body>
</html>
