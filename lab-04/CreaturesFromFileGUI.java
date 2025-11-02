import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CreaturesFromFileGUI {

    // Data model loaded from file
    private ArrayList<Creature> creatures;
    private DefaultListModel<String> listModel;
    private JList<String> creatureList;

    // Editable fields
    private JTextField nameField;
    private JTextField sizeField;

    // Display area on the right
    private JTextArea displayArea;

    // Verb feedback
    private JLabel actionOutputLabel;

    // Track selection
    private int currentIndex = -1;

    // CSV file path (relative to lab-04 folder)
    private static final String DATA_FILE = "creature-data.csv";

    public CreaturesFromFileGUI() {
        // 1. Load creatures from file using ProcessCreatureFile
        creatures = ProcessCreatureFile.loadCreatures(DATA_FILE);

        if (creatures == null) {
            creatures = new ArrayList<>();
        }

        // 2. Build list model
        listModel = new DefaultListModel<>();
        for (Creature c : creatures) {
            listModel.addElement(c.getName());
        }

        creatureList = new JList<>(listModel);
        creatureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // When user selects a creature
        creatureList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currentIndex = creatureList.getSelectedIndex();
                if (currentIndex >= 0 && currentIndex < creatures.size()) {
                    Creature selected = creatures.get(currentIndex);
                    nameField.setText(selected.getName());
                    sizeField.setText(selected.getSize());
                    updateDisplayArea(selected);
                } else {
                    clearEditor();
                }
            }
        });

        // 3. Middle editor panel (labels + text fields)
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(2, 2));

        editPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        editPanel.add(nameField);

        editPanel.add(new JLabel("Size:"));
        sizeField = new JTextField();
        editPanel.add(sizeField);

        // 4. Right display panel
        displayArea = new JTextArea(8, 20);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // 5. Buttons
        JButton saveButton = new JButton("Save Changes");
        JButton addButton = new JButton("Add Creature");
        JButton removeButton = new JButton("Remove Creature");
        JButton actionButton = new JButton("Make Creature Act");

        actionOutputLabel = new JLabel("");

        // Save Changes:
        // - push edits from text fields into the selected Creature
        // - update listModel with new name
        // - write full list back to CSV file using ProcessCreatureFile.saveCreatures
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex >= 0 && currentIndex < creatures.size()) {
                    Creature selected = creatures.get(currentIndex);

                    // update the creature from the editor fields
                    selected.setName(nameField.getText());
                    selected.setSize(sizeField.getText());

                    // update right panel
                    updateDisplayArea(selected);

                    // update list display name
                    listModel.set(currentIndex, selected.getName());

                    // save entire list to file
                    ProcessCreatureFile.saveCreatures(DATA_FILE, creatures);
                }
            }
        });

        // Add Creature:
        // - create new Creature from text fields
        // - add to ArrayList and listModel
        // - select it in the JList
        // - save to file
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();
                String newSize = sizeField.getText().trim();

                if (newName.length() == 0 || newSize.length() == 0) {
                    System.out.println("Cannot add creature with empty fields.");
                    return;
                }

                Creature newCreature = new Creature(newName, newSize);
                creatures.add(newCreature);
                listModel.addElement(newCreature.getName());

                // select the new creature in the list
                int newIndex = creatures.size() - 1;
                creatureList.setSelectedIndex(newIndex);

                // update display
                updateDisplayArea(newCreature);

                // save entire list to file
                ProcessCreatureFile.saveCreatures(DATA_FILE, creatures);
            }
        });

        // Remove Creature:
        // - remove the currently selected creature
        // - update ArrayList and listModel
        // - clear editor/display or select next available
        // - save to file
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex >= 0 && currentIndex < creatures.size()) {
                    creatures.remove(currentIndex);
                    listModel.remove(currentIndex);

                    // adjust selection
                    if (creatures.size() > 0) {
                        int newSel = Math.min(currentIndex, creatures.size() - 1);
                        creatureList.setSelectedIndex(newSel);
                    } else {
                        clearEditor();
                        displayArea.setText("");
                        currentIndex = -1;
                    }

                    // save entire list to file
                    ProcessCreatureFile.saveCreatures(DATA_FILE, creatures);
                }
            }
        });

        // Make Creature Act button calls eat() on the selected creature
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex >= 0 && currentIndex < creatures.size()) {
                    Creature selected = creatures.get(currentIndex);
                    String result = selected.eat();
                    actionOutputLabel.setText(result);
                    System.out.println(result);
                }
            }
        });

        // 6. Bottom panel holds all buttons and output label
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(saveButton);
        bottomPanel.add(addButton);
        bottomPanel.add(removeButton);
        bottomPanel.add(actionButton);
        bottomPanel.add(actionOutputLabel);

        // 7. Frame layout
        JFrame frame = new JFrame("CreaturesFromFileGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 450);
        frame.setLayout(new BorderLayout());

        JScrollPane listScrollPane = new JScrollPane(creatureList);

        frame.add(listScrollPane, BorderLayout.WEST);   // JList of creatures (left)
        frame.add(editPanel, BorderLayout.CENTER);      // editable fields (middle)
        frame.add(scrollPane, BorderLayout.EAST);       // display area (right)
        frame.add(bottomPanel, BorderLayout.SOUTH);     // buttons (bottom)

        frame.setVisible(true);

        // 8. Auto-select first creature if available
        if (!creatures.isEmpty()) {
            creatureList.setSelectedIndex(0);
        }
    }

    // helper to display creature info in the right panel
    private void updateDisplayArea(Creature c) {
        displayArea.setText(c.toString());
    }

    // helper to clear the middle text fields if nothing is selected
    private void clearEditor() {
        nameField.setText("");
        sizeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreaturesFromFileGUI());
    }
}
