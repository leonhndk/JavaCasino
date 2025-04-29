package de.esg.java.ausbildung.honl.game;

import java.util.*;

/**
 * logic to create deck of cards
 * stack of cards from which cards are dealt is also represented as an object of type Deck (one or more decks in stack)
 */
public class Deck {

    private final CardStack cardStack;

    /**
     * @param numberOfDecks: allows for multiple decks of cards in one playing deck
     * @param shuffle true if deck should be shuffled, flag for testing purposes
     */
    public Deck(int numberOfDecks, boolean shuffle) {
        ArrayList<Card> fullDeck = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    fullDeck.add(new Card(rank, suit));
                }
            }
        }
        this.cardStack = new CardStack(fullDeck);
        if (shuffle) {
            this.cardStack.shuffle();
        }
    }

    public int remainingCards() {
        return cardStack.getSize();
    }

    public void shuffleStack() {
        cardStack.shuffle();
    }

    public boolean isEmpty() {
        return cardStack.isEmpty();
    }
    /**
     * draws card from the top of the deck, removes it from deck
     */
    public Card drawCard() {
        return cardStack.drawCard();
    }

}