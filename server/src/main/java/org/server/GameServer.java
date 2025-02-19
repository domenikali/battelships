package org.server;

import org.net.GameLobby;
import org.net.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameServer {
    private Queue<Player> playersQueue;
    private final ServerSocket serverSocket;

    public GameServer() throws IOException{
        this.serverSocket = new ServerSocket(8080);
        playersQueue = new LinkedList<>();

    }

    public void closeAll(){
        try {
            this.serverSocket.close();
        }catch (IOException e){

        }
    }

    public void start(){
        while (true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("SERVER: accepting...");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Thread.sleep(100);
                String user = bufferedReader.readLine();
                playersQueue.add(new Player(socket, user));
                System.out.println("SERVER: " + user + " accepted successfully!");
                if (playersQueue.size() > 2) {
                    GameLobby gameLobby = new GameLobby(playersQueue.remove(), playersQueue.remove());
                    gameLobby.setDaemon(true);
                    gameLobby.start();
                }
            }catch (IOException | InterruptedException e){
                System.err.println(e);

            }
        }
    }

}
