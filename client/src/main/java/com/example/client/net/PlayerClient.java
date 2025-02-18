package com.example.client.net;

import java.io.*;
import java.net.Socket;

public class PlayerClient {
    private final String IP = "127.0.0.1";
    private final int PORT = 8080;
    private String user;
    private Socket socket;

    private BufferedWriter writer;
    private BufferedReader reader;

    public PlayerClient(String user) {
        this.user=user;
        this.connectToServer();
    }

    public void connectToServer(){
        try{
            this.socket= new Socket(IP,PORT);
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            closeAll();
            System.exit(1);
        }
    }

    public String receive(){
        String s="";

        try{
            s=this.reader.readLine();
        }catch(IOException e){
            closeAll();
            System.exit(1);
        }
        return s;
    }

    public void send(String s){
        try{
            this.writer.write(s);
            this.writer.flush();
        }catch (IOException |NullPointerException e){
            closeAll();
            System.exit(1);
        }
    }
    public void closeAll(){
        try {
            this.reader.close();
            this.writer.close();
            this.socket.close();
        }catch (IOException e){
            System.exit(1);
        }
    }

}
