package org.net;

import java.net.Socket;

public class Player {
    private final Socket socket;
    private final String user;

    public Player(Socket socket, String user){
        this.socket=socket;
        this.user=user;
    }

    public String getUser(){
        return this.user;
    }



}
