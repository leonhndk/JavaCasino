package de.esg.ausbildung.honl.game.gui;

import java.awt.*;

public class CardsPanelLayout implements LayoutManager {

    private final int spacing = 8; // Spacing between cards
    private final int maxCardCount = 6; // Maximum number of cards in a hand, more than 6 statistically unlikely
    private final int horizontalPadding = 14;
    private final int verticalPadding = 8;


    /**
     * Layout within the cards panel, arrange cards in a single row with spacing
     */
    @Override
    public void layoutContainer(Container parent) {
        double aspectRatio = 2.0 / 3.0; // Aspect ratio for card (60 * 90 pixels)
        if (parent.getComponentCount() == 0) {
            return; // no components to layout
        }
        Insets insets = parent.getInsets();
        // get available width and height accounting for insets
        int availableWidth = parent.getWidth() - insets.left - insets.right - horizontalPadding * 2;
        // Padding on the top and bottom of the panel
        int availableHeight = parent.getHeight() - insets.top - insets.bottom - verticalPadding * 2;
        // calculate maximum spacing and card size
        int totalSpacing = (maxCardCount - 1) * spacing;
        int cardWidth = (availableWidth - totalSpacing) / maxCardCount;
        int cardHeight = (int) (cardWidth / aspectRatio);
        // set max height to panel height if card height exceeds available height
        if (cardHeight > availableHeight) {
            cardHeight = availableHeight;
            cardWidth = (int) (cardHeight * aspectRatio);
        }
        // Center vertically
        int yPosition = insets.top + verticalPadding + (availableHeight - cardHeight) / 2;
        // order left to right
        int xPosition = insets.left + horizontalPadding;
        for (Component comp : parent.getComponents()) {
            comp.setBounds(xPosition, yPosition, cardWidth, cardHeight);
            xPosition += cardWidth + spacing;
        }
    }

    /**
     * Returns preferred size of the container based on card dimension to determine window size
     *
     * @param parent the container
     * @return preferred size of the container needed for pack()
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        // Get the default card size from CardRenderer.
        Dimension defaultCardSize = CardRenderer.getCardDimension();
        int cardWidth = defaultCardSize.width;
        int cardHeight = defaultCardSize.height;
        // Calculate total width needed to display max number of cards
        int totalGapsWidth = (maxCardCount - 1) * spacing;
        int totalCardsWidth = (maxCardCount * cardWidth) + totalGapsWidth;
        int totalWidth = totalCardsWidth + horizontalPadding * 2;
        int totalHeight = cardHeight + verticalPadding * 2;

        // Return the final dimension, including space for the panel's border/insets.
        return new Dimension(
                totalWidth + insets.left + insets.right,
                totalHeight + insets.top + insets.bottom
        );
    }
    /**
     * Minimum size should be same as preferred size
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}
}
