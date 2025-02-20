package org.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.server.GameServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

class GameServerTest {

    @Test
    public void generalTest(){
        try {
            GameServer g = new GameServer();
            Thread serverT=new Thread(g::start);
            serverT.start();

            Thread t= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket s = new Socket("localhost",8080);
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                        out.write("test");
                        out.flush();
                        out.close();
                        s.close();
                    } catch (IOException e) {
                        fail("IOEX: "+e);
                    }
                }
            });
            t.start();
            t.join();

        }catch (IOException|InterruptedException e){
            fail("IOEX: "+e);
        }


    }

}