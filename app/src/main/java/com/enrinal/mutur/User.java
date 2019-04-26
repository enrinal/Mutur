package com.enrinal.mutur;

public class User {
    public String nama;
    public String email;

    public User(){

    }

    public User(String nama,String email){
        this.nama = nama;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

}
