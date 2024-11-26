package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("localhost", 3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        Scanner scan = new Scanner(System.in);
        String username;

        
            System.out.println("Ti sei connesso ad un server...");
            // Inserimento Username da tastiera
            System.out.println("Inserisci un username");
            // controllo username
          //   do{
            while (true) {
                username = scan.nextLine();
                if (username.equals(null)) {
                    System.out.println("Digita un username valido");
                } else if (username.isEmpty()) {
                    System.out.println("Riempi l' Username ");
                } else {
                    out.write(username);
                    out.newLine();
                    out.flush();
                    break;
                }
            } 
        do {
         //    if (risposta.equals("NO")) {
           //  System.out.println("Username già esistente, Riprova");
            // }
            // else{
            System.out.println("Digita '1' per andare in chat PRIVATA, '2' per quella PUBBLICA e '3' per DISCONNETTERTI");
             //break;
            // }
            // }while (true);
            String scelta = scan.nextLine();
            out.write(scelta);
            out.newLine();
            out.flush();

            String bb = in.readLine();

            switch (bb) {
                case "PRIV":

                    break;
                //chat pubblica
                case "PUBBL":
                Ascolto a = new Ascolto(s);
                a.start();
                boolean isChat = true;
                System.out.println("Benvenuto " + username + " nella chat globale");
                    while (isChat) {

                        String messaggio = scan.nextLine();
                        if (messaggio.equals("/QUIT")) {
                            out.write(messaggio);
                            out.newLine();
                            out.flush();
                            a.interrupt();
                            isChat = false; 
                            break;
                        } else {
                            out.write(messaggio);
                            out.newLine();
                            out.flush();
                        }
                    }
                    break;
                    //disconessione dal programma;
                case "EXIT":
                System.out.println("disconessione...");
                  s.close();
                    break;
            }

        } while (true);

    }
}