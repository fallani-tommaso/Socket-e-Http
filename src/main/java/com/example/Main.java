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
                try {
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

                    File file = new File("htdocs" + arrayLinea1[1]);
                    if (file.exists())
                        sendBinaryFile(out, file);
                    else {
                        String msg = "The resource was not found";
                        out.writeBytes("HTTP/1.1 404 Not Found" + "\n");
                        out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                        out.writeBytes("Server: meucci-server" + "\n");
                        out.writeBytes("Content-Type: text/plain; charset=UTF-8" + "\n");
                        out.writeBytes("Content-Length: " + msg.length() + "\n");
                        out.writeBytes("" + "\n");
                        out.writeBytes(msg + "\n");
                    }
                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore");
            System.exit(1);
        }

    }

    public static void sendBinaryFile(DataOutput output, File file) throws IOException {
        output.writeBytes("HTTP/1.1 200 OK" + "\n");
        output.writeBytes("Content-Length: " + file.length() + "\n");
        output.writeBytes("Content-Type: " + getContentType(file) + "\n");
        output.writeBytes("\n");
        InputStream input = new FileInputStream(file);
        byte[] buf = new byte[8192];
        int n;
        while ((n = input.read(buf)) != -1) {
            output.write(buf, 0, n);
        }
        input.close();
    }

    public static String getContentType(File f) {
        String[] s = f.getName().split("\\.");
        String ext = s[s.length - 1];
        switch (ext) {
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "png":
                return "image/png";
            case "jpg":
                return "image/jpg";
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "js":
                return "test/js";
        }
        return "text/plain";
    }
}