package de.esg.java.ausbildung.honl.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int getHandValue () {
        return 0;
    }

    public boolean isBlackjack () {
        return false;
    }

    public boolean isBust () {
        return false;
    }

    public void clearHand () {
        cards.clear();
    }
}
