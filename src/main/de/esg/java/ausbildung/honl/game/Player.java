package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class Player extends AbstractPlayer {

    private BigDecimal balance;
    private BigDecimal bet;
    private String playerName;

    public Player(BigDecimal startingBalance) {
        this.balance = startingBalance;
        this.bet = BigDecimal.ZERO;
        playerName = "Player";
    }

    public BigDecimal placeBet(BigDecimal bet) {
        if (checkSufficientFunds(bet)) {
            balance = balance.subtract(bet);
            return bet;
        } else {
            return null;
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHandValue() {
        return getHand().getHandValue();
    }

    public boolean checkSufficientFunds(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public BigDecimal getCurrentBet() {
        return bet;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void winBet(BigDecimal totalBets) {
        balance = balance.add(totalBets).add(totalBets);
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

    public boolean isBust (){
        return getHand().getHandValue() > 21;
    }
    @Override
    public void drawCard(Deck deck) {
        try {
            getHand().addCard(deck.removeCard());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
}
