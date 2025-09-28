import java.util.*;

public class GoodCasino {

    public static double play(Customer c, SlotMachine s, double amount) {
        double stake = c.spend(amount);
        return s.pullLever(stake);
    }

    public static void main(String[] args) {
        Customer customer = new Customer("customer.txt");
        SlotMachine machine = new SlotMachine("slot-machine.txt");
        Scanner sc = new Scanner(System.in);

        System.out.println("GoodCasino. Type a bet (number) or 'quit'.");

        while (true) {
            if (customer.checkWallet() <= 0) {
                System.out.println("You are out of money.");
                break;
            }
            if (machine.getMoneyPot() <= 0) {
                System.out.println("Casino is out of money.");
                break;
            }

            System.out.print("Bet amount or 'quit': ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) break;

            double bet = 0.0;
            try {
                bet = Double.parseDouble(line);
            } catch (Exception e) {
                System.out.println("Invalid amount.");
                continue;
            }
            if (bet <= 0) {
                System.out.println("Bet must be positive.");
                continue;
            }

            double won = play(customer, machine, bet);
            if (won > 0) customer.receive(won);

            System.out.println("Roll: " + machine.toString());
            System.out.printf("Won: $%.2f%n", won);
            System.out.printf("Wallet: $%.2f | MoneyPot: $%.2f%n",
                    customer.checkWallet(), machine.getMoneyPot());
        }

        customer.save("customer.txt");
        machine.save("slot-machine.txt");
        System.out.println("State saved. Goodbye.");
    }
}

