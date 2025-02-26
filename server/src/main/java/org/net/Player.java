package org.net;

import java.io.*;
import java.net.Socket;
/**
 * this class handler the single communication between server and client */
public class Player {
    private final Socket socket;
    private final String user;
    private final BufferedWriter out;
    private final BufferedReader in;

    /**
     * constructor for the Player object
     * @param socket of the player connected
     * @param user name of the player connected*/
    public Player(Socket socket, String user)throws IOException{
        this.socket=socket;
        this.user=user;

        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket.isClosed();
    }

    public String getUser(){
        return this.user;
    }

    /**
     * send message to client socket
     * @param message to send*/
    public void sendMessage(String message){
        try {
            this.out.write(message+"\n");
        }catch (IOException e){
            this.closeAll();
            System.out.println("Client sender issue: "+e);
        }
    }

    /**
     * revive a message as String from the client socket
     * @return String read from the client*/
    public String receiveMessage(){
        String message="";
        try{
            message=this.in.readLine();
        }catch (IOException e){
            this.closeAll();
            System.out.println("Client receiver issue: "+e);
        }
        return message;
    }

    /**
     * this method handles the closure of socket and reader/writer*/
    public void closeAll(){
        try{
            if(this.socket!=null)this.socket.close();
            if(this.in!=null)this.in.close();
            if(this.out!=null)this.out.close();
        }catch (IOException e){
            System.out.println("CloseAll issue: "+e);
        }
    }
    public boolean isConnected(){
        return this.socket.isClosed();
    }
    public Socket getSocket(){
        return this.socket;
    }



}
