<%-- 
    Document   : collection
    Created on : 4 May 2026, 2:56:52 pm
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>EcoLoop | Schedule Collection</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Link to your new system styles -->
        <link href="css/styles.css" rel="stylesheet"> 
    </head>
    <body>

        <!-- Simple Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light bg-white mb-4 shadow-sm">
            <div class="container">
                <a class="navbar-brand text-success fw-bold" href="#">🌱 EcoLoop</a>
                <div class="d-flex gap-3">
                    <a href="WasteLogServlet" class="nav-link">Waste Log</a>
                    <a href="CollectionServlet" class="nav-link fw-bold text-success">Pick-ups</a>
                    <a href="RewardsServlet" class="nav-link">Rewards</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <h3 class="mb-4 text-success fw-bold">Manage Collections</h3>

            <div class="row gap-4">
                <!-- CREATE: Request Pick-up Form -->
                <div class="col-md-4">
                    <div class="eco-card">
                        <h5 class="mb-3">Request Pick-up</h5>
                        <form action="CollectionServlet" method="POST">
                            <input type="hidden" name="action" value="create">

                            <div class="mb-3">
                                <label class="form-label text-muted">Preferred Date</label>
                                <input type="date" name="pickupDate" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-muted">Time Slot</label>
                                <select name="timeSlot" class="form-select" required>
                                    <option value="Morning (8AM - 12PM)">Morning (8AM - 12PM)</option>
                                    <option value="Afternoon (1PM - 5PM)">Afternoon (1PM - 5PM)</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-muted">Estimated Load</label>
                                <select name="loadSize" class="form-select">
                                    <option value="Small">Small (1-2 Bags)</option>
                                    <option value="Medium">Medium (3-5 Bags)</option>
                                    <option value="Large">Large (Furniture/Appliances)</option>
                                </select>
                            </div>

                            <button type="submit" class="btn-eco-primary w-100">Schedule Collection</button>
                        </form>
                    </div>
                </div>

                <!-- RETRIEVE/UPDATE/DELETE: Requests Table -->
                <div class="col-md-7">
                    <div class="eco-card">
                        <h5 class="mb-3">Your Schedule</h5>
                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>Date & Time</th>
                                        <th>Load</th>
                                        <th>Status</th>
                                        <th class="text-end">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${collections}" var="req">
                                    <tr>
                                        <td>
                                            <strong>${req.pickupDate}</strong><br>
                                            <small class="text-muted">${req.timeSlot}</small>
                                        </td>
                                        <td>${req.loadSize}</td>
                                        <td>
                                            <span class="${req.status == 'Pending' ? 'status-pending' : 'status-completed'}">${req.status}</span>
                                        </td>
                                        <td class="text-end">
                                            <form action="CollectionServlet" method="POST" class="d-inline" onsubmit="return confirm('Cancel this collection request?');">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="requestId" value="${req.requestId}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger">Cancel</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty collections}">
                                    <tr><td colspan="4" class="text-center text-muted">No scheduled pick-ups found.</td></tr>
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
