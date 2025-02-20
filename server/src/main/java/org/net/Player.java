package org.net;

import java.io.*;
import java.net.Socket;

public class Player {
    private final Socket socket;
    private final String user;
    private final BufferedWriter out;
    private final BufferedReader in;
    private boolean isConnected;

    public Player(Socket socket, String user)throws IOException{
        this.socket=socket;
        this.user=user;
        this.isConnected=true;

        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public String getUser(){
        return this.user;
    }

    public void sendMessage(String s){
        try {
            this.out.write(s+"\n");
        }catch (IOException e){
            this.closeAll();
            System.out.println("Client sender issue: "+e);
        }
    }

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
        return this.isConnected;
    }



}
