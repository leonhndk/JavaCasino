package de.esg.java.ausbildung.honl.game;

import java.util.*;

public class CardStack {

    private final Deque<Card> cards;

    public CardStack(ArrayList<Card> initialCards) {
        this.cards = new ArrayDeque<>(initialCards);
    }

    public Card drawCard () {
        // will return null if CardStack is empty
        Card card = cards.pollFirst();
        if (card == null) {
            throw new NoSuchElementException ("No cards left in the stack!");
        }
        return card;
    }

    public void shuffle () {
        ArrayList<Card> toShuffle = new ArrayList<>(cards);
        Collections.shuffle(toShuffle);
        cards.clear();
        cards.addAll(toShuffle);
    }

    public int getSize () {
        return cards.size();
    }

    public boolean isEmpty () {
        return cards.isEmpty();
    }
}
