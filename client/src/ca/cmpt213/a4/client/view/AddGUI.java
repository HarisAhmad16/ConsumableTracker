package ca.cmpt213.a4.client.view;


import ca.cmpt213.a4.client.control.ConsumableManager;
import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.ConsumableFactory;
import com.github.lgooddatepicker.components.DatePicker;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * Adding GUI that extends to JDialog and implements listener
 * Handles the add of a consumable item
 */
public class AddGUI extends JDialog implements ActionListener {

    private static final ConsumableFactory factory = new ConsumableFactory();

    JTextField questionName, questionNotes,
            questionPrice, questionSize;
    JLabel answerName, answerNotes, answerPrice,
            answerSize, answerExpiryDate;

    JButton create = new JButton("Create");
    JButton cancel = new JButton("Cancel");

    JPanel addingPanel;
    Box addBox;

    String[] typesOfItems = {"Food", "Drink"};
    String itemName = "Weight";
    int choiceOnConsumable = 1;
    Consumable consumable;

    JFrame applicationFrame;
    DatePicker questionExpiryDate;
    JComboBox<String> itemTypeList;

    /**
     * Constructor
     *
     * @param applicationFrame main frame
     */
    public AddGUI(JFrame applicationFrame) {
        super(applicationFrame, true);
        addBox = Box.createHorizontalBox();
        addingPanel = new JPanel();
        addingPanel.setSize(600, 700);
        addUILayout();
        this.applicationFrame = applicationFrame;
        super.add(addBox);
    }

    /**
     * Displays the add dialog
     */
    public void displayAddDialog() {
        setTitle("Add Consumable Item");
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
    }

    /**
     * Builds the add dialog
     */
    private void addUILayout() {
        final JPanel addPanel = new JPanel(new BorderLayout(5, 80));
        addPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel text = new JPanel(new GridLayout(0, 1));
        JPanel userField = new JPanel(new GridLayout(0, 1));
        addPanel.add(text, BorderLayout.WEST);
        addPanel.add(userField, BorderLayout.AFTER_LINE_ENDS);

        JPanel createCancel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        makeAddItem();

        addPanel.add(itemTypeList, BorderLayout.NORTH);
        text.add(answerName);
        userField.add(questionName);
        text.add(answerNotes);
        userField.add(questionNotes);
        text.add(answerPrice);
        userField.add(questionPrice);
        text.add(answerSize);
        userField.add(questionSize);
        text.add(answerExpiryDate);
        userField.add(questionExpiryDate);

        createCancel.add(create);
        createCancel.add(cancel);
        addPanel.add(createCancel, BorderLayout.SOUTH);
        addBox.add(addPanel);

        addingPanel.add(new JLabel(" "), BoxLayout.X_AXIS);
    }

    /**
     * Handles the text and button creation
     * Handles invalid user input
     */
    private void makeAddItem() {
        itemTypeList = new JComboBox<>(typesOfItems);
        itemTypeList.setFont(new Font("Serif", Font.ITALIC, 30));
        itemTypeList.setBackground(Color.BLACK);
        itemTypeList.setForeground(Color.WHITE);
        itemTypeList.addActionListener(this);

        Font font1 = new Font("Serif", Font.ITALIC, 30);

        answerName = new JLabel("Name");
        answerName.setFont(new Font("Serif", Font.ITALIC, 30));

        answerNotes = new JLabel("Notes");
        answerNotes.setFont(new Font("Serif", Font.ITALIC, 30));

        answerPrice = new JLabel("Price");
        answerPrice.setFont(new Font("Serif", Font.ITALIC, 30));

        answerSize = new JLabel("Weight");
        answerSize.setFont(new Font("Serif", Font.ITALIC, 30));

        answerExpiryDate = new JLabel("Expiry Date");
        answerExpiryDate.setFont(new Font("Serif", Font.ITALIC, 30));

        questionName = new JTextField(20);
        questionName.setFont(font1);

        questionNotes = new JTextField(20);
        questionNotes.setFont(font1);

        questionPrice = new JTextField(20);
        questionPrice.setFont(font1);
        questionPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (questionPrice.getText() != null) {
                    questionPrice.setEditable((e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                            || e.getKeyChar() == '.' || e.getKeyChar() == 8);
                }
            }
        });

        questionSize = new JTextField(20);
        questionSize.setFont(font1);
        questionSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (questionSize.getText() != null) {
                    questionSize.setEditable((e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                            || e.getKeyChar() == '.' || e.getKeyChar() == 8);
                }
            }
        });

        questionExpiryDate = new DatePicker();

        create.setBackground(Color.black);
        create.setForeground(Color.WHITE);
        create.setPreferredSize(new Dimension(150, 40));
        create.setFont(new Font("Serif", Font.ITALIC, 30));

        create.addActionListener(this);

        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        cancel.setPreferredSize(new Dimension(150, 40));
        cancel.addActionListener(this);
        cancel.setFont(new Font("Serif", Font.ITALIC, 30));
    }

    /**
     * Handles button clicks and creates the consumable
     *
     * @param e user's button click
     *          <p>
     *          Obtaining data from JComboBox found from https://www.geeksforgeeks.org/java-swing-jcombobox-examples/
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemTypeList) {
            itemName = Objects.requireNonNull(itemTypeList.getSelectedItem()).toString();
            if (itemName.equals("Drink")) {
                answerSize.setText("Volume");
                choiceOnConsumable = 2;
            } else {
                answerSize.setText("Weight");
                choiceOnConsumable = 1;
            }
        } else if (e.getSource() == create) {
            String name = questionName.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be left blank", "Error: Consumable Item Name", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String notes = questionNotes.getText();
            if (questionPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Price cannot be left blank", "Error: Consumable Item Price", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double price = Double.parseDouble(questionPrice.getText());
            if (questionSize.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, itemName + " cannot be left blank", "Error: Consumable Item " + itemName, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double size = Double.parseDouble(questionSize.getText());

            String dateString = questionExpiryDate.getText();
            if (dateString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Expiry date cannot be left blank", "Error: Consumable Item Expiry Date", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Convert LocalDate found from https://www.javaprogramto.com/2020/12/java-convert-localdate-to-localdatetime.html
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(dateString, formatter);
            LocalDateTime localDateTime = date.atStartOfDay();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDateTime.format(format);

            String item = new JSONObject()
                    .put("name", name)
                    .put("notes", notes)
                    .put("price", price)
                    .put("expiryDate", localDateTime)
                    .put("size", size)
                    .put("choice", choiceOnConsumable)
                    .toString();
            if (choiceOnConsumable == 1) {
                try {
                    ConsumableManager.addItemToServer(choiceOnConsumable, item);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    ConsumableManager.addItemToServer(choiceOnConsumable, item);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            consumable = factory.getInstance(choiceOnConsumable, name, notes, price, size, localDateTime);
            setVisible(false);
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}
