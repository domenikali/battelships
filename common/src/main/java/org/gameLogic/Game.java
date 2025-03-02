package org.gameLogic;

import org.util.Direction;

import java.util.HashSet;
import java.util.Set;

/**
 * this class handles the game logic
 * I've chosen to use a set of string to have a O(1) time complexity at each turn to avoid deserialization*/
public class Game {
    private final Set<String> shipLocations;
    private int lives;

    public Game(String serialized){
        this.shipLocations=new HashSet<>();
        StringBuilder serializedShips = new StringBuilder(serialized);
        for(int i=0;i<serializedShips.length();i+=4){
            int size= serializedShips.charAt(i+2);
            Direction direction = Direction.newDirection(serializedShips.charAt(i+3));
            do{
                this.shipLocations.add(serializedShips.substring(i,i+2));
                switch (direction){
                    case NORD -> serializedShips.setCharAt(i+1, (char) (serializedShips.charAt(i+1)+1));
                    case SOUTH -> serializedShips.setCharAt(i+1, (char) (serializedShips.charAt(i+1)-1));
                    case EAST -> serializedShips.setCharAt(i, (char) (serializedShips.charAt(i)+1));
                    case WEST -> serializedShips.setCharAt(i, (char) (serializedShips.charAt(i)-1));
                }
                this.lives++;
                size--;
            }while(size>0);
        }
    }

    public boolean hit(String message){
        if(shipLocations.contains(message)){
            this.shipLocations.remove(message);
            this.lives--;
            return true;
        }else{
            return false;
        }
    }

    public int getLives(){
        return this.lives;
    }

    public Set<String> getShipLocations(){
        return this.shipLocations;
    }
}