package com.guideteee.myapplication;

/**
 * Created by user on 27/11/18.
 */
//this is model class
public class User {
    //variables
    int id;
    String name;
    String password;
    String phone;
    // Constructor with two parameters name and password
    public User(String name,String password)
    {
        this.name=name;
        this.password=password;
    }
    //Parameter constructor containing all three parameters
    public User(int id,String name,String psd)
    {
        this.id=id;
        this.name=name;
        this.password=psd;
    }
    public User(String name,String psd,String phon)
    {
        this.phone=phon;
        this.name=name;
        this.password=psd;
    }
    //getting id
    public int getId() {
        return id;
    }
    //setting id
    public void setId(int id) {
        this.id = id;
    }
    //getting name
    public String getName() {
        return name;
    }
    //setting name
    public void setName(String name) {
        this.name = name;
    }
    //getting password
    public String getPassword() {
        return password;
    }
    //setting password
    public void setPassword(String password) {
        this.password = password;
    }
    public String getphone() {
        return phone;
    }
    //setting name
    public void setphone(String phon) {
        this.phone = phon;
    }
}