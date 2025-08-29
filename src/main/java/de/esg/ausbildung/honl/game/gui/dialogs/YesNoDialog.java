package de.esg.ausbildung.honl.game.gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class YesNoDialog extends BaseDialog {

    public YesNoDialog(Frame parent, String message) {
        super(parent, message);
    }

    @Override
    protected void buildContent(JPanel mainPanel) {
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CASINO_RED);

        JButton yesButton = createButton("Yes", e -> {
            result = JOptionPane.YES_OPTION;
            dispose();
        });

        JButton noButton = createButton("No", e -> {
            result = JOptionPane.NO_OPTION;
            dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Make Yes the default button
        getRootPane().setDefaultButton(yesButton);
    }

    public static int promptYesNo (Frame parent, String message) {
        YesNoDialog dialog = new YesNoDialog(parent, message);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
