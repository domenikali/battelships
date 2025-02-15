package org.util;

import org.serialize.Serializer;

public class Ship extends Serializer<Ship> {
    private final Direction direction;
    private final int size;
    private final Pair location;
    private int life;
    private boolean alive;

    public Ship(Direction direction, int size, Pair location) {
        this.direction = direction;
        this.size = size;
        this.location = location;
        this.alive=true;
        this.life=size;
    }
    public Ship(){
        this.direction = Direction.NORD;
        this.size = 0;
        this.location = new Pair();
        this.alive=true;
        this.life=this.size;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSize() {
        return size;
    }

    public Pair getLocation() {
        return location;
    }

    public boolean isAlive(){
        return alive;
    }
    public void hit(){
        if(this.alive) {
            this.life--;
            if (this.life == 0)
                this.alive = false;
        }
    }

    @Override
    public String serialize() {
        String serializedMessage="";
        serializedMessage+=this.location.serialize();
        serializedMessage+=(char)this.size;
        serializedMessage+=(char)this.direction.getAsInt();
        return serializedMessage;
    }

    @Override
    public Ship deserialize(String serialized) throws IllegalArgumentException {
        if(serialized.length()!=4)
            throw new IllegalArgumentException();

        Pair p = new Pair();
        p= p.deserialize(serialized.substring(0,2));
        int size=(int)serialized.charAt(2);
        try{
            Direction d = Direction.newDirection((int)serialized.charAt(3));
            return new Ship(d,size,p);
        }catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
    }
}