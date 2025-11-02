import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SingleCreatureGUI {

    private Creature creature;          // the creature we are editing
    private JTextField nameField;       // text input for name
    private JTextField sizeField;       // text input for size
    private JTextArea displayArea;      // shows full creature info
    private JLabel actionOutputLabel;   // shows result of a verb like eat()

    public SingleCreatureGUI() {
        // Hard-code one Creature for now
        creature = new Creature("Drako", "Large");

        // ---- Left side: form panel with labels + text fields ----
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2)); // 2 rows, 2 cols (Label, TextField)

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(creature.getName());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Size:"));
        sizeField = new JTextField(creature.getSize());
        formPanel.add(sizeField);

        // ---- Right side: display area with creature info ----
        displayArea = new JTextArea(8, 20);
        displayArea.setEditable(false);
        updateDisplayArea(); // fill it with current creature data

        JScrollPane scrollPane = new JScrollPane(displayArea);

        // ---- Bottom-right: Save button ----
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Copy values from text fields into the Creature object
                creature.setName(nameField.getText());
                creature.setSize(sizeField.getText());

                // Refresh the display area to show new creature state
                updateDisplayArea();
            }
        });

        // ---- Creature verb button ----
        JButton actionButton = new JButton("Make Creature Act");
        actionOutputLabel = new JLabel(""); // will show output of the verb

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // call Creature.eat()
                String result = creature.eat();
                actionOutputLabel.setText(result);
                System.out.println(result);
            }
        });

        // ---- Layout management for the whole window ----
        JFrame frame = new JFrame("SingleCreatureGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // BorderLayout for overall window regions
        frame.setLayout(new BorderLayout());

        // bottom panel for save + action + output
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(saveButton);
        bottomPanel.add(actionButton);
        bottomPanel.add(actionOutputLabel);

        frame.add(formPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Refresh right text area with the creature's current data
    private void updateDisplayArea() {
        displayArea.setText(creature.toString());
    }

    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SingleCreatureGUI();
            }
        });
    }
}
