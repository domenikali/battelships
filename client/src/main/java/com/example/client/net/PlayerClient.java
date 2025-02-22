package com.example.client.net;

import java.io.*;
import java.net.Socket;

/**
 * This class handles the connection and data transfer of all the information tru TCP connections
 * */
public class PlayerClient {
    private final String IP = "127.0.0.1";
    private final int PORT;
    private String user;
    private Socket socket;

    private BufferedWriter writer;
    private BufferedReader reader;
    /**
     * Constructor to connect to the server
     * @param user : the username the player will go by
     * @param port : the port the server will be connected to*/
    public PlayerClient(String user,int port) {
        this.user=user;
        this.PORT=port;
        this.connectToServer();
    }
    /**
     * Constructor to connect to the server, the port is set to default to 8080
     * @param user : the username the player will go by
     * */
    public PlayerClient(String user){
        this.user=user;
        this.PORT=8080;
        this.connectToServer();
    }

    /**
     * this method manage the connection to the server and send the user to it*/
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

    /**
     * receive a new message from the server, this method is blocking
     * @return message sent from the server as a String*/
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

    /**
     * this method send the string as paramether to the server
     * @param message to send to the server connected*/
    public void send(String message){
        try{
            this.writer.write(message+"\n");
            this.writer.flush();
        }catch (IOException |NullPointerException e){
            this.closeAll();
            System.err.println("Send error: "+e);
            System.exit(1);
        }
    }

    /**
     * this method close every connection and writer/reader
     */
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
