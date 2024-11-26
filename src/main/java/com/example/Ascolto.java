package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ascolto extends Thread{

Socket s;

     public Ascolto(Socket s){
        this.s = s;
     }

    public void run(){
        try {
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String messaggio;
            while ((messaggio = in.readLine())!= null) {
                System.out.println(messaggio);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
