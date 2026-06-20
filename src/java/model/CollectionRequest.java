/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adamr
 */
package model;

import java.sql.Date;

public class CollectionRequest {
    private int requestId;
    private int userId;
    private Date pickupDate;
    private String timeSlot;
    private String loadSize;
    private String status;

    public CollectionRequest() {}

    public CollectionRequest(int userId, Date pickupDate, String timeSlot, String loadSize) {
        this.userId = userId;
        this.pickupDate = pickupDate;
        this.timeSlot = timeSlot;
        this.loadSize = loadSize;
    }

    // Getters and Setters
    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getPickupDate() { return pickupDate; }
    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getLoadSize() { return loadSize; }
    public void setLoadSize(String loadSize) { this.loadSize = loadSize; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
