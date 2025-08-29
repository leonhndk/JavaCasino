package de.esg.java.ausbildung.honl.game.gui.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * simple confirmation dialog with OK button
 */
public class ConfirmDialog extends BaseDialog {

    public ConfirmDialog(Frame parent, String message) {
        super(parent, message);
    }

    @Override
    protected void buildContent(JPanel mainPanel) {
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CASINO_RED);
        JButton okButton = createButton("OK", e -> {
            result = JOptionPane.OK_OPTION;
            dispose();
        });
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        // ok button as default
        getRootPane().setDefaultButton(okButton);
    }

    public static void showConfirm (Frame parent, String message) {
        ConfirmDialog dialog = new ConfirmDialog(parent, message);
        dialog.setVisible(true);
    }
}
