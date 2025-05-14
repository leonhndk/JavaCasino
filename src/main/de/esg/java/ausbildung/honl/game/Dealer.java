package de.esg.java.ausbildung.honl.game;

import java.util.NoSuchElementException;

public class Dealer extends AbstractPlayer {
    private static final String DEALER_NAME = "Dealer";

    @Override
    public Card drawCard(Deck deck) {
        try {
            Card card = deck.removeCard();
            getHand().addCard(card);
            return card;
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isBust() {
        return getHand().getHandValue() > 21;
    }
}
