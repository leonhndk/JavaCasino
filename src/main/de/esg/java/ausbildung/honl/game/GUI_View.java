package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;

public class GUI_View implements GameView {

    /**
     *
     */
    @Override
    public void displayWelcomeMsg() {

    }

    /**
     * @return
     */
    @Override
    public String promptPlayerName() {
        return "";
    }

    /**
     *
     */
    @Override
    public void displayBuyIn() {

    }
    /**
     *
     */
    @Override
    public void showPlayerHand (Player player) {
        System.out.printf("%s's hand: %s", player.getPlayerName(), player.getHand().toString());

    }

    /**
     * @param balance
     */
    @Override
    public void showPlayerBalance(BigDecimal balance) {

    }

    /**
     * @return
     */
    @Override
    public BigDecimal promptPlayerBet(BigDecimal maxBet) {
        return BigDecimal.ZERO;
    }

    /**
     * @param dealer
     * @param hideFirstCard
     */
    @Override
    public void showDealerHand(Dealer dealer, boolean hideFirstCard) {
    }

    /**
     * @return
     */
    @Override
    public boolean promptPlayerAction() {

        return false;
    }

    /**
     * @param message
     */
    @Override
    public void displayMessage(String message) {

    }

    /**
     * @return
     */
    @Override
    public boolean promptPlayAgain() {
        return false;
    }

    /**
     * @param message
     * @return
     */
    @Override
    public boolean promptYesNo(String message) {
        return false;
    }

    /**
     * @param abstractPlayer
     */
    @Override
    public void showCardDrawn(AbstractPlayer abstractPlayer) {

    }
}
