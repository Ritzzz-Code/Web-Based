<%-- 
    Document   : rewards
    Created on : 4 May 2026, 3:04:13 pm
    Author     : adamr
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EcoLoop | Rewards</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>

<!-- Navigation -->
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
    <!-- User Stats Header (RETRIEVE) -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="eco-card d-flex justify-content-between align-items-center bg-success text-white">
                <div>
                    <h2 class="mb-0 fw-bold">1,250 <small class="fs-6 fw-normal">EcoPoints</small></h2>
                    <p class="mb-0 text-white-50">Current Balance</p>
                </div>
                
                <!-- UPDATE: Set Monthly Goal -->
                <form action="RewardsServlet" method="POST" class="d-flex gap-2 align-items-center">
                    <input type="hidden" name="action" value="updateGoal">
                    <label class="text-white-50 mb-0">Monthly Goal:</label>
                    <input type="number" name="monthlyGoal" class="form-control form-control-sm" style="width: 80px;" value="2000">
                    <button type="submit" class="btn btn-sm btn-light text-success fw-bold">Save</button>
                </form>
            </div>
        </div>
    </div>

    <div class="row gap-4">
        <!-- CREATE: Available Rewards Catalog -->
        <div class="col-md-5">
            <h5 class="mb-3 text-success fw-bold">Available Rewards</h5>
            
            <!-- Reward Item 1 -->
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

            <!-- Reward Item 2 -->
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

        <!-- RETRIEVE/DELETE: My Digital Wallet -->
        <div class="col-md-6">
            <h5 class="mb-3 text-success fw-bold">My Active Vouchers</h5>
            <div class="eco-card bg-white border border-success border-opacity-25">
                <div class="table-responsive">
                    <table class="table align-middle border-bottom-0 mb-0">
                        <tbody>
                            <!-- Example Claimed Reward Row -->
                            <tr>
                                <td>
                                    <strong>RM5 LRT Pass</strong><br>
                                    <small class="text-muted">Code: ECO-LRT-992</small>
                                </td>
                                <td class="text-end">
                                    <form action="RewardsServlet" method="POST" class="d-inline">
                                        <input type="hidden" name="action" value="deleteClaim">
                                        <input type="hidden" name="claimId" value="C992">
                                        <button type="submit" class="btn btn-sm text-danger text-decoration-underline border-0 bg-transparent">Remove (Refund)</button>
                                    </form>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>