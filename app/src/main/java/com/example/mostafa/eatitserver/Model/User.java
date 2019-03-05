package com.example.mostafa.eatitserver.Model;

/**
 * Created by mostafa on 1/3/2018.
 */

public class User {
    private String name,phone,password, IsStaff;

    public User() {
    }

    public User(String name, String phone, String password, String isStaff) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.IsStaff = isStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        this.IsStaff = isStaff;
    }
}
