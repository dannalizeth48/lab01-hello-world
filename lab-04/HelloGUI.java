import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class HelloGUI {

    // list of possible greetings
    private static final String[] GREETINGS = {
        "Hello!",
        "Hi there!",
        "Welcome!",
        "Nice to see you!",
        "Greetings, traveler!"
    };

    public static void main(String[] args) {

        // create the main window (JFrame)
        JFrame frame = new JFrame("HelloGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200); // width x height

        // create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // label starts empty
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // button that says "Greet"
        JButton greetButton = new JButton("Greet");

        // when button is clicked, choose a random greeting and show it
        greetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                int index = rand.nextInt(GREETINGS.length); // 0-4
                String greeting = GREETINGS[index];
                messageLabel.setText(greeting);
            }
        });

        // add label to the top and button to the bottom
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(greetButton, BorderLayout.SOUTH);

        // add panel to frame
        frame.add(panel);

        // make window visible
        frame.setVisible(true);
    }
}
