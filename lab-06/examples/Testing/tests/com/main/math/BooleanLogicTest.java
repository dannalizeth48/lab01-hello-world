package com.main.math;

public class BooleanLogicTest {
    private static int fails = 0;
    private static void check(String name, boolean expected, boolean actual) {
        if (expected != actual) { System.out.println("FAIL: " + name +
            " expected=" + expected + " actual=" + actual); fails++; }
        else System.out.println("PASS: " + name);
    }
    public static void main(String[] args) {
        check("And(T,T)", true,  BooleanLogic.And(true,true));
        check("And(T,F)", false, BooleanLogic.And(true,false));
        check("Or(F,F)" , false, BooleanLogic.Or(false,false));
        check("Not(T)"  , false, BooleanLogic.Not(true));
        check("Xor(T,F)", true,  BooleanLogic.Xor(true,false));
        if (fails > 0) { System.out.println("==> TESTS FAILED: " + fails); System.exit(1); }
        else System.out.println("==> ALL TESTS PASSED");
    }
}
