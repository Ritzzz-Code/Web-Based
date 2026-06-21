<%-- 
    Document   : admin-dashboard
    Created on : 21 Jun 2026, 8:56:33 am
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EcoLoop | Admin Console</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="text-success fw-bold">📋 Global Collection Operations</h3>
            <a href="ProfileServlet" class="btn btn-sm btn-outline-secondary">Back to Profile</a>
        </div>

        <div class="eco-card bg-white">
            <h5 class="mb-3">Active Neighborhood Pick-up Requests</h5>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>User ID</th>
                            <th>Target Date</th>
                            <th>Time Slot</th>
                            <th>Load Size</th>
                            <th>Current Status</th>
                            <th class="text-end">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${allRequests}" var="req">
                            <tr>
                                <td><strong>User #${req.userId}</strong></td>
                                <td>${req.pickupDate}</td>
                                <td>${req.timeSlot}</td>
                                <td>${req.loadSize}</td>
                                <td>
                                    <span class="${req.status == 'Pending' ? 'status-pending' : 'status-completed'}">${req.status}</span>
                                </td>
                                <td class="text-end">
                                    <c:if test="${req.status == 'Pending'}">
                                        <form action="AdminDashboardServlet" method="POST" class="d-inline">
                                            <input type="hidden" name="requestId" value="${req.requestId}">
                                            <button type="submit" class="btn btn-sm btn-success">Complete Task</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>