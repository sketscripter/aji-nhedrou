package com.example.ajinhedrou.login;

public class User {
    String userId;
    String Displayname;
    String Email;
    long createdAt;

    public User (){};
    public User(String displayname,String email,long createdAt,String id){
        this.userId=id;
        this.Displayname=displayname;
        this.Email=email;
        this.createdAt=createdAt;
    }


    public String getDisplayname() {
        return Displayname;
    }
    public String getEmail() {
        return Email;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public String getuserId(){
        return userId;
    }

}
