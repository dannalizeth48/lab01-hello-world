package com.example.lemonade;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testDemandCalculation() {
        LemonadeGame game = new LemonadeGame(new java.util.Scanner(System.in));
        int demand = game.computeDemand(10);
        assertTrue(demand > 0);
    }
}
