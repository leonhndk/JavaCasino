package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A stateless utility class to create a visual representation of a playing card.
 * This class uses a static factory method and is not meant to be instantiated.
 */
public final class CardRenderer {

    private static final Dimension CARD_SIZE = new Dimension(80, 120);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Font RANK_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font SUIT_FONT = new Font("Arial", Font.PLAIN, 36);

    /**
     * Private constructor to prevent anyone from creating an instance of this utility class.
     */
    private CardRenderer() {}

    /**
     * Creates and returns a JPanel that visually represents a playing card.
     * This is the primary method to be used from outside this class.
     *
     * @param card The Card object to render. If null, it will render a face-down card.
     * @return A configured JPanel representing the card.
     */
    public static JPanel createCardView(Card card) {
        JPanel cardView = new JPanel(new BorderLayout(5, 5));
        cardView.setPreferredSize(CARD_SIZE);
        cardView.setBackground(CARD_BACKGROUND);
        cardView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        // Top panel with rank label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel rankLabelTop = new JLabel("K");
        rankLabelTop.setBorder(new EmptyBorder(0, 0 , 8, 8));
        topPanel.add(rankLabelTop, BorderLayout.NORTH);
        cardView.add(topPanel, BorderLayout.NORTH);
        // bottom panel with rank label
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        JLabel rankLabelBottom = new JLabel("K");
        rankLabelBottom.setBorder(new EmptyBorder(0, 0 , 8, 8));
        rankLabelBottom.setHorizontalAlignment(SwingConstants.RIGHT);
        bottomPanel.add(rankLabelBottom, BorderLayout.EAST);
        cardView.add(bottomPanel, BorderLayout.SOUTH);


        JPanel centerPanel = new JPanel(new GridLayout(5, 3, 5,5));
        centerPanel.setBackground(Color.BLUE);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cardView.add(centerPanel, BorderLayout.CENTER);



        return cardView;
    }


    private static Map<Point, Component> getLayout(Rank rank, String suit) {
        Map<Point, Component> layoutMap = new HashMap<>();
        switch (rank.getLabel()) {
            case "2":
                layoutMap.put(new Point(1, 0), new JLabel(suit));
                layoutMap.put(new Point(1, 4), new JLabel(suit));
                break;
            case "3":
                layoutMap.put(new Point(1, 0), new JLabel(suit));
                layoutMap.put(new Point(1, 2), new JLabel(suit));
                layoutMap.put(new Point(1, 4), new JLabel(suit));
                break;
            case "4":
                layoutMap.put(new Point(0, 0), new JLabel(suit));
                layoutMap.put(new Point(0, 4), new JLabel(suit));
                layoutMap.put(new Point(2, 0), new JLabel(suit));
                layoutMap.put(new Point(2, 4), new JLabel(suit));
                break;

        }
    }
}