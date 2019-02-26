package com.example.kimseolki.refrigerator_acin;

/**
 * Created by 6201P-03 on 2017-05-29.
 */

public class LoginInfo {
    private String ip_address = new String();
    private static LoginInfo instance = new LoginInfo();

    public LoginInfo(String s){}

    public LoginInfo(){

    }

    public static LoginInfo getInstance(){
        return instance;
    }

    public String getIP_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
