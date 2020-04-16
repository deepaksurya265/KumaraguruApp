package com.wiates.kumaraguru;

public class Customer {

    String cusID;
    String cusFirstName;
    String cusEmail;
    String cusMob;
    String cusPassword;

    public Customer(String cusID,String cusFirstName,String cusEmail,String cusMob,String cusPassword){
        this.cusID=cusID;
        this.cusFirstName=cusFirstName;
        this.cusMob=cusMob;
        this.cusEmail=cusEmail;
        this.cusPassword=cusPassword;
    }
    public String getCusID(){
        return cusID;
    }
    public String getCusFirstName(){
        return cusFirstName;
    }
    public String getCusEmail(){
        return cusEmail;
    }
    public String getCusMob(){
        return cusMob;
    }
    public String getCusPassword(){
        return cusPassword;
    }

}