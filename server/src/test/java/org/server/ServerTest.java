package org.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private String[] args;
    private int port;

    @BeforeEach
    public void changePortArgs(){
        Random r = new Random();
        this.port=r.nextInt(8080,10000);
        this.args= new String[1];
        this.args[0]= String.valueOf(this.port);
    }

    @Test
    public void generalTest() throws InterruptedException {
        Thread s = new Thread(() -> Server.main(args));
        s.start();
        Thread.sleep(100);
        Thread t = new Thread(this::standardConnection);
        t.start();
        Thread.sleep(10);
        Thread d = new Thread(this::standardConnection);
        d.start();
        d.join();
        t.join();
    }

    private void standardConnection(){

        try (Socket socket = new Socket("localhost", this.port);
             BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
            System.out.println("Connected");

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