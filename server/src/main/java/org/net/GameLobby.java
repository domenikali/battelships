package org.net;

import org.gameLogic.Game;
import org.server.GameServer;
import java.io.IOException;

/**
 * this is the GameLobby, in here there is all the logic of the game and communications between clients, this run a single demon thread*/
public class GameLobby extends Thread {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final GameServer gameServer;

    /**
     * game lobby constructor
     * @param firstPlayer one of the Player connected
     * @param secondPlayer another of the Player connected
     * @param gameServer the main gameServer for requeue*/
    public GameLobby(Player firstPlayer, Player secondPlayer, GameServer gameServer){
        this.firstPlayer=firstPlayer;
        this.secondPlayer=secondPlayer;
        this.gameServer=gameServer;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Lobby started...");
        char c = (char)11;
        this.firstPlayer.sendMessage(c+"Game Start!");
        this.secondPlayer.sendMessage(c+"Game Start!");
        System.out.println("Start message sent");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                play(secondPlayer, firstPlayer);
            }
        });
        t.start();
        Thread d = new Thread(() -> {
            play(firstPlayer, secondPlayer);
        });
        d.start();
    }

    private void play(Player receivingPlayer, Player sendingPlayer) {
        String init ="";
        try{
            sendingPlayer.getSocket().setSoTimeout(5000);
            init = sendingPlayer.receiveMessage();
        }catch (IOException e ){
            sendingPlayer.closeAll();
            closeLobby();
        }
        Game game = new Game(init);
        int lives = game.getLives();
        while(lives>0){
            String serializeMessage = receivingPlayer.receiveMessage();
            if(serializeMessage.charAt(0)<10){
                if(game.hit(serializeMessage))
                    lives--;
                sendingPlayer.sendMessage(serializeMessage);


            }else if(serializeMessage.charAt(0)>20){
                sendingPlayer.sendMessage(serializeMessage);
            }
        }
        receivingPlayer.sendMessage((char)13+"");
        sendingPlayer.sendMessage((char)12+"");
        closeLobby();
    }

    /**
     * this method close the lobby and requeue the clients if still connected
     * */
    private void closeLobby(){
        if(this.secondPlayer.isConnected())this.gameServer.requeue(this.secondPlayer);
        if(this.firstPlayer.isConnected())this.gameServer.requeue(this.firstPlayer);
    }
}
