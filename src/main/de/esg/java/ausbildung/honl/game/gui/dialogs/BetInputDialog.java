package de.esg.java.ausbildung.honl.game.gui.dialogs;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * dialog to input bet amount with validation and error messages
 */
public class BetInputDialog extends BaseDialog {
    private final BigDecimal minValue = BigDecimal.valueOf(0.00);
    private final BigDecimal maxValue;
    private JFormattedTextField currencyField;
    private JLabel errorLabel;
    private BigDecimal inputValue;

    public BetInputDialog(Frame parent, BigDecimal maxValue) {
        super(parent, String.format("Enter your bet (max %.2f €)", maxValue));
        this.maxValue = maxValue;
    }

    /**
     * implement buildContent to create input field and buttons
     */
    @Override
    protected void buildContent(JPanel mainPanel) {
        // Create input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(CASINO_RED);
        // formatted text field for currency input
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(java.util.Locale.GERMANY);
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00", symbols);
        currencyFormat.setParseBigDecimal(true);
        NumberFormatter formatter = new NumberFormatter(currencyFormat);
        formatter.setAllowsInvalid(true); // allow user to type freely, validation on submit
        formatter.setMinimum(minValue);
        formatter.setMaximum(maxValue);
        currencyField = new JFormattedTextField(formatter);
        currencyField.setValue(BigDecimal.ZERO); // default value
        currencyField.setFont(dialogFont);
        currencyField.setBackground(CASINO_GREEN);
        currencyField.setForeground(Color.WHITE);
        currencyField.setCaretColor(Color.WHITE);
        currencyField.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
        // Panel to hold the currency field and a label
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.setBackground(CASINO_GREEN);
        JLabel euroLabel = new JLabel("€");
        euroLabel.setFont(dialogFont);
        euroLabel.setForeground(Color.WHITE);
        fieldPanel.add(currencyField);
        fieldPanel.add(euroLabel);
        inputPanel.add(fieldPanel, BorderLayout.CENTER);
        // Error label (hidden until invalid input)
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.WHITE);
        errorLabel.setBackground(CASINO_RED);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        errorLabel.setFont(dialogFont);
        inputPanel.add(errorLabel, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CASINO_RED);
        // ok button calls attemptSubmit to validate input
        JButton okButton = createButton("OK", e -> attemptSubmit());
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(okButton);
        // Allow Enter key to submit
        currencyField.addActionListener(e -> attemptSubmit());
    }

    private BigDecimal getInputValue() {
        return inputValue;
    }

    /**
     * validate input and close dialog if valid
     */
    private void attemptSubmit() {
        String input = currencyField.getText().trim();
        // return 0 for empty input
        if (input.isEmpty()) {
            inputValue = BigDecimal.ZERO;
            result = JOptionPane.OK_OPTION;
            dispose();
            return;
        }
        try {
            currencyField.commitEdit();
            BigDecimal value = (BigDecimal) currencyField.getValue();
            // manual check to trigger error message
            if (value.compareTo(minValue) < 0 || value.compareTo(maxValue) > 0 ) {
                errorLabel.setText("Value must be between 0,00 € and 2,00 €");
                pack();
                return;
            }
            // set value and close dialog
            inputValue = value;
            result = JOptionPane.OK_OPTION;
            dispose();
        } catch (ParseException e) {
           errorLabel.setText("Invalid input!");
           pack();
           // refocus input field so user can correct invalid input
           currencyField.requestFocusInWindow();
        }
    }

    /**
     * factory method to show dialog and return user input
     * @param parent frame
     * @param maxBet 2.00 € or player's current balance
     * @return BigDecimal bet amount
     */
    public static BigDecimal promptCurrencyInput(Frame parent, BigDecimal maxBet) {
        BetInputDialog dialog = new BetInputDialog(parent, maxBet);
        dialog.setVisible(true);
        return dialog.getResult() == JOptionPane.OK_OPTION ? dialog.getInputValue() : BigDecimal.ZERO;
    }
}
