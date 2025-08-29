package de.esg.java.ausbildung.honl.game;

import java.util.NoSuchElementException;

public class Dealer extends AbstractPlayer {

    @Override
    public void drawCard(Deck deck) {
        try {
            Card card = deck.removeCard();
            getHand().addCard(card);
        }
        catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean isBust() {
        return getHand().getHandValue() > 21;
    }
}
