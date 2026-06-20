/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Timestamp;

public class WasteLog {
    private int logId;
    private int userId;
    private String materialType;
    private double weightKg;
    private Timestamp logDate;

    // Default Constructor
    public WasteLog() {}

    // Constructor for creating a new log (without logId and date)
    public WasteLog(int userId, String materialType, double weightKg) {
        this.userId = userId;
        this.materialType = materialType;
        this.weightKg = weightKg;
    }

    // Getters and Setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public Timestamp getLogDate() { return logDate; }
    public void setLogDate(Timestamp logDate) { this.logDate = logDate; }
}