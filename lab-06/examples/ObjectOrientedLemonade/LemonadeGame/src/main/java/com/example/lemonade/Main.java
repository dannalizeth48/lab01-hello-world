package com.example.lemonade;

import java.util.Scanner;

/**
 * Entry point for the Lemonade game Maven project.
 */
public class Main {

    public static void main(String[] args) {
        // Create a Scanner for user input and inject it into the game.
        Scanner in = new Scanner(System.in);

        LemonadeGame game = new LemonadeGame(in);
        game.run();

        in.close();
    }
}
