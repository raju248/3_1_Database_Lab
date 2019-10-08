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
public class PermanentParkingSpot {
    String Address;
    int Rent;
    int Guard;
    String PhoneNo;
    int SpotOwnerId;

    String addedDate;
    int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }


    
    public PermanentParkingSpot(String Address, int Rent, int Guard, String PhoneNo, int SpotOwnerId) {
        this.Address = Address;
        this.Rent = Rent;
        this.Guard = Guard;
        this.PhoneNo = PhoneNo;
        this.SpotOwnerId = SpotOwnerId;
    }

    public PermanentParkingSpot() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getRent() {
        return Rent;
    }

    public void setRent(int Rent) {
        this.Rent = Rent;
    }

    public int getGuard() {
        return Guard;
    }

    public void setGuard(int Guard) {
        this.Guard = Guard;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public int getSpotOwnerId() {
        return SpotOwnerId;
    }

    public void setSpotOwnerId(int SpotOwnerId) {
        this.SpotOwnerId = SpotOwnerId;
    }
    

    
    
    
}
