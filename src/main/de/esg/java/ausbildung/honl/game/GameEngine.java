package de.esg.java.ausbildung.honl.game;

public class GameEngine {

    private final AbstractPlayer player;
    private final AbstractPlayer dealer;
    private final Deck deck;
    private final GameView gameView;


    public GameEngine(AbstractPlayer player, AbstractPlayer dealer, Deck deck, GameView gameView) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.gameView = gameView;
    }

    public void startRound() {
       player.clearHand();
       dealer.clearHand();

       player.getHand().addCard(deck.drawCard());
       dealer.getHand().addCard(deck.drawCard());
       player.getHand().addCard(deck.drawCard());
       dealer.getHand().addCard(deck.drawCard());

    }

}
