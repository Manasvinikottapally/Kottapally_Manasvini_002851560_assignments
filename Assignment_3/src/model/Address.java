package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manasvini
 */
public class Address {
    private String streetAddress;
    private String unitNumber;
    private String city;
    private String state;
    private String zipcode;
    private String phonenumber;
    
    public Address(String streetAddress,String unitNumber,String city,String state,String zipcode,String phonenumber){
    this.streetAddress = streetAddress;
    this.unitNumber = unitNumber;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.phonenumber = phonenumber;
    }
    

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    
    @Override
    public String toString(){
    return streetAddress + ","+ unitNumber +","+ city +","+ state +","+ zipcode +","+ phonenumber;
    }
    
}
