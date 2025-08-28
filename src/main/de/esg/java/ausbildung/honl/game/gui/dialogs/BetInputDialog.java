package de.esg.java.ausbildung.honl.game.gui.dialogs;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

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

    @Override
    protected void buildContent(JPanel mainPanel) {
        // Create input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(CASINO_RED);

        // Create formatted text field for currency input
        // Use a locale-specific format for Euros (e.g., German) to get the comma decimal separator
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(java.util.Locale.GERMANY);
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00", symbols);
        currencyFormat.setParseBigDecimal(true);
        NumberFormatter formatter = new NumberFormatter(currencyFormat);
        formatter.setValueClass(BigDecimal.class); // Use BigDecimal to avoid ClassCastException
        formatter.setAllowsInvalid(true); // This prevents invalid input during typing
        formatter.setMinimum(minValue);
        formatter.setMaximum(maxValue);

        currencyField = new JFormattedTextField(formatter);
        currencyField.setValue(BigDecimal.ZERO); // Will now display as "0,00"
        currencyField.setFont(dialogFont);
       // currencyField.setColumns(15);
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

        // Error label (initially hidden)
        errorLabel = new JLabel(" "); // Space to maintain layout
        errorLabel.setForeground(Color.WHITE);
        errorLabel.setBackground(CASINO_RED);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        errorLabel.setFont(dialogFont);
        inputPanel.add(errorLabel, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CASINO_RED);
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

    private void attemptSubmit() {
        String input = currencyField.getText().trim();
        if (input.isEmpty()) {
            inputValue = BigDecimal.ZERO;
            result = JOptionPane.OK_OPTION;
            dispose();
            return;
        }
        try {
            currencyField.commitEdit();
            BigDecimal value = (BigDecimal) currencyField.getValue(); // Now guaranteed to be a BigDecimal
            // The formatter already checks this, but a manual check gives a better error message.
            // Corrected logic: check if value is LESS THAN min OR GREATER THAN max.
            if (value.compareTo(minValue) < 0 || value.compareTo(maxValue) > 0 ) {
                errorLabel.setText("Value must be between 0,00 € and 2,00 €");
                pack();
                return;
            }
            // If we get here, the value is valid
            inputValue = value;
            result = JOptionPane.OK_OPTION;
            dispose();
        } catch (ParseException e) {
           errorLabel.setText("Invalid input!");
           pack();
           currencyField.requestFocusInWindow();
        }
    }

    public static BigDecimal promptCurrencyInput(Frame parent, BigDecimal maxBet) {
        BetInputDialog dialog = new BetInputDialog(parent, maxBet);
        dialog.setVisible(true);
        return dialog.getResult() == JOptionPane.OK_OPTION ? dialog.getInputValue() : BigDecimal.ZERO;
    }
}
