package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.math.BigDecimal;

public class GUI_View implements GameView {

    private JFrame mainWindow;
    private GameEngine gameEngine; // Using GameEngine as per your current setup
    private JPanel gamePanel;
    private JPanel buttonsPanel;
    private JLabel playerScoreLabel;
    private JLabel dealerScoreLabel;
    private JLabel playerBalanceLabel;
    private JTable logTable; // New: For logging
    private JScrollPane logPane; // New: To make the log scrollable


    public GUI_View() {
        initGUI();
    }

    public void setGameEngine (GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    private void initGUI() {
        mainWindow = new JFrame("ESG Casino - Blackjack");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        mainWindow.setSize(1400, 1000);

        mainWindow.setLayout(new BorderLayout());
        gamePanel = new JPanel(new FlowLayout());
        playerScoreLabel = new JLabel("Player Score: 0");
        dealerScoreLabel = new JLabel("Dealer Score: 0");
        playerBalanceLabel = new JLabel("Player Balance: $0.00");
        gamePanel.add(playerScoreLabel);
        gamePanel.add(dealerScoreLabel);
        gamePanel.add(playerBalanceLabel);
        buttonsPanel = new JPanel(new FlowLayout());
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton saveGameButton = new JButton("Save Game");
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(loadGameButton);
        buttonsPanel.add(saveGameButton);
        logPane = new JScrollPane();
        logTable = new JTable(15, 3);
        logPane.add(logTable);
        mainWindow.add(logPane, BorderLayout.SOUTH);// Example: 15 rows, 3 columns
        mainWindow.add(buttonsPanel, BorderLayout.NORTH);
        mainWindow.add(gamePanel, BorderLayout.CENTER);
        mainWindow.pack();
        // Initialize components like gamePanel, buttonsPanel, dealerScoreLabel, playerBalanceLabel, logTable, logPane
        // This method can be called in the constructor or wherever appropriate
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
