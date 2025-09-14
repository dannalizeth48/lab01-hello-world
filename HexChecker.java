public class HexChecker {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: Please provide exactly one argument.");
            System.exit(1);
        }

        String hex = args[0];
        long decimalValue = 0;

        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int digit;

            if (c >= '0' && c <= '9') {
                digit = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                digit = 10 + (c - 'a');
            } else if (c >= 'A' && c <= 'F') {
                digit = 10 + (c - 'A');
            } else {
                System.err.println("Error: Invalid hexadecimal number.");
                System.exit(1);
                return;
            }

            decimalValue = decimalValue * 16 + digit;
        }

        System.out.println(decimalValue);
    }
}
