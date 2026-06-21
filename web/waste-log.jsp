<%-- 
    Document   : waste-log
    Created on : 4 May 2026, 2:28:17 pm
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>EcoLoop | Waste Log</title>
        <!-- Bootstrap 5 for UI/UX -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Link to your global system styles -->
        <link href="css/styles.css" rel="stylesheet"> 
    </head>
    <body>

        <!-- Navigation Header -->
        <nav class="navbar navbar-expand-lg navbar-light bg-white mb-4 shadow-sm">
            <div class="container">
                <a class="navbar-brand text-success fw-bold" href="#">🌱 EcoLoop</a>
                <div class="d-flex gap-3">
                    <!-- "Waste Log" is highlighted as the active page -->
                    <a href="WasteLogServlet" class="nav-link fw-bold text-success">Waste Log</a>
                    <a href="CollectionServlet" class="nav-link">Pick-ups</a>
                    <a href="RewardsServlet" class="nav-link">Rewards</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <h3 class="mb-4 text-success fw-bold">Waste Tracker</h3>

            <div class="row gap-4">
                <!-- CREATE FORM (Left Side) -->
                <div class="col-md-4">
                    <div class="eco-card">
                        <h5 class="mb-3">Log New Material</h5>
                        <form action="WasteLogServlet" method="POST">
                            <input type="hidden" name="action" value="create">

                            <div class="mb-3">
                                <label class="form-label text-muted">Material Type</label>
                                <select name="materialType" class="form-select" required>
                                    <option value="Plastic">Plastic</option>
                                    <option value="Paper">Paper/Cardboard</option>
                                    <option value="Glass">Glass</option>
                                    <option value="Metal">Metal/Aluminum</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted">Weight (kg)</label>
                                <input type="number" step="0.1" name="weight" class="form-control" placeholder="e.g., 2.5" required>
                            </div>
                            <button type="submit" class="btn-eco-primary w-100">Submit Log</button>
                        </form>
                    </div>
                </div>

                <!-- RETRIEVE/READ TABLE (Right Side) -->
                <div class="col-md-7">
                    <div class="eco-card">
                        <h5 class="mb-3">Recent Logs</h5>
                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>Date</th>
                                        <th>Material</th>
                                        <th>Weight (kg)</th>
                                        <th class="text-end">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${wasteLogs}" var="log">
                                    <tr>
                                        <td>${log.logDate}</td>
                                        <td>
                                            <span class="badge bg-success bg-opacity-25 text-success">${log.materialType}</span>
                                        </td>
                                        <td>${log.weightKg}</td>
                                        <td class="text-end">
                                            <form action="WasteLogServlet" method="POST" class="d-inline" onsubmit="return confirm('Are you sure you want to delete this log?');">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="logId" value="${log.logId}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty wasteLogs}">
                                    <tr><td colspan="4" class="text-center text-muted">No waste logged yet.</td></tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>