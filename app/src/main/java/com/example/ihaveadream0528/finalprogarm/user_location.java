package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/6/1.
 */

public class user_location {
    private String User;
    private double Latitude;
    private double Longitude;

    public user_location(String User, double latitude, double longitude){
        this.User = User;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
    public user_location(){

    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
