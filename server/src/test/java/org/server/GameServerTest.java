package org.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.Random;

class GameServerTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void initConsoleOutput(){
        this.outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outputStream));
    }
    @AfterEach
    public void resetConsoleOutput(){
        System.setOut(System.out);
    }

    /**
     * This test check that a message is sent correctly from the client to the server and received correctly
     * */
    @Test
    public void generalTest(){
        int port = new Random().nextInt(8080,10000);
        try {
            GameServer g = new GameServer(port);
            Thread serverT=new Thread(g::start);
            serverT.start();

            Thread t= new Thread(() -> {
                try {
                    Socket s = new Socket("localhost",port);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    out.write("test");
                    out.flush();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    fail("IOEX: "+e);
                }
            });
            t.start();
            t.join();

        }catch (IOException|InterruptedException e){
            fail("IOEX: "+e);
        }
        String consoleOutput = this.outputStream.toString();
        assertTrue(consoleOutput.contains("SERVER: ready to accept"));

    }

    /**
     * This test check for a timed out connection statement
     */
    @Test
    public void timeOut(){
        int port = new Random().nextInt(8080,10000);

        try{
            GameServer g = new GameServer(port);
            Thread serverT=new Thread(g::start);
            serverT.start();

            Thread t = new Thread(() -> {
                try(Socket ignored = new Socket("localhost", port)) {
                    Thread.sleep(1000);
                }catch (IOException|InterruptedException e){
                    fail("IOEX: "+e);
                }
            });
            t.start();
            t.join();
        }catch (IOException|InterruptedException e){
            fail("IOEX: "+e);
        }
        String consoleOutput = this.outputStream.toString();
        assertTrue(consoleOutput.contains("connection timed out"));
    }
}