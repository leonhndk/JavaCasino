package de.esg.java.ausbildung.honl.game.gui;

import de.esg.java.ausbildung.honl.game.*;
import de.esg.java.ausbildung.honl.game.gui.dialogs.BetInputDialog;
import de.esg.java.ausbildung.honl.game.gui.dialogs.ConfirmDialog;
import de.esg.java.ausbildung.honl.game.gui.dialogs.NameInputDialog;
import de.esg.java.ausbildung.honl.game.gui.dialogs.YesNoDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class MainFrame extends JFrame implements ActionListener, GameView {

    private GameEngine gameEngine;
    private final JTable logTable;
    private JButton startButton;
    private JButton loadButton;
    private JButton infoButton;
    private JButton quitButton;
    private final Font CASINO_FONT = new Font("Serif", Font.BOLD, 18);
    private final JLabel playerHandLabel;
    private final JLabel playerBalanceLabel;
    private final JLabel playerHandValueLabel;
    private final JLabel dealerHandValueLabel;
    private final JPanel playerCardsPanel;
    private final JPanel dealerCardsPanel;
    private final JLabel betTotalLabel;
    private final JFileChooser fileChooser;


    public MainFrame () {
        // Initialize components that need to be created in the constructor
        String[] columnNames = {"Timestamp", "Player", "Action"};
        DefaultTableModel logTableModel = new DefaultTableModel(columnNames, 0);
        this.logTable = new JTable(logTableModel);
        this.playerCardsPanel = createCardsPanel();
        this.dealerCardsPanel = createCardsPanel();
        this.playerHandLabel = new JLabel("Player's Hand");
        this.playerHandValueLabel = new JLabel("Hand Value: 0");
        this.playerHandValueLabel.setForeground(Constants.CASINO_GOLD);
        this.dealerHandValueLabel = new JLabel("Hand Value: 0");
        this.dealerHandValueLabel.setForeground(Constants.CASINO_GOLD);
        this.playerBalanceLabel = new JLabel(Constants.BALANCE_ZERO);
        this.playerBalanceLabel.setForeground(Constants.CASINO_GOLD);
        this.betTotalLabel = new JLabel("Bet total: 0.00 €");
        betTotalLabel.setForeground(Constants.CASINO_GOLD);
        this.fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setDialogTitle("Load Game Save");
        initializeGUI();
    }

    /**
     * Set up main GUI components
     */
    public void initializeGUI() {
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        JPanel topPanel = createTopPanel();
        JPanel mainPanel = createMainPanel();
        JPanel logPanel = createLogPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(logPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }
    /**
     * Create log panel with styling
     */
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBackground(Constants.CASINO_RED);
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        logTable.setFont(CASINO_FONT);
        logTable.setForeground(Constants.CASINO_GOLD);
        logTable.setEnabled(false);
        logTable.setBackground(Constants.CASINO_RED);
        logTable.setGridColor(Constants.CASINO_RED);
        // update UIManager properties to avoid custom header renderer
        UIManager.put("TableHeader.cellBorder", BorderFactory.createLineBorder(Constants.CASINO_GOLD, 1));
        JTableHeader tableHeader = logTable.getTableHeader();
        tableHeader.setBackground(Constants.CASINO_GREEN);
        tableHeader.setForeground(Constants.CASINO_GOLD);
        tableHeader.setFont(CASINO_FONT);
        // set row height based on font size with some padding
        logTable.setRowHeight(CASINO_FONT.getSize() + 10);
        int headerHeight = tableHeader.getPreferredSize().height;
        // Show 15 rows at a time
        int tableContentHeight = logTable.getRowHeight() * 15;
        int preferredHeight = headerHeight + tableContentHeight;
        int preferredWidth = logTable.getPreferredSize().width;
        logPanel.add(tableHeader, BorderLayout.NORTH);
        logPanel.add(logTable, BorderLayout.CENTER);
        // pass preferred size so pack() works correctly
        logPanel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        return logPanel;
    }

    /**
     * Create main panel with panels for player and dealer hands with GridBagLayout for extensibility
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        playerHandLabel.setFont(CASINO_FONT);
        playerHandLabel.setForeground(Constants.CASINO_GOLD);
        JLabel dealerHandLabel = new JLabel("Dealer's Hand");
        dealerHandLabel.setFont(CASINO_FONT);
        dealerHandLabel.setForeground(Constants.CASINO_GOLD);
        mainPanel.setBackground(Constants.CASINO_RED);
        playerHandValueLabel.setFont(CASINO_FONT);
        dealerHandValueLabel.setFont(CASINO_FONT);
        // Constraints for positioning components, add weights and fill if resizing enabled
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);
        // Column 1: Player's and Dealer's Hand Labels: centered, no fill, no weight
        // Row 1 (Player)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(playerHandLabel, gbc);
        // Row 2 (Dealer)
        gbc.gridy = 1;
        mainPanel.add(dealerHandLabel, gbc);
        // Column 2: cards panel, flexible width, anchor left, fill
        // Row 1 (Player)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(playerCardsPanel, gbc);
        // Row 2 (Dealer)
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(dealerCardsPanel, gbc);
        // Column 3: Hand Value Labels: no fill, no weight
        // Row 1: Player hand value label
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(playerHandValueLabel, gbc);
        // Row 2: Dealer hand value label
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(dealerHandValueLabel, gbc);
        return mainPanel;
    }

    /**
     * helper method to create cards panel with custom layout manager
     */
    private JPanel createCardsPanel() {
        CardsPanelLayout cardsPanelLayout = new CardsPanelLayout(); // use custom layout to show hands
        JPanel cardsPanel = new JPanel(cardsPanelLayout);
        cardsPanel.setBackground(Constants.CASINO_GREEN);
        cardsPanel.setBorder(BorderFactory.createLineBorder(Constants.CASINO_GOLD, 2));
        return cardsPanel;
    }

    /**
     * create top buttons with consistent styling
     * @param caption button caption
     * @return styled JButton
     */
    private JButton createTopButtons(String caption) {
        JButton button = new JButton(caption);
        button.setFont(CASINO_FONT);
        button.setBackground(Constants.CASINO_GREEN);
        button.setForeground(Constants.CASINO_GOLD);
        button.setFocusable(false);
        button.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Constants.CASINO_GOLD, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        button.addActionListener(this);
        return button;
    }

    /**
     * Create top panel with menu buttons, display player balance and bet total
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = createTopButtons("Start Game");
        loadButton = createTopButtons("Load Game");
        infoButton = createTopButtons("About");
        quitButton = createTopButtons("Quit");
        topPanel.setBackground(Constants.CASINO_RED);
        topPanel.add(startButton);
        topPanel.add(loadButton);
        topPanel.add(infoButton);
        topPanel.add(quitButton);
        playerBalanceLabel.setFont(CASINO_FONT);
        betTotalLabel.setFont(CASINO_FONT);
        topPanel.add(playerBalanceLabel);
        topPanel.add(betTotalLabel);
        return topPanel;
    }
    /**
     * Set game engine for this view
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    private void resetGUI () {
        playerCardsPanel.removeAll();
        playerCardsPanel.repaint();
        dealerCardsPanel.removeAll();
        dealerCardsPanel.repaint();
        playerHandValueLabel.setText("Hand Value: 0");
        dealerHandValueLabel.setText("Hand Value: 0");
        playerBalanceLabel.setText(Constants.BALANCE_ZERO);
        betTotalLabel.setText("Bet total: 0.00 €");
        startButton.setEnabled(true);
        loadButton.setEnabled(true);
    }

    /**
     * Handle menu button actions and call methods to start or load game
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions here
        if (e.getSource() == startButton) {
            // disable buttons while game is running
            startButton.setEnabled(false);
            loadButton.setEnabled(false);
            // run game in separate thread to avoid freezing the GUI
            new Thread(() -> {
                try {
                    gameEngine.playNewGame();
                } finally {
                    // reset GUI components when game ends
                    SwingUtilities.invokeLater(this::resetGUI);
                }
                }) .start();
        } else if (e.getSource() == loadButton) {
            // open file chooser dialog to select save file
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                startButton.setEnabled(false);
                loadButton.setEnabled(false);
                // new thread to avoid freezing the GUI
                new Thread(() -> {
                    try {
                        // load game and start round
                        gameEngine.loadAndPlay(selectedFile.toPath());
                    } finally {
                        // reset GUI components when game ends
                        SwingUtilities.invokeLater(this::resetGUI);
                    }
                }).start();
            }
        } else if (e.getSource() == infoButton) {
            displayMessage("ESG Java Casino - " +
                    "Developed by Leon Hondyk");
        }
        else if (e.getSource() == quitButton) {
            if (promptYesNo("Are you sure you want to quit?") ) {
                System.exit(0);
            }
        }
    }

    @Override
    public void displayWelcomeMsg() {
        ConfirmDialog.showConfirm(this, Constants.WELCOME_MSG);
    }

    @Override
    public String promptPlayerName() {
        final String name = NameInputDialog.nameInput(this);
        SwingUtilities.invokeLater(() -> {
            if (name != null && !name.isEmpty()) {
                playerHandLabel.setText(name + "'s Hand");
            } else {
                playerHandLabel.setText("Player's Hand");
            }
        });
        return name;
    }

    @Override
    public void showPlayerBalance(BigDecimal balance) {
        SwingUtilities.invokeLater(() -> playerBalanceLabel.setText("Player Balance: " + balance + " €"));
    }

    @Override
    public BigDecimal promptPlayerBet(BigDecimal maxBet) {
        return BetInputDialog.promptCurrencyInput(this, maxBet);
    }

    @Override
    public void showPlayerHand(Player player) {
        SwingUtilities.invokeLater(() -> {
            playerCardsPanel.removeAll();
            Hand hand = player.getHand();
            for (Card card : hand.getCards()) {
                playerCardsPanel.add(CardRenderer.createCardView(card, false));
            }
            playerHandValueLabel.setText("Hand Value: " + hand.getHandValue());
            playerCardsPanel.revalidate();
            playerCardsPanel.repaint();
        });
    }

    @Override
    public void showDealerHand(Dealer dealer, boolean hideFirstCard) {
        SwingUtilities.invokeLater(() -> {
            dealerCardsPanel.removeAll();
            Hand hand = dealer.getHand();
            boolean hideCard = hideFirstCard;
            for (Card card : hand.getCards()) {
                dealerCardsPanel.add(CardRenderer.createCardView(card, hideCard));
                hideCard = false;
            }
            if (hideFirstCard) {
                dealerHandValueLabel.setText("Hand Value: ?");
            } else {
                dealerHandValueLabel.setText("Hand Value: " + dealer.getHand().getHandValue());
            }
            dealerCardsPanel.revalidate();
            dealerCardsPanel.repaint();
        });
    }

    @Override
    public void displayMessage(String message) {
        ConfirmDialog.showConfirm(this, message);
    }

    @Override
    public boolean promptYesNo(String message) {
        return YesNoDialog.promptYesNo(this, message) == JOptionPane.YES_OPTION;
    }

    @Override
    public void displayForcedHit(Player player) {
        String name = "Player";
        if (player.getPlayerName() != null && !player.getPlayerName().isEmpty()) {
            name = player.getPlayerName();
        }
        ConfirmDialog.showConfirm(this,name + " forced to hit!");
    }

    @Override
    public void displayBuyInMsg() {
        ConfirmDialog.showConfirm(this, Constants.BUY_IN_MSG);
    }

    @Override
    public boolean promptSaveGame() {
        return YesNoDialog.promptYesNo(this, "Would you like to save this game state?") == JOptionPane.YES_OPTION;
    }

    @Override
    public void updatePlayerName(String name) {
        SwingUtilities.invokeLater(() -> playerHandLabel.setText(name + "'s Hand"));
    }

    @Override
    public void showTotalBets(BigDecimal totalBets) {
        SwingUtilities.invokeLater(() -> betTotalLabel.setText("Bet total: " + totalBets + " €"));
    }

    /**
     * Log game events to the log table with timestamp, actor and action with last 15 events
     */
    @Override
    public void logEvent(String actor, String action) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                DefaultTableModel model = (DefaultTableModel) logTable.getModel();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(formatter);
                model.addRow(new Object[] {timestamp, actor, action});
                if (model.getRowCount() > 15) {
                    model.removeRow(0);
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
    }
}
