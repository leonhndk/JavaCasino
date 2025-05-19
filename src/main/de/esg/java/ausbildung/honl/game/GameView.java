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

    void showCardDrawn(AbstractPlayer abstractPlayer);
}
