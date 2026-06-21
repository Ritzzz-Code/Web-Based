<%-- 
    Document   : header
    Created on : 21 Jun 2026, 9:08:33?am
    Author     : adamr
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-light bg-white mb-4 shadow-sm">
    <div class="container">
        <a class="navbar-brand text-success fw-bold" href="ProfileServlet">? EcoLoop</a>
        <div class="d-flex gap-3 align-items-center">
            <a href="ProfileServlet" class="nav-link">Profile</a>
            <a href="WasteLogServlet" class="nav-link">Waste Log</a>
            <a href="CollectionServlet" class="nav-link">Pick-ups</a>
            <a href="RewardsServlet" class="nav-link">Rewards</a>
            <a href="LoginServlet?action=logout" class="btn btn-sm btn-outline-danger ms-2">Sign Out</a>
        </div>
    </div>
</nav>
