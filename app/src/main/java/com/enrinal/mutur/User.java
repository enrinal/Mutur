package com.enrinal.mutur;

public class User {
    private String Name,Email,uid;

    public User(){

    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getUid(){
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

}
