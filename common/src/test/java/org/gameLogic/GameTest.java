package org.gameLogic;

import org.junit.jupiter.api.Test;
import org.util.Direction;
import org.util.Pair;
import org.util.Ship;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void gameTest(){
        Ship ship = new Ship(Direction.NORD ,2,new Pair(9,9));
        String serialized = ship.serialize();
        Game game = new Game(serialized);
        assertEquals(game.getLives(),2);
        assertTrue(game.hit(new Pair(9,9).serialize()));
        assertEquals(game.getLives(),1);
    }

    @Test
    public void bigSetTest(){
        String serialized= "";
        Set<String> testSet= new HashSet<>();
        for(int i=0;i<10;i++){
            Ship s = new Ship(Direction.NORD,1,new Pair(i,i));
            serialized+=s.serialize();
        }
        for (int i=0;i<10;i++){
            testSet.add(new Pair(i,i).serialize());
        }

        Game game = new Game(serialized);
        assertEquals(game.getShipLocations(),testSet);

    }

}