package com.example.lemonade;

import java.util.*;

/**
 * Core game logic for Lemonade Stand.
 * 
 * This class encapsulates the full game loop and state:
 * - list of players
 * - current day
 * - random events and weather
 * - money, prices, demand and advertising.
 */
public class LemonadeGame {

    // --- Constants from the BASIC program ---
    private static final int    P9 = 10;        // reference price in cents (used in demand formula)
    private static final double SIGN_COST = 0.15;
    private static final int    BASE_DEMAND = 30;
    private static final double AD_COEFF = 0.5; // C9 in BASIC
    private static final double C2 = 1.0;       // C2 in BASIC (kept for parity)

    // Random with stable seed per run for reproducibility
    private final Random rng = new Random();

    /**
     * Represents one player (one lemonade stand).
     */
    private static class Player {
        double assets = 2.00; // A(I) starts at $2.00
        boolean bankrupt = false;

        // Per-day inputs/outputs
        int glassesToMake; // L(I) [0..1000]
        int signs;         // S(I) [0..50]
        int priceCents;    // P(I) [0..100]
        int glassesSold;   // N2
        double income;     // M
        double expenses;   // E
        double profit;     // P1
        boolean thunderRuined = false; // H(I)/G(I) effect
    }

    /** Simple enum for the daily weather. */
    private enum Weather { SUNNY, CLOUDY, HOT_DRY, THUNDERSTORM }

    // Game state
    private int day = 0;
    private final Scanner in;

    public LemonadeGame(Scanner in) {
        this.in = in;
    }

    /**
     * Runs the whole interactive game loop until the player chooses to stop.
     */
    public void run() {
        println("Hi! Welcome to Lemonsville, California!\n");
        int players = askInt("How many people will be playing (1–30)? ", 1, 30);

        List<Player> P = new ArrayList<>();
        for (int i = 0; i < players; i++) P.add(new Player());

        while (true) {
            // Weather report selection
            Weather weather = pickWeather();

            // Start of new day
            day++;
            double perGlassCost = perGlassCostForDay(day); // C1 in dollars
            println("\nOn day " + day + ", the cost of lemonade is $" + formatMoney(perGlassCost));

            if (day == 3) println("(Your mother quit giving you free sugar.)");
            if (day == 7) println("(The price of lemonade mix just went up.)");

            // Random events after day 2
            // R1 multiplies demand; R2 special street-crew flag; R3 thunder flag
            double R1 = 1.0;
            boolean R2StreetBuysAll = false;
            boolean thunder = false;

            if (day > 2) {
                // CLOUDY: light rain % chance reduces demand
                if (weather == Weather.CLOUDY) {
                    int J = 30 + (rng.nextInt(5) * 10); // 30, 40, 50, 60, 70
                    println("There is a " + J + "% chance of light rain, and the weather is cooler today.");
                    R1 = 1.0 - (J / 100.0);
                }
                // HOT & DRY: predicted heat wave doubles demand
                else if (weather == Weather.HOT_DRY) {
                    println("A heat wave is predicted for today!");
                    R1 = 2.0;
                }

                // 25% chance street department works today
                if (rng.nextDouble() < 0.25) {
                    println("The street department is working today.");
                    println("There will be no traffic on your street.");
                    if (rng.nextDouble() < 0.5) {
                        // Street crews buy all your lemonade at lunchtime
                        R2StreetBuysAll = true;
                    } else {
                        // Almost no customers
                        R1 = 0.1;
                    }
                }

                // 25% chance of THUNDERSTORM when CLOUDY
                if (weather == Weather.CLOUDY && rng.nextDouble() < 0.25) {
                    weather = Weather.THUNDERSTORM;
                    thunder = true; // sets G(I)=0 in BASIC
                    println("WEATHER REPORT: A severe thunderstorm hit Lemonsville earlier today,");
                    println("just as the lemonade stands were being set up. Unfortunately, everything was ruined!");
                }
            }

            // Collect decisions
            for (int i = 0; i < players; i++) {
                Player p = P.get(i);
                if (p.bankrupt) {
                    println("\nLEMONADE STAND " + (i + 1) + " — ASSETS " + money(p.assets));
                    println("You are bankrupt; no decisions for you to make.");
                    continue;
                }

                println("\nLEMONADE STAND " + (i + 1) + " — ASSETS " + money(p.assets));
                p.glassesToMake = askBoundedIntAndFunds(
                    "How many glasses of lemonade do you wish to make (0–1000)? ",
                    0, 1000, p.assets, perGlassCost,
                    "To make that many glasses you need $" + formatMoney(perGlassCost) + " each.");

                double cashLeftAfterLemon = p.assets - (p.glassesToMake * perGlassCost);

                p.signs = askBoundedIntAndFunds(
                    "How many advertising signs (" + cents(SIGN_COST) + " each) do you want to make (0–50)? ",
                    0, 50, cashLeftAfterLemon, SIGN_COST,
                    "You don't have enough cash left after making lemonade.");

                p.priceCents = askInt(
                    "What price (in CENTS, 0–100) do you wish to charge per glass? ",
                    0, 100);
            }

            // Daily financial report & calculations
            println("\n$$ Lemonsville Daily Financial Report $$\n");

            for (int i = 0; i < players; i++) {
                Player p = P.get(i);
                if (p.bankrupt) {
                    println("Stand " + (i + 1) + " — BANKRUPT");
                    continue;
                }

                // Thunderstorm ruins everything: zero sales
                if (thunder) {
                    p.thunderRuined = true;
                    p.glassesSold = 0;
                } else if (R2StreetBuysAll) {
                    // Street crews buy all your lemonade (N2=L)
                    p.glassesSold = p.glassesToMake;
                    println("The street crews bought all your lemonade at lunchtime!!");
                } else {
                    int demandEstimate = computeDemand(p.priceCents);
                    int withAds = applyAdvertising(demandEstimate, p.signs);
                    double adjusted = R1 * withAds;
                    int potential = (int)Math.floor(adjusted);
                    p.glassesSold = Math.min(p.glassesToMake, potential);
                }

                p.income   = (p.glassesSold * (p.priceCents / 100.0));
                p.expenses = (p.signs * SIGN_COST) + (p.glassesToMake * perGlassCost);
                p.profit   = p.income - p.expenses;
                p.assets   = roundMoney(p.assets + p.profit);

                println("Day " + day + " — Stand " + (i + 1));
                println("  " + p.glassesSold + " glasses sold");
                println("  " + cents(p.priceCents / 100.0) + " per glass   Income " + money(p.income));
                println("  " + p.glassesToMake + " glasses made");
                println("  " + p.signs + " signs          Expenses " + money(p.expenses));
                println("                 Profit   " + money(p.profit));
                println("                 Assets   " + money(p.assets));
                println("");

                // Bankruptcy check (assets <= current per-glass cost)
                if (p.assets <= perGlassCost) {
                    println("...You don't have enough money left to stay in business — you're BANKRUPT!");
                    p.bankrupt = true;
                }
            }

            // If single player and bankrupt with no ability to cover cost next day, end.
            if (players == 1 && P.get(0).bankrupt) {
                println("\nYou're out of business. Game over.");
                break;
            }

            // Continue?
            String cont = askLine("Continue to next day? [Enter=Yes, N=no] ").trim();
            if (cont.equalsIgnoreCase("N")) break;
        }

        println("\nThanks for playing Lemonade Stand!");
    }

    // --- Pricing & Demand (mirrors Applesoft BASIC lines 1190–1240) ---

    /** Base demand N1 from price (P) using original piecewise formula with P9=10, S2=30. */
    int computeDemand(int priceCents) {
        double N1;
        if (priceCents >= P9) {
            // N1 = (P9^2 * S2) / P^2
            N1 = (Math.pow(P9, 2) * BASE_DEMAND) / Math.pow(priceCents, 2);
        } else {
            // N1 = ((P9 - P)/P9)*0.8*S2 + S2
            N1 = ((P9 - priceCents) / (double)P9) * 0.8 * BASE_DEMAND + BASE_DEMAND;
        }
        return (int)Math.floor(N1);
    }

    /** Advertising effect (W = -S*AD_COEFF; V = 1 - exp(W)*C2; N1 + N1*V). */
    int applyAdvertising(int baseDemand, int signs) {
        double W = -signs * AD_COEFF;
        double V = 1.0 - (Math.exp(W) * C2);
        double withAds = baseDemand + (baseDemand * V);
        return (int)Math.floor(withAds);
    }

    // --- Weather & costs ---

    /** Apple II distribution: 60% SUNNY, 20% CLOUDY, 20% HOT & DRY; first two days forced SUNNY. */
    Weather pickWeather() {
        if (day < 2) return Weather.SUNNY;
        double r = rng.nextDouble();
        if (r < 0.60) return Weather.SUNNY;
        else if (r < 0.80) return Weather.CLOUDY;
        else return Weather.HOT_DRY;
    }

    /** Per-glass production cost by day, in dollars. */
    double perGlassCostForDay(int dayNumber) {
        int cents;
        if (dayNumber <= 2) cents = 2;
        else if (dayNumber <= 6) cents = 4;
        else cents = 5;
        return cents / 100.0;
    }

    // --- IO helpers ---

    private int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer in [" + min + ", " + max + "].");
            }
        }
    }

    private int askBoundedIntAndFunds(String prompt, int min, int max,
                                      double availableCash, double unitCost, String notEnoughMsg) {
        while (true) {
            int v = askInt(prompt, min, max);
            double needed = v * unitCost;
            if (needed <= availableCash + 1e-9) return v;
            System.out.println("Think again! You have only " + money(availableCash) + " in cash.");
            System.out.println(notEnoughMsg + " (need " + money(needed) + "). Try again.");
        }
    }

    private String askLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    private static void println(String s) { System.out.println(s); }

    private static String money(double v) { return "$" + formatMoney(v); }

    private static String cents(double dollars) {
        // for showing prices that were entered as cents
        return String.format("$%.2f", dollars);
    }

    private static String formatMoney(double v) {
        return String.format(Locale.US, "%.2f", roundMoney(v));
    }

    private static double roundMoney(double v) {
        // Same behavior as the BASIC "STI => dollars.cents" routine
        return Math.round(v * 100.0) / 100.0;
    }
}
