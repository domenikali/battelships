package org.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void serializeStandard() {
        Ship ship = new Ship(Direction.EAST,3,new Pair());
        String serialize = ship.serialize();
        Ship test = ship.deserialize(serialize);
        assertEquals(serialize.length(),4);
        assertEquals(ship.getSize(),test.getSize());
        assertEquals(ship.getDirection(),test.getDirection());
        assertEquals(ship.getLocation().getX(),test.getLocation().getX());
        assertEquals(ship.getLocation().getY(),test.getLocation().getY());
    }

    @Test
    void wrongString(){
        Ship ship = new Ship();
        String str = "wrong";
        assertThrows(IllegalArgumentException.class,()->ship.deserialize(str));
    }

    @Test
    void wrongStringRightSize(){
        Ship ship = new Ship();
        String str = "wro1";
        assertThrows(IllegalArgumentException.class,()->ship.deserialize(str));
    }
}