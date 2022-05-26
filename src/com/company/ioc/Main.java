package com.company.ioc;

import java.net.URLEncoder;

public class Main {

    public static void main(String[] args) {
        String url = "www.naver.com/books/it?page=10&size=20@name=spring-boot";

        //Base64 encoding

        //url encoding

        Encoder encoder = new Encoder();
        String result = encoder.encode(url);

        System.out.println(result);
    }
}
