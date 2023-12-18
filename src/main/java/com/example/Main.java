package com.example;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("SERVER PARTITO IN ESECUZIONE...");
            ServerSocket ss = new ServerSocket(8080);

            while (true) {
                Socket s = ss.accept();
                System.out.println("Un client si è connesso");

                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                String linea1 = in.readLine();
                System.out.println(linea1);
                String vuota = "";
                do {
                    vuota = in.readLine();
                    System.out.println(vuota);

                } while (!vuota.equals(""));

                // analizzare il contenuto di stringa2
                String[] arrayLinea1 = linea1.split(" ");
                System.out.println("La stringa numero 2 è composta da: " + arrayLinea1[1]);

                

                try {
                    File file = new File("htdocs" + arrayLinea1[1]);

                    Scanner myReader = new Scanner(file);

                    out.writeBytes("HTTP/1.1 200 OK" + "\n");
                    out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                    out.writeBytes("Content-Length: " + file.length() + "\n");
                    out.writeBytes("Server: meucci-server" + "\n");
                    out.writeBytes("Content-Type: text/html; charset=UTF-8" + "\n");
                    out.writeBytes(" " + "\n");

                    while (myReader.hasNextLine()) {
                        out.writeBytes(myReader.nextLine()+ "\n");
                    }
                    myReader.close();

                } catch (FileNotFoundException e) {
                    String msg = "The resource was not found";
                    out.writeBytes("HTTP/1.1 404 Not Found" + "\n");
                    out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                    out.writeBytes("Server: meucci-server" + "\n");
                    out.writeBytes("Content-Type: text/plain; charset=UTF-8" + "\n");
                    out.writeBytes("Content-Length: " + msg.length() + "\n");
                    out.writeBytes(" " + "\n");
                    out.writeBytes(msg + "\n");

                }
                s.close();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore");
            System.exit(1);
        }

    }
}