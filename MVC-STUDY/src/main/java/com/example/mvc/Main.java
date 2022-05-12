package com.example.mvc;

import com.example.mvc.singleton.Aclazz;
import com.example.mvc.singleton.Bclazz;
import com.example.mvc.singleton.SocketClient;

public class Main {

    public  static void main(String[] args) {

        Aclazz aclazz = new Aclazz();
        Bclazz bclazz = new Bclazz();

        SocketClient aClient = aclazz.getSocketClient();
        SocketClient bClient = bclazz.getSocketClient();

        System.out.println("두개의 객체가 동일한가?");
        System.out.println(aClient.equals((bClient)));
    }
}
