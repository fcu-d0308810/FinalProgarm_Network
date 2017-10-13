package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/10/10.
 */

public class User {
    private String id;
    private String introduction;
    private String name;
    private String password;
    private int permission;
    public User(){

    }
    public User(String id, String password, String name, String introduction, int permission){
        this.id = id;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
