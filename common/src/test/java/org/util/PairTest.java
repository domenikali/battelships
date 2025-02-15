package org.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import com.fasterxml.jackson.databind.ObjectMapper;

class PairTest {
    @Test
    void serialize() {
        Pair p = new Pair(0,0);
        String str = p.serialize();
        Pair test = p.deserialize(str);
        assertEquals(str.length(),2);
        assertEquals(p.getX(),test.getX());
        assertEquals(p.getY(),test.getY());
    }
    @Test
    void extensiveTests(){
        for(int i=0;i<1000;i++){
            Pair p= new Pair(i,i);
            String str = p.serialize();
            Pair test = p.deserialize(str);
            assertEquals(str.length(),2);
            assertEquals(p.getX(),test.getX());
            assertEquals(p.getY(),test.getY());
        }
    }

    @Test
    void timeTest(){
        long time =System.nanoTime();
        for(int i=0;i<1000;i++){
            Pair p= new Pair(i,i);
            String str = p.serialize();
            Pair test = p.deserialize(str);
            assertEquals(str.length(),2);
            assertEquals(p.getX(),test.getX());
            assertEquals(p.getY(),test.getY());
        }
        System.out.println("bit serialize: "+time );
        //TODO: try object mapper

    }
}