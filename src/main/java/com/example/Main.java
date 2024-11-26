package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("localhost", 3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        Scanner scan = new Scanner(System.in);
        String username;
        Ascolto a = new Ascolto(s);

        
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
            boolean isRunning = true;
            while (isRunning) {
                
          
                System.out.println("Digita '1' per andare in chat PRIVATA, '2' per quella PUBBLICA e '3' per DISCONNETTERTI");
                String scelta = scan.nextLine();
                out.write(scelta);
                out.newLine();
                out.flush();
    
                String bb = in.readLine();
    
                switch (bb) {
                    case "PRIV":
                        // Gestione chat privata
                        System.out.println("Digita username con cui vuoi parlare:");
                        String dst = scan.nextLine();
                        out.write(dst);
                        out.newLine();
                        out.flush();
    
                        String risposta = in.readLine();
                        if (risposta.equals("ok")) {
                            System.out.println("Ti sei connesso con: " + dst);
                            a.start(); // Avvia il thread di ascolto
    
                            boolean isChatPr = true;
                            while (isChatPr) {
                                String messaggio = scan.nextLine();
                                if (messaggio.equals("/QUIT")) {
                                    out.write(messaggio);
                                    out.newLine();
                                    out.flush();
                                     // Interrompe il thread di ascolto
                                    isChatPr = false;
                                    System.out.println("Sei tornato al menu principale.");
                                    break;
                                } else {
                                    out.write(messaggio);
                                    out.newLine();
                                    out.flush();
                                }
                            }
                        } else {
                            System.out.println("Utente non trovato!");
                        }
                        break;
    
                    case "PUBBL":
                        // Gestione chat pubblica
                        a.start();
                        boolean isChat = true;
                        System.out.println("Benvenuto " + username + " nella chat globale");
                        while (isChat) {
                            String messaggio = scan.nextLine();
                            if (messaggio.equals("/QUIT")) {
                                out.write(messaggio);
                                out.newLine();
                                out.flush();
                                 // Interrompe il thread di ascolto
                                isChat = false;
                                System.out.println("Sei tornato al menu principale.");
                                break;
                            } else {
                                out.write(messaggio);
                                out.newLine();
                                out.flush();
                            }
                        }
                        a.interrupt();
                        break;
    
                    case "EXIT":
                        System.out.println("Disconnessione...");
                        s.close();
                        isRunning = false;  // Esci dal ciclo principale
                        break;
                }
    
            };
    }
}