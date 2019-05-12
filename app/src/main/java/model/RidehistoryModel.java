package model;

/**
 * Created by wolfsoft4 on 15/9/18.
 */

public class RidehistoryModel {
    private String mutur_id,user,waktu_peminjaman;
    private int i1,i2,i3;
    private String price;
    private String  timestamp;

    public RidehistoryModel(int i1, int i2, int i3, String mutur_id, String  price, String timestamp, String user, String waktu_peminjaman){
        this.i1=i1;
        this.i2=i2;
        this.i3=i3;
        this.mutur_id= mutur_id;
        this.price=price;
        this.timestamp=timestamp;
        this.user=user;
        this.waktu_peminjaman=waktu_peminjaman;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(Integer i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(Integer i2) {
        this.i2 = i2;
    }

    public int getI3() {
        return i3;
    }

    public void setI3(Integer i3) {
        this.i3 = i3;
    }

    public String getMutur_id() {
        return mutur_id;
    }

    public String getPrice() {
        return price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getWaktu_peminjaman() {
        return waktu_peminjaman;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMutur_id(String mutur_id) {
        this.mutur_id = mutur_id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setWaktu_peminjaman(String waktu_peminjaman) {
        this.waktu_peminjaman = waktu_peminjaman;
    }
}
