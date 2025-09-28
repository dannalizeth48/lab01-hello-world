import java.io.*;
import java.nio.file.*;

public class Customer {
    private double wallet;

    public Customer() { this.wallet = 500.00; }

    public Customer(String filename) {
        this.wallet = readAmount(filename, 500.00);
    }

    public double spend(double amount) {
        if (amount <= 0) return 0.0;
        double paid = Math.min(amount, wallet);
        wallet -= paid;
        return paid;
    }

    public void receive(double amount) {
        if (amount > 0) wallet += amount;
    }

    public double checkWallet() { return wallet; }

    public void save(String filename) { writeAmount(filename, wallet); }

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


