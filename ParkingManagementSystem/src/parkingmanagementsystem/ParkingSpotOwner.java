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
public class ParkingSpotOwner {
    int userId;
    int status;
    int SpotOwerId;

    public ParkingSpotOwner(int userId, int status) {
        this.userId = userId;
        this.status = status;
    }

    public ParkingSpotOwner() {
        
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSpotOwerId() {
        return SpotOwerId;
    }

    public void setSpotOwerId(int SpotOwerId) {
        this.SpotOwerId = SpotOwerId;
    }
    
    
    
}
