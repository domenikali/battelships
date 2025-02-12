package org.util;

/**
 * Pair cals to use for any two-dimensional coordinate
 *
 * */
public class Pair {
    private int x;
    private int y;
    public Pair(int x, int y){
        this.x=x;
        this.y=y;
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
}
