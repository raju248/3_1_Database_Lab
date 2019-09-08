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
    float earning;

    public ParkingSpot(int spotOwnerId, int status, float rent, String address, float rating, float earning) {
        this.spotOwnerId = spotOwnerId;
        this.status = status;
        this.rent = rent;
        this.address = address;
        this.rating = rating;
        this.earning = earning;
    }

    ParkingSpot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public float getEarning() {
        return earning;
    }

    public void setEarning(float earning) {
        this.earning = earning;
    }
    
    
    
}
