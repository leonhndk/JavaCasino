package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * helper class to render playing cards as JPanels with factory methods
 */
public final class CardRenderer {

    private static final Dimension CARD_SIZE = new Dimension(60, 90);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Font RANK_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font SUIT_FONT = new Font("Arial", Font.PLAIN, 36);

    private CardRenderer() {}

    public static Dimension getCardDimension() {
        return CARD_SIZE;
    }

    /**
     * Creates and returns a JPanel that visually represents a playing card.
     * This is the primary method to be used from outside this class.
     *
     * @param card The Card object to render. If null, it will render a face-down card.
     * @return A configured JPanel representing the card.
     */
    public static JPanel createCardView(Card card) {
        JPanel cardView = new JPanel(new BorderLayout(5, 5));
        String rankLabel = card.getRank().getLabel();
        cardView.setPreferredSize(CARD_SIZE);
        cardView.setBackground(CARD_BACKGROUND);
        cardView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        // Top panel with rank label
        JLabel rankLabelTop = new JLabel(rankLabel);
        rankLabelTop.setBorder(new EmptyBorder(5, 5 , 0, 0));
        cardView.add(rankLabelTop, BorderLayout.NORTH);
        // bottom panel with rank label
        JLabel rankLabelBottom = new JLabel(rankLabel);
        rankLabelBottom.setBorder(new EmptyBorder(0, 0 , 5, 5));
        rankLabelBottom.setHorizontalAlignment(SwingConstants.RIGHT);
        cardView.add(rankLabelBottom, BorderLayout.SOUTH);


        JPanel centerPanel = createGridPanel(getLayout(card));
        centerPanel.setBackground(Color.WHITE);
        cardView.add(centerPanel, BorderLayout.CENTER);



        return cardView;
    }
    /**
     * Create a JPanel with a 5x3 grid layout, add suit symbols or placeholders panels according to layoutMap.
     * @param layoutMap map with point coordinates as keys and JPanel components as values.
     * @return A JPanel representing the card with suit symbols.
     */
    private static JPanel createGridPanel(Map<Point, Component> layoutMap) {
        JPanel gridPanel = new JPanel(new GridLayout(5, 3));
        gridPanel.setBackground(Color.CYAN);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                Component component = layoutMap.get(new Point(col, row));
                if (component != null) {
                    gridPanel.add(component);
                }
                else {
                    JPanel emptyPanel = new JPanel();
                    // adjust color after testing
                    emptyPanel.setBackground(Color.GREEN);
                    gridPanel.add(emptyPanel);
                }
            }
        }
        return gridPanel;
    }

    /**
     * Create layout for placement of suit symbols on the card in a 5x3 grid.
     * @param card to render
     * @return A Map with Point coordinates as key values and JLabel components as values,
     */
    private static Map<Point, Component> getLayout(Card card) {
        Suit suit = card.getSuit();
        Rank rank = card.getRank();
        Map<Point, Component> layoutMap = new HashMap<>();
        String suitStr = suit.getName();
        boolean redSuit = suit.equals(Suit.HEARTS) || suit.equals(Suit.DIAMONDS);
        switch (rank.getLabel()) {
            case "2":
                layoutMap.put(new Point(1, 0), new JLabel(suitStr));
                layoutMap.put(new Point(1, 4), new JLabel(suitStr));
                break;
            case "3":
                layoutMap.put(new Point(1, 0), new JLabel(suitStr));
                layoutMap.put(new Point(1, 2), new JLabel(suitStr));
                layoutMap.put(new Point(1, 4), new JLabel(suitStr));
                break;
            case "4":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "5":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(1, 2), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "6":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(0, 2), new JLabel(suitStr));
                layoutMap.put(new Point(2, 2), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "7":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(1, 1), new JLabel(suitStr));
                layoutMap.put(new Point(0, 2), new JLabel(suitStr));
                layoutMap.put(new Point(2, 2), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "8":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(0, 1), new JLabel(suitStr));
                layoutMap.put(new Point(0, 2), new JLabel(suitStr));
                layoutMap.put(new Point(2, 2), new JLabel(suitStr));
                layoutMap.put(new Point(1, 3), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "9":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(0, 1), new JLabel(suitStr));
                layoutMap.put(new Point(2, 1), new JLabel(suitStr));
                layoutMap.put(new Point(1, 2), new JLabel(suitStr));
                layoutMap.put(new Point(0, 3), new JLabel(suitStr));
                layoutMap.put(new Point(2, 3), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            case "10":
                layoutMap.put(new Point(0, 0), new JLabel(suitStr));
                layoutMap.put(new Point(2, 0), new JLabel(suitStr));
                layoutMap.put(new Point(0, 1), new JLabel(suitStr));
                layoutMap.put(new Point(1, 1), new JLabel(suitStr));
                layoutMap.put(new Point(2, 1), new JLabel(suitStr));
                layoutMap.put(new Point(0, 3), new JLabel(suitStr));
                layoutMap.put(new Point(1, 3), new JLabel(suitStr));
                layoutMap.put(new Point(2, 3), new JLabel(suitStr));
                layoutMap.put(new Point(0, 4), new JLabel(suitStr));
                layoutMap.put(new Point(2, 4), new JLabel(suitStr));
                break;
            default:
                // For face cards and Ace place suit symbol in the center
                layoutMap.put(new Point(1, 2), new JLabel(suitStr));
                break;
        }
        for (Component component : layoutMap.values()) {
            if (redSuit) {
                component.setForeground(Color.RED);
            }
            else {
                component.setForeground(Color.BLACK);
            }
        }
        return layoutMap;
    }
}