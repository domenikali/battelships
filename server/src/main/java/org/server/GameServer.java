package org.server;

import org.net.GameLobby;
import org.net.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameServer {

    private Condition[] queueInUse;
    private int[] queue;
    private final Lock lock;
    private Queue<Player> playersQueue;
    private boolean inUse;


    private final ServerSocket serverSocket;
    private final int port;

    /**
     * GameServer constructor with specific port to connect
     * @param port to listen to
     * @throws IOException if IO issue arise during the ServerSocket initialization*/
    public GameServer(int port) throws IOException{
        this.inUse=false;
        this.lock= new ReentrantLock();
        this.queueInUse = new Condition[2];
        for(int i=0;i<2;i++)
            this.queueInUse[i]= lock.newCondition();

        this.queue=new int[2];//0 for main-server 1 to rejoin
        this.port=port;
        this.serverSocket = new ServerSocket(this.port);
        this.playersQueue = new LinkedList<>();

    }

    public void closeAll(){
        try {
            this.serverSocket.close();
        }catch (IOException e){
            System.out.println("Server closeAll issue: "+e);
        }
    }

    /**
     * this method start the main server loop, it gives less priority to clients connecting for the first time,
     * handles connections and times outs then queue the clients and start the lobby threads*/

    public void start(){
        while (true){
            try {
                lock.lock();
                while(this.queue[1]>0||this.inUse){
                    this.queue[0]++;
                    this.queueInUse[0].await();
                    this.queue[0]--;
                }
                this.inUse=true;
                System.out.println("SERVER: ready to accept...");
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(1000);//the client has only 100ms, from the readline call, to send the username before being timed-out
                BufferedReader bufferedReader=null;
                String user="";
                Player p;
                try{
                    System.out.println("SERVER: accepting...");
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    user = bufferedReader.readLine();
                    socket.setSoTimeout(0);
                    p = new Player(socket,user);
                }catch (IOException e){
                    //if the connection is timed-out or there are buff. issue the server keeps going
                    socket.close();
                    if(bufferedReader!=null)bufferedReader.close();
                    System.out.println("SERVER: connection timed out: "+e);
                    continue;
                }
                playersQueue.add(p);
                System.out.println("SERVER: " + user + " accepted successfully!");
                if (playersQueue.size() >= 2) {
                    System.out.println("SERVER: lobby starting...");
                    GameLobby gameLobby = new GameLobby(this.playersQueue.remove(), this.playersQueue.remove(),this);
                    gameLobby.setDaemon(true);
                    gameLobby.start();
                }
            }catch (IOException|InterruptedException e){
                System.err.println("Server loop stopped: "+e);
                this.closeAll();
            }
            finally {
                if(this.queue[1]>0)
                    this.queueInUse[1].signalAll();
                else if(this.queue[0]>0)
                    this.queueInUse[0].signalAll();
                this.inUse=false;
                this.lock.unlock();
            }
        }
    }

    /**
     * this method requeue a still connected player that exited form a running lobby, this method has priority over the standard connection procedure
     * @param player to requeue*/
    public void requeue(Player player){
        this.lock.lock();

        try {
            while (this.inUse){
                this.queue[1]++;
                this.queueInUse[1].await();
                this.queue[1]--;
            }
            this.inUse=true;
            this.playersQueue.add(player);
        }catch (InterruptedException e){
            player.closeAll();
            System.err.println("Player requeue error: "+e);
        }finally {
            if(this.queue[1]>0)
                this.queueInUse[1].signalAll();
            else
                this.queueInUse[0].signalAll();
            this.inUse=false;
            this.lock.unlock();
        }
    }

    public ServerSocket getServerSocket(){
        return this.serverSocket;
    }

}
