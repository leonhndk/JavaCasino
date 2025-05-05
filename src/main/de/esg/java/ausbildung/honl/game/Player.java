package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;

public class Player extends AbstractPlayer {

    private BigDecimal balance;
    private BigDecimal bet;
    private final GameView gameView;
    private final String playerName;

    public Player(BigDecimal startingBalance, GameView gameView, String playerName) {
        this.balance = startingBalance;
        this.gameView = gameView;
        this.bet = BigDecimal.ZERO;
        this.playerName = playerName;
    }

    public void placeBet(BigDecimal bet) {
        if (bet.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient balance to place bet!");
        }
        this.bet = bet;
        balance = balance.subtract(bet);
    }


    public BigDecimal getCurrentBet() {
        return bet;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void winBet() {
        balance = balance.add(bet).add(bet);
        bet = BigDecimal.ZERO;
    }

/*
    * This method is called when the game ties,
    * player is "refunded" bet to balance
    *
    */
    public void pushBet() {
        balance = balance.add(bet);
        bet = BigDecimal.ZERO;
    }

    public void loseBet() {
        bet = BigDecimal.ZERO;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void takeTurn (Deck deck, GameEngine gameEngine) {
        System.out.printf("Your turn! Current hand value: %d\n", hand.getHandValue());
    }
}
