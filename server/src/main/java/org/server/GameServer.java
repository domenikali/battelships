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

    public GameServer() throws IOException{
        this.inUse=false;
        this.lock= new ReentrantLock();
        this.queueInUse = new Condition[2];
        for(int i=0;i<2;i++)
            this.queueInUse[i]= lock.newCondition();

        this.queue=new int[2];//0 for main-server 1 to rejoin

        this.serverSocket = new ServerSocket(8080);
        this.playersQueue = new LinkedList<>();

    }

    public void closeAll(){
        try {
            this.serverSocket.close();
        }catch (IOException e){
            System.out.println("Server closeAll issue: "+e);
        }
    }

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
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(100);//the client has only 100ms, from the readline call, to send the username before being timed-out
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
                    System.out.println("SERVER: connection timed out");
                    continue;
                }
                playersQueue.add(p);
                System.out.println("SERVER: " + user + " accepted successfully!");
                if (playersQueue.size() > 2) {
                    GameLobby gameLobby = new GameLobby(playersQueue.remove(), playersQueue.remove());
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
                lock.unlock();
            }
        }
    }

    public void requeue(Player p){
        this.lock.lock();

        try {
            while (this.inUse){
                this.queue[1]++;
                this.queueInUse[1].await();
                this.queue[1]--;
            }
            this.inUse=true;
            this.playersQueue.add(p);
        }catch (InterruptedException e){

        }finally {
            if(this.queue[1]>0)
                this.queueInUse[1].signalAll();
            else
                this.queueInUse[0].signalAll();
            this.inUse=false;
            this.lock.unlock();
        }
    }

}
