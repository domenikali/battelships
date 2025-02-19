package org.server;

import org.net.Player;

import java.io.IOException;
import java.util.List;

public class Server {


    public static void main(String[] args) {
        System.out.println("Server Running!");
        try {
            GameServer gameServer = new GameServer();
            gameServer.start();
        }catch (IOException e){
            System.err.println("Game server stopped unexpectedly: "+ e);
            System.exit(1);
        }

    }
}