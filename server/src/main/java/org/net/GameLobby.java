package org.net;

public class GameLobby extends Thread {
    private final Player firstPlayer;
    private final Player secondPlayer;

    public GameLobby(Player firstPlayer, Player secondPlayer){
        this.firstPlayer=firstPlayer;
        this.secondPlayer=secondPlayer;
    }

    @Override
    public void run() {

    }
}
