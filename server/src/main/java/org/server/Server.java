package org.server;

import java.io.IOException;

public class Server {


    public static void main(String[] args) {
        System.out.println("Server Running!");
        try {
            GameServer gameServer = new GameServer(8080);
            gameServer.start();

        }catch (IOException e){
            System.err.println("Game server stopped unexpectedly: "+ e);
            System.exit(1);
        }

    }
}