package com.dev.quizmania.Models;

public class UserList {
    public String gender;
    public String image;
    public String name;
    public String userID;

    public UserList() {
    }

    public UserList(String Name, String UserID, String Gender,String Image) {
        this.name = Name;
        this.userID = UserID;
        this.image = Image;
        this.gender = Gender;
    }
    public String getGender(){
        return gender;
    }
    public String getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
    public String getUserID(){
        return userID;
    }
}
