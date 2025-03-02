package org.util;

import org.server.Serializer;

/**
 * Pair cals to use for any two-dimensional coordinate
 * */
public class Pair extends Serializer<Pair> {
    private final int x;
    private final int y;
    public Pair(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Pair(){
        this.x=0;
        this.y=0;
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    @Override
    public String toString(){
        return this.x+":"+this.y;
    }

    @Override
    public String serialize() {

        String serializedMessage="";
        serializedMessage+=(char)this.getX();
        serializedMessage+=(char)this.getY();
        return serializedMessage;
    }

    @Override
    public Pair deserialize(String serialized) throws IllegalArgumentException {
        if(serialized.length()!=2)
            throw new IllegalArgumentException();

        int x= serialized.charAt(0);
        int y=serialized.charAt(1);
        return new Pair(x,y);
    }

}
