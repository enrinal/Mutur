package com.enrinal.mutur;

public class MuturInformation {
    public String nama;
    public double latitude;
    public double longitude;
    public int is_reserved;

    public MuturInformation(){

    }

    public MuturInformation(double latitude,double longitude,String nama,int is_reserved){
        this.latitude=latitude;
        this.longitude=longitude;
        this.is_reserved=is_reserved;
        this.nama=nama;
    }
}
