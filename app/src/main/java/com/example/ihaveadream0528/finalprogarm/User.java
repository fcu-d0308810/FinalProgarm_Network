package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/10/10.
 */

public class User {
    private String self;
    private String name;
    private int permission;
    public User(){

    }
    public User(String name, String introduction, int permission){
        this.name = name;
        this.self = introduction;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return self;
    }

    public void setIntroduction(String introduction) {
        this.self = introduction;
    }
    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
