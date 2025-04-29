package de.esg.java.ausbildung.honl.game;

public abstract class AbstractPlayer {

    protected Hand hand = new Hand();

    public Hand getHand() {
        return hand;
    }

    public abstract void takeTurn (Deck deck, GameEngine gameEngine);
}
