import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MultipleCreatureGUI {

    // Data model
    private ArrayList<Creature> creatures;  // holds all creatures
    private JList<String> creatureList;     // visible list of creature names
    private DefaultListModel<String> listModel;

    // Editable fields for currently selected creature
    private JTextField nameField;
    private JTextField sizeField;

    // Display area on the right
    private JTextArea displayArea;

    // For action feedback like eat()
    private JLabel actionOutputLabel;

    // Track which creature is selected
    private int currentIndex = -1;

    public MultipleCreatureGUI() {
        // 1. Create some sample creatures to populate the list
        creatures = new ArrayList<>();
        creatures.add(new Creature("Drako", "Large"));
        creatures.add(new Creature("Mimi", "Tiny"));
        creatures.add(new Creature("Fang", "Medium"));

        // 2. Build list model for JList
        listModel = new DefaultListModel<>();
        for (Creature c : creatures) {
            listModel.addElement(c.getName());
        }

        creatureList = new JList<>(listModel);
        creatureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // When the user clicks a creature in the list:
        creatureList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currentIndex = creatureList.getSelectedIndex();
                if (currentIndex >= 0) {
                    Creature selected = creatures.get(currentIndex);
                    // fill middle edit fields
                    nameField.setText(selected.getName());
                    sizeField.setText(selected.getSize());
                    // update right panel text area
                    updateDisplayArea(selected);
                }
            }
        });

        // 3. Middle panel: form with labels + text fields
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(2, 2));

        editPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        editPanel.add(nameField);

        editPanel.add(new JLabel("Size:"));
        sizeField = new JTextField();
        editPanel.add(sizeField);

        // 4. Right panel: text area showing full creature info
        displayArea = new JTextArea(8, 20);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // 5. Bottom panel: Save button + Action button + output label
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex >= 0) {
                    Creature selected = creatures.get(currentIndex);

                    // push values from text fields into that Creature
                    selected.setName(nameField.getText());
                    selected.setSize(sizeField.getText());

                    // update display area
                    updateDisplayArea(selected);

                    // also update the JList name if the creature's name changed
                    listModel.set(currentIndex, selected.getName());
                }
            }
        });

        JButton actionButton = new JButton("Make Creature Act");
        actionOutputLabel = new JLabel("");

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex >= 0) {
                    Creature selected = creatures.get(currentIndex);
                    String result = selected.eat(); // uses Creature.eat()
                    actionOutputLabel.setText(result);
                    System.out.println(result);
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(saveButton);
        bottomPanel.add(actionButton);
        bottomPanel.add(actionOutputLabel);

        // 6. Build frame layout with 3 main sections:
        // LEFT   = creatureList (inside a scroll pane)
        // CENTER = editPanel (text fields for edit)
        // EAST   = scrollPane (displayArea)
        // SOUTH  = bottomPanel (buttons)

        JFrame frame = new JFrame("MultipleCreatureGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        // Put the list in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(creatureList);

        frame.add(listScrollPane, BorderLayout.WEST);   // far left JList
        frame.add(editPanel, BorderLayout.CENTER);      // middle editable fields
        frame.add(scrollPane, BorderLayout.EAST);       // right display area
        frame.add(bottomPanel, BorderLayout.SOUTH);     // bottom buttons

        frame.setVisible(true);

        // 7. Optional: select the first creature by default so UI is not blank
        if (!creatures.isEmpty()) {
            creatureList.setSelectedIndex(0);
        }
    }

    // update the right-side displayArea with info from given creature
    private void updateDisplayArea(Creature c) {
        displayArea.setText(c.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MultipleCreatureGUI());
    }
}
