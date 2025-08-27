package de.esg.java.ausbildung.honl.game;

/**
 * holds constructor for card and methods to format String output
 */
public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getCardValue() {
        return rank.getCardValue();
    }

    public Rank getRank() {
        return rank;
    }

    public  Suit getSuit() {
        return suit;
    }

//    /**
//     * @param cardDescription returns a string that is exactly 19 characters long by adding whitespace if needed
//     */
//    private String normalizeString(String cardDescription) {
//        if (cardDescription.length() > 19) {
//            return null;
//        }
//        StringBuilder normalizedDescription = new StringBuilder(cardDescription);
//        while (normalizedDescription.length() < 19) {
//            normalizedDescription.append(" ");
//        }
//        return normalizedDescription.toString();
//    }


    public String consoleString() {
        String suitIcon = suit.getIconColor() + suit.getIcon() + "\u001B[0m";
        return suitIcon + rank.getLabel() + "\t";
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card card = (Card) obj;
        if (card.suit == null || card.rank == null) {
            return false;
        }
        return suit == card.suit && rank == card.rank;
    }
}