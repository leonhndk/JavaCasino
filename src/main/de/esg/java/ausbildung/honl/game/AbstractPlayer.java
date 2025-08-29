package de.esg.java.ausbildung.honl.game;

public abstract class AbstractPlayer {
    private final Hand hand = new Hand();

    public Hand getHand() {
        return hand;
    }

    public abstract void drawCard (Deck deck);

    public void clearHand() {
        hand.clearHand();
    }

    public abstract boolean isBust();

}
