package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;

public class GameEngine {

    private final Player player;
    private final Dealer dealer;
    private Deck deck;
    private final GameView gameView;
    private final int RESHUFFLE_THRESHOLD = 20;

    public GameEngine(Player player, Dealer dealer, Deck deck, GameView gameView) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.gameView = gameView;
    }

    public void playGame() {
        gameView.displayWelcomeMsg();
        boolean playAgain = true;
        boolean sufficientFunds = true;
        while (playAgain && sufficientFunds) {
            // check for depletion of card stack
            if (checkReshuffle()) {
                gameView.displayMessage(Constants.RESHUFFLE_MSG);
            }
            // show player balance
            gameView.showPlayerBalance(player.getBalance());
            // initial Deal
        }
    }

    private boolean checkReshuffle () {
      if (deck.remainingCards() < RESHUFFLE_THRESHOLD) {
          this.deck = new Deck(2, true);
          return true;
      }
      return false;
    }

    private boolean checkSufficientFunds(BigDecimal amount) {
        if (player.getBalance().compareTo(amount) < 0) {
            gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
            return false;
        }
        return true;
    }

    private void initialDeal() {
        gameView.displayMessage(Constants.INITIAL_DEAL_MSG);
        // player draws card
        player.drawCard(deck);
        // dealer draws card (hidden)
        // player draws card
        // dealer draws card (visible)

    }

}
