package de.esg.java.ausbildung.honl.game;

import java.util.*;

/**
 * logic to create deck of cards
 * stack of cards from which cards are dealt is also represented as an object of type Deck (one or more decks in stack)
 */
public class Deck {

    private final ArrayDeque<Card> cardStack;
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
        if (shuffle) {
            Collections.shuffle(fullDeck);
        }
        this.cardStack = new ArrayDeque<>(fullDeck);
    }

    public int getSize() {
        return cardStack.size();
    }

    public ArrayList<Card> getCards() {
        return new ArrayList<>(cardStack);
    }

    public void shuffleStack() {
        ArrayList<Card> toShuffle = new ArrayList<>(cardStack);
        Collections.shuffle(toShuffle);
        cardStack.clear();
        cardStack.addAll(toShuffle);
    }

    public boolean isEmpty() {
        return cardStack.isEmpty();
    }

    public void addCards(ArrayList<Card> cards, boolean replace) {
        if (replace) {
            cardStack.clear();
        }
        cardStack.addAll(cards);
    }
    /**
     * draws card from the top of the deck, removes it from deck
     */
    public Card removeCard() {
        if (cardStack.isEmpty()) {
            throw new NoSuchElementException("Card stack is empty");
        }
        return cardStack.pollFirst();
    }

}