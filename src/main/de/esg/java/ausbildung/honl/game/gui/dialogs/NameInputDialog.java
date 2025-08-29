package de.esg.java.ausbildung.honl.game.gui.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for player name input
 */
public class NameInputDialog extends BaseDialog {
    private JTextField textField;
    private String inputValue;

    public NameInputDialog(Frame parent) {
        super(parent, null);
    }

    @Override
    protected void buildContent(JPanel mainPanel) {
        // Create input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(CASINO_RED);
        JLabel messageLabel = createLabel("Please enter your name");
        inputPanel.add(messageLabel, BorderLayout.NORTH);
        textField = new JTextField("Player", 20);
        textField.setFont(dialogFont);
        textField.setForeground(Color.WHITE);
        textField.setBackground(CASINO_GREEN);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
        inputPanel.add(textField, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CASINO_RED);
        // close dialog on button press
        JButton okButton = createButton("OK", e -> {
            inputValue = textField.getText();
            result = JOptionPane.OK_OPTION;
            dispose();
        });
        JButton cancelButton = createButton("Cancel", e -> {
            result = JOptionPane.CANCEL_OPTION;
            dispose();
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        // ok as default button
        getRootPane().setDefaultButton(okButton);
    }

    public String getInputValue() {
        return inputValue;
    }

    /**
     * factory method to show dialog and return input value
     * @param parent frame (MainFrame)
     * @return input name or null if cancelled
     */
    public static String nameInput (Frame parent) {
        NameInputDialog dialog = new NameInputDialog(parent);
        dialog.setVisible(true);
        return dialog.getResult() == JOptionPane.OK_OPTION ? dialog.getInputValue() : null;
    }
}
