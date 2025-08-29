package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.nio.file.Path;

public class GameEngine {

    private final Player player;
    private final Dealer dealer;
    private Deck deck;
    private final GameView gameView;
    private BigDecimal totalBets;

    public GameEngine(GameView gameView) {
        this.player = new Player(Constants.STARTING_BALANCE);
        this.dealer = new Dealer();
        this.deck = new Deck(2, true);
        this.gameView = gameView;
        this.totalBets = BigDecimal.ZERO;
    }

    /**
     *  Start new game session, reset player values and initialize game.
     */
    public void playNewGame() {
        player.resetPlayer();
        gameInit();
        runGameLoop();
    }

    /**
     * Load saved game from file at path and start game loop.
     *
     * @param filePath Path to saved game file
     */
    public void loadAndPlay(Path filePath) {
        SaveData saveData = SaveUtils.loadSavedGame(filePath);
        if (saveData != null) {
            player.setBalance(saveData.balance());
            player.setPlayerName(saveData.playerName());
            this.deck = new Deck(0, false); // Create empty deck
            deck.addCards(saveData.cardStack(), true);
            gameView.displayMessage("Game loaded successfully.");
            gameView.logEvent("System", "Loaded game for player " + player.getPlayerName());
            gameView.updatePlayerName(player.getPlayerName());
            gameView.showPlayerBalance(player.getBalance());
            runGameLoop();
        } else {
            gameView.displayMessage("Failed to load game!");
            gameView.logEvent("System", "Failed to load game from " + filePath);
        }
    }

    /**
     * Main game loop, continue until game over or player out of funds.
     */
    private void runGameLoop() {
        boolean playAgain = true;
        while (playAgain) {
            player.clearHand();
            dealer.clearHand();
            totalBets = BigDecimal.ZERO;
            // check for depletion of card stack
            if (checkReshuffle()) {
                gameView.displayMessage(Constants.RESHUFFLE_MSG);
                gameView.logEvent("System", "Deck reshuffled");
            }
            // process buy-in
            gameView.displayBuyInMsg();
            if (player.placeBet(Constants.BUY_IN) == null) {
                gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
                break;
            }
            gameView.logEvent(player.getPlayerName(), "Placed buy-in of " + Constants.BUY_IN + " €");
            totalBets = totalBets.add(Constants.BUY_IN);
            gameView.showTotalBets(totalBets);
            // show player balance
            gameView.showPlayerBalance(player.getBalance());
            // initial Deal
            initialDeal();
            // show player hand
            gameView.showPlayerHand(player);
            // show dealer hand
            gameView.showDealerHand(dealer, true);
            // check for blackjack
            if (checkBlackjack(player) || checkBlackjack(dealer)) {
                playAgain = gameView.promptYesNo(Constants.PLAY_AGAIN_MSG);
                continue;
            }
            // player turn
            playerTurn(player);
            if (!player.isBust()) {
                dealerTurn();
            }
            determineWinner();
            playAgain = gameView.promptYesNo(Constants.PLAY_AGAIN_MSG);
        }
        // game is over
        gameView.displayMessage("Game finished, thank you for playing!");
        gameView.showPlayerBalance(player.getBalance());
        gameView.logEvent(player.getPlayerName(), "Game session ended ");
        if (gameView.promptSaveGame()) {
            if (SaveUtils.saveGame(deck, player.getPlayerName(), player.getBalance())){
                gameView.displayMessage("Game saved successfully.");
                gameView.logEvent("System", "Saved game for player " + player.getPlayerName());
            } else {
                gameView.displayMessage("Game save failed.");
                gameView.logEvent("System", "Failed to save game for player " + player.getPlayerName());
            }
        }
        if (gameView instanceof ConsoleView) {
            ((ConsoleView) gameView).closeScanner();
        }
    }

    /**
     * Determine winner, update balances and log results.
     */
    private void determineWinner() {
        int playerHandValue = player.getHand().getHandValue();
        int dealerHandValue = dealer.getHand().getHandValue();
        if (player.isBust()) {
            gameView.logEvent("Dealer", Constants.WIN_ROUND_MSG);
            gameView.displayMessage("Player busts! Dealer wins.");
            player.loseBet();
        } else if (dealer.getHand().isBust()) {
            gameView.logEvent(player.getPlayerName(), Constants.WIN_ROUND_MSG);
            player.winBet(totalBets);
            gameView.displayMessage("Dealer busts! Player wins!");
        } else if (playerHandValue > dealerHandValue) {
            gameView.displayMessage("Player wins!");
            gameView.logEvent(player.getPlayerName(), Constants.WIN_ROUND_MSG);
            player.winBet(totalBets);
        } else if (playerHandValue < dealerHandValue) {
            gameView.displayMessage("Dealer wins!");
            gameView.logEvent("Dealer", Constants.WIN_ROUND_MSG);
            player.loseBet();
        } else {
            gameView.displayMessage("It's a push (tie)!");
            gameView.showDealerHand(dealer, false);
            gameView.logEvent("System", "Round ended in push");
            player.pushBet();
        }
        gameView.showPlayerBalance(player.getBalance());
    }

    /**
     * Check if deck needs reshuffling, if so create new deck and shuffle.
     * @return true if deck reshuffled
     */
    private boolean checkReshuffle () {
        int reshuffleThreshold = 20;
        if (deck.getSize() < reshuffleThreshold) {
          this.deck = new Deck(2, true);
          return true;
      }
      return false;
    }

    /**
     * Deal two cards each
     */
    private void initialDeal() {
        gameView.displayMessage(Constants.INITIAL_DEAL_MSG);
        gameView.logEvent("System", "Initial deal");
        // player draws card
        player.drawCard(deck);
        dealer.drawCard(deck);
        player.drawCard(deck);
        dealer.drawCard(deck);
    }

    /**
     * display welcome message, initialize game, prompt for player name
     */
    private void gameInit() {
        gameView.displayWelcomeMsg();
        player.setPlayerName(gameView.promptPlayerName());
        gameView.showPlayerBalance(player.getBalance());
    }

    /**
     * Check for blackjack, display messages and update balances
     * @param abstractPlayer Player or Dealer to check for blackjack
     * @return true if blackjack
     */
    private boolean checkBlackjack (AbstractPlayer abstractPlayer) {
        if (abstractPlayer.getHand().isBlackjack()) {
            if (abstractPlayer instanceof Player) {
                gameView.displayMessage(Constants.BLACKJACK_MSG);
                gameView.displayMessage( ((Player) abstractPlayer).getPlayerName() + " wins this round");
                ((Player) abstractPlayer).winBet(totalBets);
                gameView.showPlayerBalance(((Player) abstractPlayer).getBalance());
            }
            else {
                gameView.showDealerHand((Dealer) abstractPlayer, false);
                gameView.displayMessage(Constants.BLACKJACK_MSG);
                gameView.logEvent("Dealer", Constants.WIN_ROUND_MSG);
                gameView.displayMessage("Dealer wins!");
            }
            return true;
        }
        return false;
    }
    /**
     * Perform player turn until player stands or busts.
     * @param player current player
     */
    private void playerTurn(Player player) {
        while (true) {
            // Check for bust
            if (player.isBust()) {
                gameView.displayMessage(player.getPlayerName() + " is bust!");
                return;
            }
            // Display current hand and prompt for bet
            gameView.showPlayerHand(player);
            // Prompt for additional bet
            BigDecimal additionalBet = gameView.promptPlayerBet(
                    player.getBalance().min(Constants.MAX_BET));
            gameView.logEvent(player.getPlayerName(), "Placed bet of " + additionalBet + " €");
            // displays insufficient funds message if bet exceeds balance
            if (additionalBet != null && additionalBet.compareTo(BigDecimal.ZERO) > 0) {
                if (player.placeBet(additionalBet) == null) {
                    gameView.displayMessage(Constants.INSUFFICIENT_FUNDS_MSG);
                    break;
                }
                // Update balance and total bets
                gameView.showPlayerBalance(player.getBalance());
                totalBets = totalBets.add(additionalBet);
                gameView.showTotalBets(totalBets);
            }
            // Force hit for hands < 17
            if (player.getHand().getHandValue() < 17) {
                gameView.displayForcedHit(player);
                player.drawCard(deck);
                gameView.logEvent(player.getPlayerName(), Constants.DRAW_CARD_MSG);
                gameView.showPlayerHand(player); // Update the hand display
                try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                continue;
            }
            // Prompt for action
            boolean wantsToHit = gameView.promptYesNo("Do you wish to draw another card?");
            if (wantsToHit) {
                player.drawCard(deck);
                gameView.logEvent(player.getPlayerName(), Constants.DRAW_CARD_MSG);
                gameView.showPlayerHand(player); // Update the hand display
                // Pause so user can see card before next message appears
                try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            } else {
                gameView.displayMessage(player.getPlayerName() + " stands.");
                gameView.logEvent(player.getPlayerName(), "Stands");
                break;
            }
        }
    }


    /**
     * Perform dealer turn, force stand on 17 or higher, hit otherwise.
     */
    private void dealerTurn() {
        gameView.showDealerHand(dealer, false);
        if (dealer.getHand().getHandValue() >= 17) {
            gameView.displayMessage("Dealer stands.");
            gameView.logEvent("Dealer", "Stands");
            return;
        }
        while (dealer.getHand().getHandValue() < 17) {
            // pause to show cards before next dialog
            try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            dealer.drawCard(deck);
            gameView.logEvent("Dealer", Constants.DRAW_CARD_MSG);
            gameView.showDealerHand(dealer, false); // Update the hand display
        }

    }
}
