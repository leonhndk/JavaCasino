package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;

public class GameEngine {

    private final Player player;
    private final Dealer dealer;
    private Deck deck;
    private final GameView gameView;
    private BigDecimal totalBets;
    private final int RESHUFFLE_THRESHOLD = 20;

    public GameEngine(GameView gameView) {
        this.player = new Player(Constants.STARTING_BALANCE);
        this.dealer = new Dealer();
        this.deck = new Deck(2, true);
        this.gameView = gameView;
        this.totalBets = BigDecimal.ZERO;
    }

    public void playGame() {
        gameInit();
        boolean playAgain = true;
        boolean sufficientFunds = true;
        BigDecimal bet = BigDecimal.ZERO;
        while (playAgain && sufficientFunds) {
            player.clearHand();
            dealer.clearHand();
            // check for depletion of card stack
            if (checkReshuffle()) {
                gameView.displayMessage(Constants.RESHUFFLE_MSG);
            }
            gameView.displayMessage(Constants.BUY_IN_MSG);
            if (player.placeBet(Constants.BUY_IN) == null) {
                gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
                break;
            }
            else {
                totalBets = totalBets.add(bet);
                gameView.showPlayerBalance(player.getBalance());
            }
            // show player balance
            gameView.showPlayerBalance(player.getBalance());
            // initial Deal
            initialDeal();
            // show player hand
            gameView.showPlayerHand(player);
            // show dealer hand
            gameView.showDealerHand(dealer, false);
            // check for blackjack
            if (checkBlackjack(player) || checkBlackjack(dealer)) {
                break;
            }
            // player turn
            player.placeBet(gameView.promptPlayerBet(Constants.MAX_BET));
            takeTurn(player);

            }



    }

    private boolean checkReshuffle () {
      if (deck.remainingCards() < RESHUFFLE_THRESHOLD) {
          this.deck = new Deck(2, true);
          return true;
      }
      return false;
    }


    private void initialDeal() {
        gameView.displayMessage(Constants.INITIAL_DEAL_MSG);
        // player draws card
        player.drawCard(deck);
        dealer.drawCard(deck);
        player.drawCard(deck);
        dealer.drawCard(deck);
    }

    private void gameInit() {
        gameView.displayWelcomeMsg();
        player.setPlayerName(gameView.promptPlayerName());
        gameView.showPlayerBalance(player.getBalance());
    }

    // abstraction unnecessary?
    private boolean checkBlackjack (AbstractPlayer abstractPlayer) {
        if (abstractPlayer.getHand().isBlackjack()) {
            gameView.displayMessage(Constants.BLACKJACK_MSG);
            if (abstractPlayer instanceof Player) {
                ((Player) abstractPlayer).winBet(totalBets);
                gameView.showPlayerBalance(((Player) abstractPlayer).getBalance());
            }
            else {
                gameView.displayMessage("Dealer wins!");
            }
            return true;
        }
        return false;
    }

    private boolean takeTurn (Player player) {

        gameView.showDealerHand(dealer, false);
        player.placeBet(gameView.promptPlayerBet(Constants.MAX_BET));
        gameView.showPlayerBalance(player.getBalance());

        boolean turnOver = false;
        while (!turnOver) {
            int handValue = player.getHandValue();
            gameView.showPlayerHand(player);
            if (player.isBust()) {
                gameView.displayMessage("Player is bust!");
                break;
                }
            if (handValue < 17) {
                gameView.displayMessage("Player forced to hit!");
                player.drawCard(deck);
                gameView.showCardDrawn(player);
            } else {
                boolean hit = gameView.promptPlayerAction();
                if (hit) {
                    player.drawCard(deck);
                    gameView.showCardDrawn(player);
                } else {
                    turnOver = true;
                }
            }
        }

        return false;
    }


}
