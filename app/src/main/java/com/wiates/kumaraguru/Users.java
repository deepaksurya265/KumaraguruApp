package com.wiates.kumaraguru;

public class Users {

    String cusFirstName;
    String cusEmail;
    String cusMob;
    String cusPassword;

    public Users(String cusFirstName, String cusEmail, String cusMob, String cusPassword){
        this.cusFirstName=cusFirstName;
        this.cusMob=cusMob;
        this.cusEmail=cusEmail;
        this.cusPassword=cusPassword;
    }

    public Users(){}

    public String getCusFirstName() {
        return cusFirstName;
    }

    public void setCusFirstName(String cusFirstName) {
        this.cusFirstName = cusFirstName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusMob() {
        return cusMob;
    }

    public void setCusMob(String cusMob) {
        this.cusMob = cusMob;
    }

    public String getCusPassword() {
        return cusPassword;
    }

    public void setCusPassword(String cusPassword) {
        this.cusPassword = cusPassword;
    }
}