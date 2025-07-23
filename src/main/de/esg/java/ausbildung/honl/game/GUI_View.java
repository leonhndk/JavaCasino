package de.esg.java.ausbildung.honl.game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class GUI_View extends JFrame implements GameView  {

    private JFrame mainWindow;
    private GameEngine gameEngine; // Using GameEngine as per your current setup
    private JPanel gamePanel;
    private JPanel cardsPanel;
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
    private JPanel logPanel;
    private JScrollPane logPane; // New: To make the log scrollable
    private final String [] columnNames = {"Time", "Player", "Action"}; // Example column names for logTable
    private String [][] logData;



    public GUI_View() {
        mainWindow = new JFrame("ESG Casino - Blackjack");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setResizable(false);
        mainWindow.setPreferredSize(new Dimension(1200, 800)); // Set a preferred size for the main window

        // Initialize components
        gamePanel = createGamePanel();
        buttonsPanel = createButtonsPanel();
        logPanel = createLogPanel();

        // Add components to the main window
        mainWindow.add(gamePanel, BorderLayout.CENTER);
        mainWindow.add(buttonsPanel, BorderLayout.NORTH);
        mainWindow.add(logPanel, BorderLayout.SOUTH);


        // Pack and set visible
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public void setGameEngine (GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        panel.add(new JButton("Action 1"));
        panel.add(new JButton("Action 2"));
        panel.add(new JButton("Action 3"));
        return panel;
    }

    private JPanel createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        cardsPanel = createCardsPanel();
        JPanel scorePanel = createScorePanel();
        gamePanel.add(cardsPanel, BorderLayout.CENTER);
        gamePanel.add(scorePanel, BorderLayout.EAST);

        return gamePanel;
    }

    private JPanel createScorePanel() {
        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Score")); // Adds a border and title

        JLabel scoreLabel = new JLabel("Player 1:");
        JTextField scoreField = new JTextField("0", 10);
        scoreField.setEditable(false);
        scorePanel.add(scoreLabel);
        scorePanel.add(scoreField);
        return scorePanel;
    }

    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        logTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(logTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCardsPanel() {
        cardsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Row 0 ---
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // Align label to the right
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        gbc.gridx = 0;
        cardsPanel.add(new JLabel("Player Hand"), gbc);

        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        gbc.gridx = 1;
        cardsPanel.add(new JLabel(new ImageIcon("KingSpades.jpg")), gbc); // Placeholder for image
        gbc.gridx = 2;
        cardsPanel.add(new JLabel(new ImageIcon("KingSpades.jpg")), gbc);
        // ... add other components for row 0

        // --- Row 1 ---
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        cardsPanel.add(new JLabel("Dealer's Hand"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        cardsPanel.add(new JLabel(new ImageIcon("placeholder.png")), gbc);
        gbc.gridx = 2;
        cardsPanel.add(new JLabel(new ImageIcon("placeholder.png")), gbc);
        // ... add other components for row 1
        cardsPanel.setBackground(new Color(226, 245, 232)); // Set background color to green

        return cardsPanel;
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

//    private void initGUI() {
//        JFrame frame = new JFrame("My Application");
//        frame.setLayout(new BorderLayout());
//e
//        mainWindow = new JFrame("ESG Casino - Blackjack");
//        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainWindow.setVisible(true);
//
//
//
//        mainWindow.setLayout(new BorderLayout());
//        scorePanel = new JPanel(new GridLayout(3, 2));
//        scorePanel.setBackground(new Color(226, 245, 232));
//        scorePanel.setPreferredSize(new Dimension(400, 400));// Set background color to green
//        scorePanel.setBorder(new EmptyBorder(30, 60, 30, 30));
//        gamePanel = new JPanel(new GridBagLayout());
//
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        gbc.insets = new Insets(5, 5, 5, 5); // Margin between cells
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.weighty = 1.0; // Make rows stretch evenly
//
//// Row 0, Column 0 — First Label
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 0.2; // 20% width
//        panel.add(new JLabel("Player Hand"), gbc);
//
//// Row 0, Column 1 — First Image Panel
//        gbc.gridx = 1;
//        gbc.weightx = 0.8; // 80% width
//        panel.add(playerHandLabel, gbc); // or use a blank JPanel
//
//// Row 1, Column 0 — Second Label
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.weightx = 0.2;
//        panel.add(new JLabel("Image B:"), gbc);
//
//// Row 1, Column 1 — Second Image Panel
//        gbc.gridx = 1;
//        gbc.weightx = 0.8;
//        panel.add(new JLabel(new ImageIcon("placeholder2.png")), gbc);
//

//        gamePanel.setPreferredSize(new Dimension(1000, 400));// Set background color to green
//        buttonsPanel = new JPanel(new FlowLayout());
//        buttonsPanel.setBackground(new Color(226, 245, 232));
//        playerHandValueLabel = new JLabel("Player Hand Value:");
//        dealerHandValueLabel = new JLabel("Dealer Hand Value:");
//        playerBalanceLabel = new JLabel("Player Balance:");
//        playerHandValue = new JLabel();
//        playerHandValue.setText("Placeholder");
//        dealerHandValue = new JLabel();
//        dealerHandValue.setText("Placeholder");
//        playerBalance = new JLabel();
//        playerBalance.setText("Placeholder €");
//
//        scorePanel.add(playerHandValueLabel);
//        scorePanel.add(playerHandValue);
//        scorePanel.add(dealerHandValueLabel);
//        scorePanel.add(dealerHandValue);
//        scorePanel.add(playerBalanceLabel);
//        scorePanel.add(playerBalance);
//        for (Component component : scorePanel.getComponents()) {
//            if (component instanceof JLabel) {
//                ((JLabel) component).setFont(new Font("Monaco", Font.BOLD, 16));
//            }
//        }
//
//        JButton newGameButton = new JButton("New Game");
//        JButton loadGameButton = new JButton("Load Game");
//        JButton saveGameButton = new JButton("Save Game");
//        buttonsPanel.add(newGameButton);
//        buttonsPanel.add(loadGameButton);
//        buttonsPanel.add(saveGameButton);
//        DefaultTableModel model = new DefaultTableModel(columnNames, 10);
//
//        logTable = new JTable(model);
//        logTable.setEnabled(false);
//        logPane = new JScrollPane(logTable);
//        mainWindow.add(logPane, BorderLayout.SOUTH);// Example: 15 rows, 3 columns
//        mainWindow.add(buttonsPanel, BorderLayout.NORTH);
//        mainWindow.add(scorePanel, BorderLayout.EAST);
//        mainWindow.add(gamePanel, BorderLayout.CENTER);
//
//        mainWindow.pack();
//        // Initialize components like gamePanel, buttonsPanel, dealerScoreLabel, playerBalanceLabel, logTable, logPane
//        // This method can be called in the constructor or wherever appropriate
    }



