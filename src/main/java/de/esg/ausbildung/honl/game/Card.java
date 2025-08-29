package de.esg.ausbildung.honl.game;

/**
 * holds constructor for card and methods to format String output
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

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

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Card card)) {
            return false;
        }
        if (card.suit == null || card.rank == null) {
            return false;
        }
        return suit == card.suit && rank == card.rank;
    }
}