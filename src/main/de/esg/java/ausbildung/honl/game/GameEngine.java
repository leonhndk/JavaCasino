package de.esg.java.ausbildung.honl.game;

public class GameEngine {

    private final AbstractPlayer player;
    private final AbstractPlayer dealer;
    private final Deck deck;

    public GameEngine(AbstractPlayer player, AbstractPlayer dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
    }


}
