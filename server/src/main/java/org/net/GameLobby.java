
package org.net;

import org.server.GameServer;

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

    }

    /**
     * this method close the lobby and requeue the clients if still connected
     * */
    private void closeLobby(){
        if(this.secondPlayer.isConnected())this.gameServer.requeue(this.secondPlayer);
        if(this.firstPlayer.isConnected())this.gameServer.requeue(this.firstPlayer);
    }
}
