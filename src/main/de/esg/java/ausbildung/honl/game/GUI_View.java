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
     * @param playerHand
     */
    @Override
    public void showPlayerHand(Hand playerHand) {
    }

    /**
     * @param dealerHand
     * @param hideFirstCard
     */
    @Override
    public void showDealerHand(Hand dealerHand, boolean hideFirstCard) {
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
    public void showMessage(String message) {

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
}
