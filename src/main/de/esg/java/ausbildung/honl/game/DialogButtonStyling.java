package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;

public class DialogButtonStyling extends ButtonUI {

    private static final Color CASINO_GREEN = new Color(0x2d543d);
    private static final Color CASINO_RED = new Color(0x952d28);
    private static final Color CASINO_GOLD = new Color(0xD4AF37);

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JButton button = (JButton) c;
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setBackground(CASINO_GREEN);
        button.setForeground(CASINO_GOLD);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setOpaque(true);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(CASINO_GREEN);
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());

        // draw text
        g2.setColor(CASINO_GOLD);
        FontMetrics fm = g2.getFontMetrics();
        String text = b.getText();
        int x = (c.getWidth() - fm.stringWidth(text)) / 2;
        int y = (c.getHeight() + fm.getAscent()) / 2 - 2;
        g2.drawString(text, x, y);
        g2.dispose();
    }
}
