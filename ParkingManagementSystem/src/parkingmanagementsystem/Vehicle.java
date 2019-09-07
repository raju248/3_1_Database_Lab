/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

/**
 *
 * @author Tech Land
 */
public class Vehicle {
 
    int vehicleOwnerId;
    String vehicleLicenseNo;
    String vehicleModel;
    int vehicleStatus; // 0 = not parked 1 = parked;

    public Vehicle(int vehicleOwnerId, String vehicleLicenseNo, String vehicleModel, int vehicleStatus) {
        this.vehicleOwnerId = vehicleOwnerId;
        this.vehicleLicenseNo = vehicleLicenseNo;
        this.vehicleModel = vehicleModel;
        this.vehicleStatus = vehicleStatus;
    }

    public int getVehicleOwnerId() {
        return vehicleOwnerId;
    }

    public void setVehicleOwnerId(int vehicleOwnerId) {
        this.vehicleOwnerId = vehicleOwnerId;
    }

    

    public String getVehicleLicenseNo() {
        return vehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        this.vehicleLicenseNo = vehicleLicenseNo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    
    
    public int getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(int vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
    
    
        
}
