package com.example.client.net;

import java.io.*;
import java.net.Socket;

public class PlayerClient {
    private final String IP = "127.0.0.1";
    private final int PORT;
    private String user;
    private Socket socket;

    private BufferedWriter writer;
    private BufferedReader reader;

    public PlayerClient(String user,int port) {
        this.user=user;
        this.PORT=port;
        this.connectToServer();

    }
    public PlayerClient(String user){
        this.user=user;
        this.PORT=8080;
        this.connectToServer();
    }

    public void connectToServer(){
        try{
            this.socket= new Socket(IP,PORT);
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException  e){
            this.closeAll();
            System.exit(1);
        }

        this.send(this.user);
    }

    public String receive(){
        String s="";

        try{
            s=this.reader.readLine();
        }catch(IOException e){
            this.closeAll();
            System.exit(1);
        }
        return s;
    }

    public void send(String s){
        try{
            this.writer.write(s);
            this.writer.flush();
        }catch (IOException |NullPointerException e){
            this.closeAll();
            System.exit(1);
        }
    }

    public void closeAll(){
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }


}
