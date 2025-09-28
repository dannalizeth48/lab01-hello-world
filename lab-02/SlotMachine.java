import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SlotMachine {
    private char r1, r2, r3;
    private double moneyPot;
    private final Random rng = new Random();

    private static final char[] SYMBOLS = { '\u263A', '\u2764', '7' }; // ☺ ❤ 7

    public SlotMachine() {
        this.moneyPot = 1_000_000.00;
    }

    public SlotMachine(String filename) {
        this.moneyPot = readAmount(filename, 1_000_000.00);
    }

    public double pullLever(double bet) {
        if (bet <= 0) {
            spin();
            return 0.0;
        }
        spin();
        boolean jackpot = (r1 == r2) && (r2 == r3);
        if (jackpot) {
            double payout = 10.0 * bet;
            double paid = Math.min(payout, moneyPot);
            moneyPot -= paid;
            return paid;
        }
        return 0.0;
    }

    private void spin() {
        r1 = SYMBOLS[rng.nextInt(SYMBOLS.length)];
        r2 = SYMBOLS[rng.nextInt(SYMBOLS.length)];
        r3 = SYMBOLS[rng.nextInt(SYMBOLS.length)];
    }

    public String toString() {
        return "" + r1 + r2 + r3;
    }

    public double getMoneyPot() { return moneyPot; }

    public void save(String filename) { writeAmount(filename, moneyPot); }

    private static double readAmount(String file, double fallback) {
        try {
            String s = Files.readString(Path.of(file)).trim();
            return Double.parseDouble(s);
        } catch (Exception e) { return fallback; }
    }

    private static void writeAmount(String file, double amt) {
        try { Files.writeString(Path.of(file), String.format("%.2f", amt)); }
        catch (IOException e) { System.err.println("Failed to save " + file); }
    }
}
