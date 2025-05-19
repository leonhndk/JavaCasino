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
        BigDecimal bet = BigDecimal.ZERO;
        while (playAgain) {
            player.clearHand();
            dealer.clearHand();
            totalBets = BigDecimal.ZERO;
            // check for depletion of card stack
            if (checkReshuffle()) {
                gameView.displayMessage(Constants.RESHUFFLE_MSG);
            }
            gameView.displayMessage(Constants.BUY_IN_MSG);
            if (player.placeBet(Constants.BUY_IN) == null) {
                gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
                break;
            }

            totalBets = totalBets.add(Constants.BUY_IN);

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
                playAgain = gameView.promptYesNo(Constants.PLAY_AGAIN_MSG);
                continue;
            }
            // player turn
            boolean playerTurnResult = playerTurn(player);
            if (!player.isBust()) {
                dealerTurn();
            }
            determineWinner();
            playAgain = gameView.promptYesNo(Constants.PLAY_AGAIN_MSG);
            }
        // game is over
        gameView.displayMessage("Game finished, thank you for playing!");
        gameView.showPlayerBalance(player.getBalance());
        if (SaveUtils.saveGame(deck, player.getPlayerName(), player.getBalance())){
            gameView.displayMessage("Game saved successfully.");
        } else {
            gameView.displayMessage("Game save failed.");
        }
        if (gameView instanceof ConsoleView) {
            ((ConsoleView) gameView).closeScanner();
        }
    }

    private void determineWinner() {
        // use list of players when implementing multiplayer
        int playerHandValue = player.getHand().getHandValue();
        int dealerHandValue = dealer.getHand().getHandValue();
        if (player.isBust()) {
            gameView.displayMessage("Player busts! Dealer wins.");
            player.loseBet();
        } else if (dealer.getHand().isBust()) {
            gameView.displayMessage("Dealer busts! Player wins!");
            player.winBet(totalBets);
        } else if (playerHandValue > dealerHandValue) {
            gameView.displayMessage("Player wins!");
            player.winBet(totalBets);
        } else if (playerHandValue < dealerHandValue) {
            gameView.displayMessage("Dealer wins!");
            player.loseBet();
        } else {
            gameView.displayMessage("It's a push (tie)!");
            player.pushBet();
        }

        gameView.showPlayerBalance(player.getBalance());
    }

    private boolean checkReshuffle () {
      if (deck.getSize() < RESHUFFLE_THRESHOLD) {
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
        if (SaveUtils.gameSaveExists()) {
            if (gameView.promptYesNo(Constants.LOAD_GAME_MSG)) {
                SaveData saveData = SaveUtils.loadSavedGame(Constants.filePath);
                if (saveData != null) {
                    player.setBalance(saveData.balance());
                    deck.addCards(saveData.cardStack(), true);
                    gameView.displayMessage("Game loaded successfully.");
                }
                else {
                    gameView.displayMessage("Failed to load game! Continuing with new game...");
                }
            }

        }
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

    private boolean playerTurn(Player player) {
        while (true) {
            // Check for bust
            if (player.isBust()) {
                gameView.displayMessage(player.getPlayerName() + " is bust!");
                return false;
            }

            // Display current hand and prompt for bet
            gameView.showPlayerHand(player);

            // Prompt for additional bet
            BigDecimal additionalBet = gameView.promptPlayerBet(
                    player.getBalance().min(Constants.MAX_BET));

            // Place additional bet
            // check might be unnecessary, as placeBet() already checks for sufficient funds
            if (additionalBet != null && additionalBet.compareTo(BigDecimal.ZERO) > 0) {
                if (player.placeBet(additionalBet) == null) {
                    gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
                    break;
                }
                totalBets = totalBets.add(additionalBet);
            }

            // Force hit for hands < 17
            if (player.getHandValue() < 17) {
                gameView.displayMessage(player.getPlayerName() + " forced to hit!");
                player.drawCard(deck);
                gameView.showCardDrawn(player);
                continue;
            }

            // Prompt for action
            boolean wantsToHit = gameView.promptYesNo("Do you wish to draw another card?");
            if (wantsToHit) {
                player.drawCard(deck);
                gameView.showCardDrawn(player);
            } else {
                break;
            }
        }
        return true;
    }

    private void dealerTurn() {
        gameView.showDealerHand(dealer, false);
        while (dealer.getHand().getHandValue() < 17) {
            dealer.drawCard(deck);
            gameView.displayMessage("Dealer draws a card...");
            gameView.showCardDrawn(dealer);
        }
    }

}
