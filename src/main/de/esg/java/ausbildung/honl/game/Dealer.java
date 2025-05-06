package de.esg.java.ausbildung.honl.game;

import java.util.NoSuchElementException;

public class Dealer extends AbstractPlayer {
    private static final String DEALER_NAME = "Dealer";

    @Override
    public void drawCard(Deck deck) {
        try {
            getHand().addCard(deck.removeCard());
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
}
