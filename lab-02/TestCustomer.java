public class TestCustomer {
    public static void main(String[] args) {
        Customer c = new Customer();              // starts at 500.00
        System.out.printf("Start: %.2f%n", c.checkWallet());

        double paid = c.spend(200);               // pay 200
        System.out.printf("Paid: %.2f%n", paid);
        System.out.printf("After spend: %.2f%n", c.checkWallet());

        c.receive(75);                            // win 75
        System.out.printf("After receive: %.2f%n", c.checkWallet());

        paid = c.spend(1000);                     // try to overspend
        System.out.printf("Paid: %.2f%n", paid);
        System.out.printf("Final: %.2f%n", c.checkWallet());
    }
}
