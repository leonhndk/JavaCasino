package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import java.math.BigDecimal;

public class GUI_View implements GameView {

    private JFrame frame;

    public GUI_View() {
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

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
     * @param message
     */
    @Override
    public void displayMessage(String message) {

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
