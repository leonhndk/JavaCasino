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
    private JButton saveButton;
    private JButton loadButton;
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
        this.playerBalanceLabel = new JLabel("Player Balance: 0.00 €");
        this.playerBalanceLabel.setForeground(Constants.CASINO_GOLD);
        this.betTotalLabel = new JLabel("Bet total: 0.00 €");
        betTotalLabel.setForeground(Constants.CASINO_GOLD);
        this.fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setDialogTitle("Load Game Save");
        initializeGUI();
    }

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
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBackground(Constants.CASINO_RED);
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        logTable.setFont(CASINO_FONT);
        logTable.setForeground(Constants.CASINO_GOLD);
        logTable.setEnabled(false);
        logTable.setBackground(Constants.CASINO_RED);
        logTable.setGridColor(Constants.CASINO_GOLD);
        UIManager.put("TableHeader.cellBorder", BorderFactory.createLineBorder(Constants.CASINO_GOLD, 1));
        JTableHeader tableHeader = logTable.getTableHeader();
        tableHeader.setBackground(Constants.CASINO_GREEN);
        tableHeader.setForeground(Constants.CASINO_GOLD);
        tableHeader.setFont(CASINO_FONT);
        logTable.setRowHeight(CASINO_FONT.getSize() + 10);
        int headerHeight = tableHeader.getPreferredSize().height;
        int tableContentHeight = logTable.getRowHeight() * 15;
        int preferredHeight = headerHeight + tableContentHeight;
        int preferredWidth = logTable.getPreferredSize().width;
        logPanel.add(tableHeader, BorderLayout.NORTH);
        logPanel.add(logTable, BorderLayout.CENTER);
        logPanel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        return logPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        playerHandLabel.setFont(CASINO_FONT);
        playerHandLabel.setForeground(Constants.CASINO_GOLD);
        JLabel dealerHandLabel = new JLabel("Dealer's Hand");
        dealerHandLabel.setFont(CASINO_FONT);
        dealerHandLabel.setForeground(Constants.CASINO_GOLD);
        mainPanel.setBackground(Constants.CASINO_RED);
        playerHandValueLabel.setFont(CASINO_FONT);
        dealerHandValueLabel.setFont(CASINO_FONT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        // Column 1: Player's and Dealer's Hand Labels: centered, no fill, no weight
        // Row 1 (Player)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(playerHandLabel, gbc);
        // Row 2 (Dealer)
        gbc.gridy = 1;
        gbc.weighty = 1;
        mainPanel.add(dealerHandLabel, gbc);

        // Column 2: cards panel, flexible width, anchor left, fill
        // Row 1 (Player)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(playerCardsPanel, gbc);
        // Row 2 (Dealer)
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(dealerCardsPanel, gbc);
        // Column 3: Hand Value Labels: no fill, no weight
        // Row 1: Player hand value label
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(playerHandValueLabel, gbc);
        // Row 2: Dealer hand value label
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(dealerHandValueLabel, gbc);
        return mainPanel;
    }
    private JPanel createCardsPanel() {
        CardsPanelLayout cardsPanelLayout = new CardsPanelLayout(); // use custom layout to show hands
        JPanel cardsPanel = new JPanel(cardsPanelLayout);
        cardsPanel.setBackground(Constants.CASINO_GREEN);
        cardsPanel.setBorder(BorderFactory.createLineBorder(Constants.CASINO_GOLD, 2));
        return cardsPanel;
    }

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

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = createTopButtons("Start Game");
        saveButton = createTopButtons("Save Game");
        loadButton = createTopButtons("Load Game");
        topPanel.setBackground(Constants.CASINO_RED);
        topPanel.add(startButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        playerBalanceLabel.setFont(CASINO_FONT);
        betTotalLabel.setFont(CASINO_FONT);
        topPanel.add(playerBalanceLabel);
        topPanel.add(betTotalLabel);
        return topPanel;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions here
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);
            loadButton.setEnabled(false);
            new Thread(() -> {
                try {
                    gameEngine.playNewGame();
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        playerCardsPanel.removeAll();
                        playerCardsPanel.repaint();
                        dealerCardsPanel.removeAll();
                        dealerCardsPanel.repaint();
                        startButton.setEnabled(true);
                        loadButton.setEnabled(true);
                    });
                }
                }) .start();
        } else if (e.getSource() == saveButton) {
            displayMessage("Save feature not yet implemented.");
        } else if (e.getSource() == loadButton) {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                startButton.setEnabled(false);
                loadButton.setEnabled(false);
                new Thread(() -> {
                    try {
                        gameEngine.loadAndPlay(selectedFile.toPath());
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            startButton.setEnabled(true);
                            loadButton.setEnabled(true);
                        });
                    }
                }).start();
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
    public void showCardDrawn(AbstractPlayer abstractPlayer) {

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
            e.printStackTrace();
        }

    }
}
