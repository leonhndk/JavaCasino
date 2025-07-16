package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class GUI_View implements GameView {

    private JFrame mainWindow;
    private GameEngine gameEngine; // Using GameEngine as per your current setup
    private JPanel gamePanel;
    private JPanel scorePanel; // New: For displaying scores
    private JPanel buttonsPanel;
    private JLabel playerHandValueLabel;
    private JLabel dealerHandValueLabel;
    private JLabel playerBalanceLabel;
    private JLabel dealerHandValue;
    private JLabel playerHandValue;
    private JLabel playerBalance;
    private JLabel playerHandLabel; // New: For displaying dealer's score
    private JLabel dealerHandLabel;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel; // New: For displaying dealer's hand
    private JTable logTable; // New: For logging
    private JScrollPane logPane; // New: To make the log scrollable
    private final String [] columnNames = {"Time", "Player", "Action"}; // Example column names for logTable
    private String [][] logData;



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



        mainWindow.setLayout(new BorderLayout());
        scorePanel = new JPanel(new GridLayout(3, 2));
        scorePanel.setBackground(new Color(226, 245, 232));
        scorePanel.setPreferredSize(new Dimension(400, 400));// Set background color to green
        scorePanel.setBorder(new EmptyBorder(30, 60, 30, 30));
        gamePanel = new JPanel(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Margin between cells
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // Make rows stretch evenly

// Row 0, Column 0 — First Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2; // 20% width
        panel.add(new JLabel("Player Hand"), gbc);

// Row 0, Column 1 — First Image Panel
        gbc.gridx = 1;
        gbc.weightx = 0.8; // 80% width
        panel.add(playerHandLabel, gbc); // or use a blank JPanel

// Row 1, Column 0 — Second Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Image B:"), gbc);

// Row 1, Column 1 — Second Image Panel
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(new JLabel(new ImageIcon("placeholder2.png")), gbc);

        gamePanel.setBackground(new Color(66, 126, 96));
        gamePanel.setPreferredSize(new Dimension(1000, 400));// Set background color to green
        buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(new Color(226, 245, 232));
        playerHandValueLabel = new JLabel("Player Hand Value:");
        dealerHandValueLabel = new JLabel("Dealer Hand Value:");
        playerBalanceLabel = new JLabel("Player Balance:");
        playerHandValue = new JLabel();
        playerHandValue.setText("Placeholder");
        dealerHandValue = new JLabel();
        dealerHandValue.setText("Placeholder");
        playerBalance = new JLabel();
        playerBalance.setText("Placeholder €");

        scorePanel.add(playerHandValueLabel);
        scorePanel.add(playerHandValue);
        scorePanel.add(dealerHandValueLabel);
        scorePanel.add(dealerHandValue);
        scorePanel.add(playerBalanceLabel);
        scorePanel.add(playerBalance);
        for (Component component : scorePanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setFont(new Font("Monaco", Font.BOLD, 16));
            }
        }

        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton saveGameButton = new JButton("Save Game");
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(loadGameButton);
        buttonsPanel.add(saveGameButton);
        DefaultTableModel model = new DefaultTableModel(columnNames, 10);

        logTable = new JTable(model);
        logTable.setEnabled(false);
        logPane = new JScrollPane(logTable);
        mainWindow.add(logPane, BorderLayout.SOUTH);// Example: 15 rows, 3 columns
        mainWindow.add(buttonsPanel, BorderLayout.NORTH);
        mainWindow.add(scorePanel, BorderLayout.EAST);
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
