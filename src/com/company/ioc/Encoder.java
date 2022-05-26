package com.company.ioc;

import java.util.Base64;

public class Encoder {
    private IEncoder iEncoder;

    public Encoder(){
        this.iEncoder = new UrlEncoder();
    }
    public String encode(String message){
        return iEncoder.encode(message);
    }
}
