public class TestSlot {
    public static void main(String[] args) {
        SlotMachine s = new SlotMachine();
        for (int i = 0; i < 5; i++) {
            double win = s.pullLever(50);
            System.out.println("Spin: " + s.toString() + " Win: " + win + " Pot: " + s.getMoneyPot());
        }
    }
}
