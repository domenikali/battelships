package org.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    public void generalTest() throws InterruptedException {
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(new String[0]);
            }
        });
        s.start();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                standardConnection();
            }
        });
        t.start();
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                standardConnection();
            }
        });
        d.start();
        t.join();
    }

    private void standardConnection(){
        Socket socket;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter = null;
        try {
            System.out.println("connecting..");
            socket = new Socket("localhost", 8080);
            System.out.println("connected...");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("test");

            String message =bufferedReader.readLine();
            assertEquals(message.charAt(0),10);
            Thread.sleep(1000);
        }catch (IOException|InterruptedException e){
            fail("Socket creation: "+e);
        }
    }

}