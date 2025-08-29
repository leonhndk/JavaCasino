package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;


public interface GameView {
    void displayWelcomeMsg();

    String promptPlayerName();

    void showPlayerBalance(BigDecimal balance);

    BigDecimal promptPlayerBet(BigDecimal maxBet);

    void showPlayerHand(Player player);

    void showDealerHand(Dealer dealer, boolean hideFirstCard);

    void displayMessage(String message);

    boolean promptYesNo(String message);

    void displayBuyInMsg();

    void displayForcedHit(Player player);

    boolean promptSaveGame();

    void updatePlayerName(String name);

    void showTotalBets(BigDecimal totalBets);

    void logEvent (String actor, String action);

}
