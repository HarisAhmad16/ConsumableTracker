package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ConsumableManager;
import ca.cmpt213.a4.client.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

/**
 * Remove GUI class extending to JDialog and listener implemented
 * Controls the user removal of a consumable item
 */
public class RemoveGUI extends JDialog implements ActionListener {

    private final ConsumableManager manager = new ConsumableManager();

    JTextField questionIndex;
    JLabel answerRemove;
    JPanel removingPanel;
    Box removeBox;
    int index;

    JFrame applicationFrame;
    JButton removeButton = new JButton("Remove Item");
    JButton cancel = new JButton("Cancel");
    JTextArea textArea = new JTextArea(25, 42);

    /**
     * Constructor
     *
     * @param applicationFrame main frame
     */
    public RemoveGUI(JFrame applicationFrame) {
        super(applicationFrame, true);
        removeBox = Box.createHorizontalBox();
        removingPanel = new JPanel();
        removingPanel.setSize(600, 700);
        removeUILayout();
        this.applicationFrame = applicationFrame;
        super.add(removeBox);
    }

    /**
     * Displays the dialog
     *
     * @return the index of the item being removed
     */
    public int displayRemoveDialog() {
        setTitle("Remove Consumable Item");
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);

        return index;
    }

    /**
     * Lists the item in order
     *
     * @param itemList the list to be printed
     */
    public void listItem(List<Consumable> itemList) {
        if (itemList.size() == 0) {
            textArea.append("No Consumable items to display");
        } else {
            Collections.sort(itemList);

            int foodIndex = 1;
            for (Consumable item : itemList) {
                textArea.append("Item #" + foodIndex + "\n");
                textArea.append(item.toString());
                foodIndex++;
            }
        }
    }

    /**
     * Builds the dialog layout
     */
    public void removeUILayout() {

        makeRemoveButtons();

        textArea.setLineWrap(true);
        Font font1 = new Font("Times New Roman", Font.ITALIC, 22);
        textArea.setFont(font1);
        textArea.setEditable(false);
        textArea.setVisible(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        removingPanel.add(scroll);
        removingPanel.add(answerRemove);
        removingPanel.add(questionIndex);
        removingPanel.add(removeButton, Component.BOTTOM_ALIGNMENT);
        removingPanel.add(cancel, Component.BOTTOM_ALIGNMENT);
        removingPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        removingPanel.setPreferredSize(new Dimension(800, 800));
        removingPanel.setMaximumSize(new Dimension(800, 800));

        removeBox.add(removingPanel);
        removeBox.add(new JLabel(" "), BoxLayout.X_AXIS);


        listItem(manager.getArrayList());
    }

    /**
     * Makes the buttons and text areas in the dialog
     */
    private void makeRemoveButtons() {
        Font font1 = new Font("Serif", Font.ITALIC, 30);

        removeButton.setFont(font1);
        removeButton.setBackground(Color.black);
        removeButton.setForeground(Color.WHITE);
        removeButton.setPreferredSize(new Dimension(200, 40));
        removeButton.addActionListener(this);

        questionIndex = new JTextField(20);
        questionIndex.setFont(font1);
        questionIndex.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (questionIndex.getText() != null) {
                    questionIndex.setEditable((e.getKeyChar() >= '1' && e.getKeyChar() <= '9')
                            || e.getKeyChar() == '.' || e.getKeyChar() == 8);
                }
            }
        });

        answerRemove = new JLabel("Item Number: ");
        answerRemove.setFont(new Font("Serif", Font.ITALIC, 30));

        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        cancel.setPreferredSize(new Dimension(150, 40));
        cancel.addActionListener(this);
        cancel.setFont(new Font("Serif", Font.ITALIC, 30));
    }

    /**
     * Handles button clicks
     *
     * @param e represents the user's click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            if (questionIndex.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Item Number to remove cannot be left blank", "Error: Remove Item Number", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            index = Integer.parseInt(questionIndex.getText());
            setVisible(false);
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}

