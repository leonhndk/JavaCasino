package de.esg.java.ausbildung.honl.game;

public abstract class AbstractPlayer {
    private Hand hand = new Hand();



    public Hand getHand() {
        return hand;
    }

    public abstract Card drawCard (Deck deck);

    public void clearHand() {
        hand.clearHand();
    }

    public abstract boolean isBust();

}
