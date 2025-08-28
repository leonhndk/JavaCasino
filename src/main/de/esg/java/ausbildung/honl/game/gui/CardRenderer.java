package de.esg.java.ausbildung.honl.game.gui;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.attributes.ViewBox;
import com.github.weisj.jsvg.parser.SVGLoader;
import de.esg.java.ausbildung.honl.game.Card;
import de.esg.java.ausbildung.honl.game.Constants;
import de.esg.java.ausbildung.honl.game.Rank;
import de.esg.java.ausbildung.honl.game.Suit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * helper class to render playing cards as JPanels with factory methods
 */
public final class CardRenderer {

    private static final Dimension CARD_SIZE = new Dimension(160, 240);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Font CARD_FONT = new Font("Arial", Font.BOLD, 22);
    private static final Map<Suit, ImageIcon> ICON_CACHE = new EnumMap<>(Suit.class);
    private static final int ICON_SIZE = 24;

    public static Dimension getCardDimension() {
        return CARD_SIZE;
    }

    /**
     * Creates and returns a JPanel that visually represents a playing card.
     * This is the primary method to be used from outside this class.
     *
     * @param card     The Card object to render. If null, it will render a face-down card.
     * @param faceDown
     * @return A configured JPanel representing the card.
     */
    public static JPanel createCardView(Card card, boolean faceDown) {
        JPanel cardView = new JPanel(new BorderLayout(5, 5));
        String rankLabel = card.getRank().getLabel();
        cardView.setPreferredSize(CARD_SIZE);
        cardView.setBackground(CARD_BACKGROUND);
        cardView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        if (faceDown) {
            // Render face-down card (empty panel with casino gold background)
            cardView.setBackground(Constants.CASINO_GOLD);
            return cardView;
        }
        // Top panel with rank label
        JLabel rankLabelTop = new JLabel(rankLabel);
        rankLabelTop.setFont(CARD_FONT);
        rankLabelTop.setBorder(new EmptyBorder(8, 12 , 0, 0));
        cardView.add(rankLabelTop, BorderLayout.NORTH);
        // bottom panel with rank label
        JLabel rankLabelBottom = new JLabel(rankLabel);
        rankLabelBottom.setFont(CARD_FONT);
        rankLabelBottom.setBorder(new EmptyBorder(0, 0 , 8, 12));
        rankLabelBottom.setHorizontalAlignment(SwingConstants.RIGHT);
        cardView.add(rankLabelBottom, BorderLayout.SOUTH);
        JPanel centerPanel = createGridPanel(getLayout(card));
        centerPanel.setBackground(Color.WHITE);
        cardView.add(centerPanel, BorderLayout.CENTER);
        return cardView;
    }

    /**
     * Load an ImageIcon for suit and cache it
     * @param suit The suit to load the icon for.
     * @return ImageIcon
     */
    private static ImageIcon loadSuitIcon(Suit suit) {
        // Check cache for icon, return if found
        if (ICON_CACHE.containsKey(suit)) {
            return ICON_CACHE.get(suit);
        }
        // If not in cache load from resources folder
        String path = "/resources/" + suit.name().toLowerCase() + ".svg";
        URL resourceUrl = CardRenderer.class.getResource(path);
        Objects.requireNonNull(resourceUrl, "Cannot find SVG: " + path);
        try {
            // load svg file with external library
            SVGLoader loader = new SVGLoader();
            SVGDocument svgDocument = loader.load(resourceUrl);
            // blank canvas to render SVG onto
            BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Render SVG onto canvas.
            if (svgDocument == null) {
                throw new RuntimeException("SVG is null for path: " + path);
            }
            svgDocument.render(null, g2d, new ViewBox(0, 0, ICON_SIZE, ICON_SIZE));
            g2d.dispose();
            // Create ImageIcon, cache and return it
            ImageIcon icon = new ImageIcon(image);
            ICON_CACHE.put(suit, icon);
            return icon;
        } catch (Exception e) {
            // If anything goes wrong, throw runtime exception.
            throw new RuntimeException("Failed to load SVG: " + path, e);
        }
    }
    
    
    /**
     * Create a JPanel with a 5x3 grid layout, add suit symbols or placeholders panels according to layoutMap.
     * @param layoutMap map with point coordinates as keys and JPanel components as values.
     * @return A JPanel representing the card with suit symbols.
     */
    private static JPanel createGridPanel(Map<Point, Component> layoutMap) {
        JPanel gridPanel = new JPanel(new GridLayout(5, 3));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                Component component = layoutMap.get(new Point(col, row));
                if (component != null) {
                    gridPanel.add(component);
                }
                else {
                    JPanel emptyPanel = new JPanel();
                    emptyPanel.setOpaque(false);
                    emptyPanel.setForeground(Color.WHITE);
                    gridPanel.add(emptyPanel);
                }
            }
        }
        return gridPanel;
    }

    /**
     * Create layout for placement of suit symbols on the card in a 5x3 grid.
     * @param card to render
     * @return Map with Point coordinates as key values and JLabel components as values
     */
    private static Map<Point, Component> getLayout(Card card) {
        Suit suit = card.getSuit();
        Rank rank = card.getRank();
        Map<Point, Component> layoutMap = new HashMap<>();
        ImageIcon suitIcon = loadSuitIcon(suit);
        boolean redSuit = suit.equals(Suit.HEARTS) || suit.equals(Suit.DIAMONDS);
        switch (rank.getLabel()) {
            case "2":
                layoutMap.put(new Point(1, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 4), getIconLabel(suitIcon));
                break;
            case "3":
                layoutMap.put(new Point(1, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 4), getIconLabel(suitIcon));
                break;
            case "4":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "5":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "6":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "7":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "8":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "9":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 2), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            case "10":
                layoutMap.put(new Point(0, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 0), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 1), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(1, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 3), getIconLabel(suitIcon));
                layoutMap.put(new Point(0, 4), getIconLabel(suitIcon));
                layoutMap.put(new Point(2, 4), getIconLabel(suitIcon));
                break;
            default:
                // For face cards and Ace place suit symbol in the center
                layoutMap.put(new Point(1, 2), getIconLabel(suitIcon));
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

    private static Component getIconLabel(ImageIcon suitIcon) {
        return new JLabel(suitIcon);
    }

}