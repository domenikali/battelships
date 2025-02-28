package org.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

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

        Thread.sleep(100);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                standardConnection();
            }
        });
        t.start();
        Thread.sleep(10);
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                standardConnection();
            }
        });
        d.start();
        d.join();
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
            bufferedWriter.write("test"+"\n");
            bufferedWriter.flush();
            System.out.println("User sent");

            String message =bufferedReader.readLine();
            System.out.println((int)message.charAt(0));

            assertEquals(message.charAt(0),11);

        }catch (IOException e){
            fail("Socket creation: "+e);
        }
    }

}