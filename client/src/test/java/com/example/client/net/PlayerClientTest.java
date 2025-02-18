package com.example.client.net;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerClientTest {

    @Test
    public void sendTest() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(8080);
                    Socket clientSocket=serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    assertEquals(in.readLine(),"hello");
                    in.close();
                    clientSocket.close();
                    serverSocket.close();

                } catch (IOException e) {
                    fail("ioex");

                }
            }
        });
        t.start();

        PlayerClient p = new PlayerClient("test");
        p.send("hello");
        p.closeAll();
    }

    @Test
    public void receiveTest(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(8080);
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

        PlayerClient p = new PlayerClient("test");
        assertEquals(p.receive(),"hello");
        p.closeAll();
    }
}