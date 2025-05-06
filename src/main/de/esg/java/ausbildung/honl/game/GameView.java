package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;

public interface GameView {
    void displayWelcomeMsg();

    String promptPlayerName();

    void displayBuyIn();

    void showPlayerBalance(BigDecimal balance);

    BigDecimal promptPlayerBet(BigDecimal maxBet);

    void showPlayerHand(Hand playerHand);

    void showDealerHand(Hand dealerHand, boolean hideFirstCard);

    boolean promptPlayerAction();

    void displayMessage(String message);

    boolean promptPlayAgain();

    boolean promptYesNo(String message);
}
