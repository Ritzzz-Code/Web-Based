<%-- 
    Document   : rewards
    Created on : 4 May 2026, 3:04:13 pm
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- CRITICAL FIX: Added taglib directive so the application compiles JSTL expressions like <c:forEach> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>EcoLoop | Rewards</title>
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
                    <a href="RewardsServlet" class="nav-link fw-bold text-success">Rewards</a>
                </div>
            </div>
        </nav>

        <div class="container">

            <%-- Displays point error notifications --%>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
                    ⚠️ <strong>Error:</strong> ${sessionScope.errorMsg}
                    <c:remove var="errorMsg" scope="session"/>
                </div>
            </c:if>

            <div class="row mb-4">
                <div class="col-12">
                    <div class="eco-card d-flex justify-content-between align-items-center bg-success text-white">
                        <div>
                            <%-- DYNAMIC FIX: Binding point balance explicitly to servlet attribute tracking --%>
                            <h2 class="mb-0 fw-bold">${balance} <small class="fs-6 fw-normal">EcoPoints</small></h2>
                            <p class="mb-0 text-white-50">Current Balance</p>
                        </div>

                        <form action="RewardsServlet" method="POST" class="d-flex gap-2 align-items-center">
                            <input type="hidden" name="action" value="updateGoal">
                            <label class="text-white-50 mb-0">Monthly Goal:</label>
                            <%-- DYNAMIC FIX: Binding input placeholder to database context value '${goal}' --%>
                            <input type="number" name="monthlyGoal" class="form-control form-control-sm" style="width: 80px;" value="${goal}">
                            <button type="submit" class="btn btn-sm btn-light text-success fw-bold">Save</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row gap-4">
                <div class="col-md-5">
                    <h5 class="mb-3 text-success fw-bold">Available Rewards</h5>

                    <div class="eco-card mb-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-1">RM10 Grocery Voucher</h6>
                                <small class="text-muted">500 Points</small>
                            </div>
                            <form action="RewardsServlet" method="POST">
                                <input type="hidden" name="action" value="createClaim">
                                <input type="hidden" name="rewardId" value="R01">
                                <button type="submit" class="btn btn-sm btn-outline-success">Claim</button>
                            </form>
                        </div>
                    </div>

                    <div class="eco-card">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-1">Free Reusable Coffee Cup</h6>
                                <small class="text-muted">1200 Points</small>
                            </div>
                            <form action="RewardsServlet" method="POST">
                                <input type="hidden" name="action" value="createClaim">
                                <input type="hidden" name="rewardId" value="R02">
                                <button type="submit" class="btn btn-sm btn-outline-success">Claim</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <h5 class="mb-3 text-success fw-bold">My Active Vouchers</h5>
                    <div class="eco-card bg-white border border-success border-opacity-25">
                        <div class="table-responsive">
                            <table class="table align-middle border-bottom-0 mb-0">
                                <tbody id="activeVouchersTable">
                                    <c:forEach items="${vouchers}" var="v">
                                        <tr>
                                            <td>
                                                <strong>${v.rewardName}</strong><br>
                                                <small class="text-muted">Code: ${v.voucherCode}</small>
                                            </td>
                                            <td class="text-end">
                                                <form action="RewardsServlet" method="POST" class="d-inline" onsubmit="return confirm('Refund this voucher?');">
                                                    <input type="hidden" name="action" value="deleteClaim">
                                                    <input type="hidden" name="claimId" value="${v.claimId}">
                                                    <!-- FIXED INCONSISTENCY: Hidden input field matches Integer parsing parameters -->
                                                    <input type="hidden" name="pointsSpent" value="${v.pointsSpent}">
                                                    <button type="submit" class="btn btn-sm text-danger text-decoration-underline border-0 bg-transparent">Remove (Refund)</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty vouchers}">
                                        <tr><td class="text-center text-muted">Your active wallet is empty.</td></tr>
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