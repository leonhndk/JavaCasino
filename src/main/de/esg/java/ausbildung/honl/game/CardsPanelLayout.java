package de.esg.java.ausbildung.honl.game;

import javax.swing.text.Position;
import java.awt.*;

public class CardsPanelLayout implements LayoutManager {

    private int spacing; // Spacing between cards
    private final int maxCardCount = 6; // Maximum number of cards in a hand, more than 6 statistically unlikely
    private final double aspectRatio = 6.0 / 9.0; // Aspect ratio for card layout


    public CardsPanelLayout(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void layoutContainer(Container parent) {
        int count = parent.getComponentCount();
        // no cards before first deal, no layout needed
        if (count == 0) {
            return;
        }
        Insets insets = parent.getInsets();
        // get available width and height accounting for insets
        int availableWidth = parent.getWidth() - insets.left - insets.right;
        int availableHeight = parent.getHeight() - insets.top - insets.bottom;
        // +0.5: half of card width for padding on the right
        double maxComponentWidth = availableWidth / (maxCardCount + 0.5);
        int maxComponentSpacing = 0;
        // calculate maximum spacing if more than one card in panel
        if (count > 1) {
            maxComponentSpacing = (int) ((count - 1) * spacing);
        }
        // components and spacing should not resize after adding another component
        int componentWidth = (availableWidth - maxComponentSpacing) / maxCardCount;
        int componentHeight = (int) (componentWidth / aspectRatio);
        // Adjust component height if too tall
        if (componentHeight > availableHeight) {
            componentHeight = availableHeight; // set height to maximum available height
            componentHeight = (int) (componentHeight * aspectRatio);
        }
        // Center vertically
        int yPosition = insets.top + (availableHeight - componentHeight);
        // order from left to right
        int xPosition = insets.left;
        for (Component comp : parent.getComponents()) {
            comp.setBounds(xPosition, yPosition, componentWidth, componentHeight);
            xPosition += componentHeight + spacing;
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
//        int preferredWidth = (int) (parent.getPreferredSize().getWidth());
//        int preferredHeight = preferredWidth / 2; // height of half the parent container (two panels)
        return new Dimension(600,  200); // fixed size for preferred layout
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
//        int minWidth = (int) (parent.getMinimumSize().getWidth());
//        int minHeight = (int) (parent.getMinimumSize().getHeight()/ 2); // height of half the parent container (two panels)
        return new Dimension(300,  100);
    }
}
