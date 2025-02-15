package org.util;

public enum Direction {
    NORD,
    SOUTH,
    EAST,
    WEST;

    public int getAsInt(){
        switch (this){
            case NORD -> {return 0;}
            case SOUTH -> {return 1;}
            case EAST -> {return 2;}
            case WEST -> {return 3;}
        }
        return -1;
    }
    public static Direction newDirection(int num) throws IndexOutOfBoundsException{
        if(num>3||num<0)
            throw new IndexOutOfBoundsException();
        switch (num){
            case 0 -> {return NORD;}
            case 1 -> {return SOUTH;}
            case 2 -> {return EAST;}
            case 3 -> {return WEST;}
        }
        return NORD;
    }

}
