package de.esg.java.ausbildung.honl.game.gui.dialogs;

import de.esg.java.ausbildung.honl.game.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Base class for custom dialogs
 */
public abstract class BaseDialog extends JDialog {

    // protected access for subclasses
    protected final Font dialogFont = new Font("Serif", Font.PLAIN, 18);
    protected final Color CASINO_RED = Constants.CASINO_RED;
    protected final Color CASINO_GREEN = Constants.CASINO_GREEN;
    protected final Color CASINO_GOLD = Constants.CASINO_GOLD ;
    protected Border dialogBorder;
    protected JLabel messageLabel;
    // track user choice
    protected int result = JOptionPane.CANCEL_OPTION;

    public BaseDialog(Frame parent, String message) {
        // null as title for undecorated dialog
        super(parent, null, true); // Modal dialog
        dialogBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CASINO_GOLD, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setUndecorated(true);
        getContentPane().setBackground(CASINO_RED);
        // Create the main panel with custom border
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(dialogBorder);
        mainPanel.setBackground(CASINO_RED);
        if (message != null && !message.isEmpty()) {
            messageLabel = createLabel(message);
            mainPanel.add(messageLabel, BorderLayout.NORTH);
        }
        // Build the specific content (implemented by subclasses)
        buildContent(mainPanel);
        add(mainPanel);
        pack();
        setLocationRelativeTo(getParent());
    }

    protected abstract void buildContent(JPanel mainPanel);

    /**
     * helper method to create consistently styled buttons
     * @param text button text
     * @param listener for button
     * @return styled JButton
     */
    protected JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(dialogFont);
        button.setBackground(CASINO_GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.addActionListener(listener);
        return button;
    }

    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(dialogFont);
        label.setForeground(Color.WHITE);
        return label;
    }

    public int getResult() {
        return result;
    }
}
