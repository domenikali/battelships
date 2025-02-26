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
        char c = (char)11;
        this.firstPlayer.sendMessage(c+"Game Start!");
        this.secondPlayer.sendMessage(c+"Game Start!");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String init ="";
                try{
                    firstPlayer.getSocket().setSoTimeout(5000);
                    init = firstPlayer.receiveMessage();
                    firstPlayer.getSocket().setSoTimeout(0);
                }catch (IOException e ){
                    firstPlayer.closeAll();
                    closeLobby();
                }
                Game game = new Game(init);
                int lives = game.getLives();
                while(lives>0){
                    String serializeMessage =firstPlayer.receiveMessage();
                    if(serializeMessage.charAt(0)<10){
                        if(game.hit(serializeMessage))
                            lives--;

                    }else if(serializeMessage.charAt(0)>20){
                        secondPlayer.sendMessage(serializeMessage);
                    }
                }
                firstPlayer.sendMessage((char)13+"");
                secondPlayer.sendMessage((char)12+"");
                closeLobby();
            }
        });
        t.start();
    }

    /**
     * this method close the lobby and requeue the clients if still connected
     * */
    private void closeLobby(){
        if(this.secondPlayer.isConnected())this.gameServer.requeue(this.secondPlayer);
        if(this.firstPlayer.isConnected())this.gameServer.requeue(this.firstPlayer);
    }
}
