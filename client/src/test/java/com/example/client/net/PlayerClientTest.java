package com.example.client.net;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerClientTest {


    @Test
    public void sendTest() {
        Random r=new Random();
        int port = r.nextInt(8080,10000);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket=serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    assertEquals(in.readLine(),"test");
                    in.close();
                    clientSocket.close();
                    serverSocket.close();

                } catch (IOException e) {
                    fail("ioex");

                }
            }
        });
        t.start();


        PlayerClient p = new PlayerClient("test",port);

    }

    @Test
    public void receiveTest(){
        Random r=new Random();
        int port = r.nextInt(8080,10000);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket=serverSocket.accept();
                    BufferedWriter in = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    in.write("hello");
                    in.flush();
                    in.close();
                    clientSocket.close();
                    serverSocket.close();

                } catch (IOException e) {
                    fail("ioex");

                }
            }
        });
        t.start();

        PlayerClient p = new PlayerClient("test",port);
        assertEquals(p.receive(),"hello");
        p.closeAll();
    }
}