package org.serialize;

import org.junit.jupiter.api.Test;
import org.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PairSerializerTest {
    @Test
    void SerializationTest() {
        Pair p= new Pair(1,  1);
        PairSerializer serializer = new PairSerializer();
        try {
            String serialized =serializer.serialize(p);
            assertEquals(serialized,"1:1");
        }catch (IllegalArgumentException e){
            fail("illegal argument");
        }
    }
    @Test
    void DeserializationTest(){
        Pair p= new Pair( 9, 0);
        Serializer<Pair> serializer = new PairSerializer();
        try{
            String serialized= serializer.serialize(p);
            Pair test = serializer.deserialize(serialized);
            assertEquals(p.getX(),test.getX());
            assertEquals(p.getY(),test.getY());
        }catch (IllegalArgumentException e){
            fail("illegal argument");
        }
    }
    @Test
    void bitSerializationTest(){
        Pair p= new Pair(9, 9);
        PairSerializer serializer = new PairSerializer();
        try{
            String ser = serializer.bitSerialize(p);
            Pair test = serializer.bitDeserializer(ser);
            assertEquals(p.getY(),test.getY());
            assertEquals(p.getX(),test.getX());
        }catch (IllegalArgumentException e){
            fail("illegal argument");
        }
    }
    @Test
    void extensiveTests(){
        PairSerializer serializer = new PairSerializer();
        for(int i=0;i<10000;i++){
            Pair p= new Pair(i,i);
            try{
                String ser = serializer.bitSerialize(p);
                Pair test = serializer.bitDeserializer(ser);
                assertEquals(p.getY(),test.getY());
                assertEquals(p.getX(),test.getX());
                System.out.println(p.getX()+":"+test.getX());
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                fail("illegal argument");
            }
        }
    }

    @Test
    void timeTest(){
       PairSerializer pairSerializer = new PairSerializer();
       long time = System.nanoTime();
       for(int i=0;i<100000;i++){
            Pair p = new Pair(i%10,i%10);
            try{
                String s = pairSerializer.bitSerialize(p);
                Pair temp = pairSerializer.bitDeserializer(s);
                assertEquals(temp.getX(),p.getX());
            }catch (IllegalArgumentException e){
                fail("illegal");
            }
       }
       System.out.println("bit: \t"+(System.nanoTime()-time));
       Serializer<Pair> serializer = new PairSerializer();
       time =System.nanoTime();
       for(int i=0;i<100000;i++){
           Pair p = new Pair(i%10,i%10);
           try {
               String s = serializer.serialize(p);
               Pair temp = serializer.deserialize(s);
           }catch (IllegalArgumentException e){
               fail("illegal");
           }
       }
        System.out.println("char: \t"+(System.nanoTime()-time));

    }
}