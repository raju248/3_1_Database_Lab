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
public class ParkingSpot {
    int spotOwnerId;
    int status;
    float rent;
    String address;
    float rating;

    public ParkingSpot(int spotOwnerId, int status, float rent, String address, float rating) {
        this.spotOwnerId = spotOwnerId;
        this.status = status;
        this.rent = rent;
        this.address = address;
        this.rating = rating;
    }

    public int getSpotOwnerId() {
        return spotOwnerId;
    }

    public void setSpotOwnerId(int spotOwnerId) {
        this.spotOwnerId = spotOwnerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getRent() {
        return rent;
    }

    public void setRent(float rent) {
        this.rent = rent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    
    
}
