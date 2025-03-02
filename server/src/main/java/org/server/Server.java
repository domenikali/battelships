package org.server;

import java.io.IOException;

public class Server {


    public static void main(String[] args) {
        int port=8080;
        if(args.length>0){
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e ){
                System.out.println("Error in port number: "+e);
            }
        }

        System.out.println("Server Running!");
        try {
            GameServer gameServer = new GameServer(port);
            gameServer.start();

        }catch (IOException e){
            System.err.println("Game server stopped unexpectedly: "+ e);
            System.exit(1);
        }

    }
}