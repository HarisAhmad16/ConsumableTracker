package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ConsumableManager;
import ca.cmpt213.a4.client.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

/**
 * GUI class extending to JFrame with listeners implemented
 * Controls the main menu of the GUI
 */
public class GUI extends JFrame implements ActionListener {

    private final ConsumableManager manager = new ConsumableManager();

    JButton all = new JButton("All");
    JButton expired = new JButton("Expired");
    JButton nonExpired = new JButton("Non-Expired");
    JButton expiringInAWeek = new JButton("Expiring In 7 days");
    JButton addButton = new JButton("Add Item");
    JButton removeButton = new JButton("Remove Item");

    JFrame applicationFrame;
    JTextArea textArea = new JTextArea(25, 42);
    final JPanel mainPanelLayout = new JPanel();

    /**
     * Constructor that starts the main frame
     * <p>
     * Closing window found from https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
     */
    public GUI() {
        manager.getServerList();
        applicationFrame = new JFrame("My Consumable Tracker");
        applicationFrame.setSize(800, 900);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainUILayout();
        applicationFrame.setVisible(true);
        applicationFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(applicationFrame,
                        "Are you sure you would like to close this window? (All progress will be saved)", "Closing Window",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    manager.onExitClick();
                    System.exit(0);
                } else {
                    applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        listItem(manager.getArrayList());
    }


    /**
     * Creates the main frame with scroll, textArea and panels added to it
     * <p>
     * Understanding BoxLayout found from https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
     * Understanding Scroll TextArea found form https://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
     */
    private void mainUILayout() {
        applicationFrame.getContentPane().setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.Y_AXIS));

        makeButtons();

        textArea.setLineWrap(true);
        Font font1 = new Font("Times New Roman", Font.ITALIC, 22);
        textArea.setFont(font1);
        textArea.setEditable(false);
        textArea.setVisible(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        mainPanelLayout.add(all);
        mainPanelLayout.add(expired);
        mainPanelLayout.add(nonExpired);
        mainPanelLayout.add(expiringInAWeek);
        mainPanelLayout.add(scroll);
        mainPanelLayout.add(addButton, Component.BOTTOM_ALIGNMENT);
        mainPanelLayout.add(removeButton, Component.BOTTOM_ALIGNMENT);
        mainPanelLayout.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        mainPanelLayout.setPreferredSize(new Dimension(800, 1600));
        mainPanelLayout.setMaximumSize(new Dimension(800, 1600));

        applicationFrame.getContentPane().add(mainPanelLayout);
        applicationFrame.add(mainPanelLayout);
        applicationFrame.add(new JLabel(" "), BoxLayout.X_AXIS);
    }

    /**
     * Creates all the buttons
     */
    private void makeButtons() {
        all.addActionListener(this);
        all.setBackground(Color.black);
        all.setForeground(Color.WHITE);
        all.setPreferredSize(new Dimension(80, 60));
        all.setFont(new Font("Serif", Font.ITALIC, 30));

        expired.addActionListener(this);
        expired.setBackground(Color.black);
        expired.setForeground(Color.WHITE);
        expired.setPreferredSize(new Dimension(140, 60));
        expired.setFont(new Font("Serif", Font.ITALIC, 30));

        nonExpired.addActionListener(this);
        nonExpired.setBackground(Color.black);
        nonExpired.setForeground(Color.WHITE);
        nonExpired.setPreferredSize(new Dimension(200, 60));
        nonExpired.setFont(new Font("Serif", Font.ITALIC, 30));

        expiringInAWeek.addActionListener(this);
        expiringInAWeek.setBackground(Color.black);
        expiringInAWeek.setForeground(Color.WHITE);
        expiringInAWeek.setPreferredSize(new Dimension(260, 60));
        expiringInAWeek.setFont(new Font("Serif", Font.ITALIC, 30));

        addButton.addActionListener(this);
        addButton.setBackground(Color.black);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(200, 60));
        addButton.setFont(new Font("Serif", Font.ITALIC, 30));

        removeButton.addActionListener(this);
        removeButton.setBackground(Color.black);
        removeButton.setForeground(Color.WHITE);
        removeButton.setPreferredSize(new Dimension(200, 60));
        removeButton.setFont(new Font("Serif", Font.ITALIC, 30));
    }

    /**
     * Updates the textArea in the scroll
     */
    public void resetTextArea() {
        textArea.selectAll();
        textArea.replaceSelection("");
    }


    /**
     * Lists the item in order
     *
     * @param itemList the list to be printed
     */
    public void listItem(List<Consumable> itemList) {

        if (itemList.size() == 0 || itemList == null) {
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
     * Controls the button clicks
     *
     * @param e represents what button is clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("All")) {
            resetTextArea();
            listItem(manager.getArrayList());
        } else if (e.getActionCommand().equals("Expired")) {
            resetTextArea();
            listItem(manager.listingExpired());
        } else if (e.getActionCommand().equals("Non-Expired")) {
            resetTextArea();
            listItem(manager.listingNonExpired());
        } else if (e.getActionCommand().equals("Expiring In 7 days")) {
            resetTextArea();
            listItem(manager.listingExpiringInAWeek());
        } else if (e.getActionCommand().equals("Add Item")) {
            AddGUI dialog = new AddGUI(applicationFrame);
            dialog.displayAddDialog();
            manager.refreshArrayState();
            resetTextArea();
            listItem(manager.getArrayList());
        } else if (e.getActionCommand().equals("Remove Item")) {
            RemoveGUI dialog = new RemoveGUI(applicationFrame);
            int index = dialog.displayRemoveDialog();
            manager.removeConsumable(index);
            manager.refreshArrayState();
            resetTextArea();
            listItem(manager.getArrayList());
        }
    }
}
