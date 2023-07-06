package com.hamro_garage.module;

public class profile {
    private String fullname,address,mobile,email,password;
    public profile(){

    }


    public profile(String fullname,String address, String mobile, String email, String password) {
        this.fullname = fullname;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.password=password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
