package de.esg.java.ausbildung.honl.game.gui;

import de.esg.java.ausbildung.honl.game.*;
import de.esg.java.ausbildung.honl.game.gui.dialogs.BetInputDialog;
import de.esg.java.ausbildung.honl.game.gui.dialogs.ConfirmDialog;
import de.esg.java.ausbildung.honl.game.gui.dialogs.NameInputDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
//    private JOptionPane optionPane;
//    private final JFileChooser fileChooser;
    private final Color CASINO_GREEN = Constants.CASINO_GREEN;
    private final Color CASINO_RED = Constants.CASINO_RED;
    private final Color CASINO_GOLD = Constants.CASINO_GOLD;
    private final Font CASINO_FONT = new Font("Serif", Font.BOLD, 18);
    private final Font LOG_FONT = new Font("Serif", Font.PLAIN, 14);
    private final JLabel playerHandLabel;
    private final JLabel playerBalanceLabel;
    private final JLabel playerHandValueLabel;
    private final JLabel dealerHandValueLabel;
    private final JPanel playerCardsPanel;
    private final JPanel dealerCardsPanel;


    public MainFrame () {
        // Initialize components that need to be created in the constructor
        String[] columnNames = {"Timestamp", "Player", "Action"};
        DefaultTableModel logTableModel = new DefaultTableModel(columnNames, 10);
        this.logTable = new JTable(logTableModel);
        this.playerCardsPanel = createCardsPanel();
        this.dealerCardsPanel = createCardsPanel();
//        playerCardsPanel.add(CardRenderer.createCardView(new Card(Rank.KING, Suit.HEARTS))); // temporary cards for testing
//        dealerCardsPanel.add(CardRenderer.createCardView(new Card(Rank.FIVE, Suit.SPADES)));
//        dealerCardsPanel.add(CardRenderer.createCardView(new Card(Rank.NINE, Suit.CLUBS)));
        this.playerHandLabel = new JLabel("Player's Hand");
        this.playerHandValueLabel = new JLabel("Player Hand Value: 0");
        this.playerHandValueLabel.setForeground(CASINO_GOLD);
        this.dealerHandValueLabel = new JLabel("Dealer Hand Value: 0");
        this.dealerHandValueLabel.setForeground(CASINO_GOLD);
        this.playerBalanceLabel = new JLabel("Player Balance: 0.00 €");
        this.playerBalanceLabel.setForeground(CASINO_GOLD);
//        fileChooser = new JFileChooser();
//        optionPane = new JOptionPane();
        initializeGUI();
    }

    public void initializeGUI() {
//        UIManager.put("OptionPane.background", CASINO_RED);
//        UIManager.put("OptionPane.messageForeground", CASINO_GOLD);
//        UIManager.put("Button.background", CASINO_GREEN);
//        UIManager.put("Button.foreground", CASINO_GOLD);
        UIManager.put("Button.focusPainted", false);
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
        logTable.setEnabled(false);
        logTable.setBackground(CASINO_RED);
        logTable.setGridColor(CASINO_GOLD);
        UIManager.put("TableHeader.cellBorder", BorderFactory.createLineBorder(CASINO_GOLD, 1));
        JTableHeader tableHeader = logTable.getTableHeader();
        tableHeader.setBackground(CASINO_GREEN);
        tableHeader.setForeground(CASINO_GOLD);
        tableHeader.setFont(CASINO_FONT);
        int headerHeight = tableHeader.getPreferredSize().height;
        int tableContentHeight = logTable.getRowHeight() * logTable.getRowCount();
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
        playerHandLabel.setForeground(CASINO_GOLD);
        JLabel dealerHandLabel = new JLabel("Dealer's Hand");
        dealerHandLabel.setFont(CASINO_FONT);
        dealerHandLabel.setForeground(CASINO_GOLD);
        mainPanel.setBackground(CASINO_RED);
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
        cardsPanel.setBackground(CASINO_GREEN);
        cardsPanel.setBorder(BorderFactory.createLineBorder(CASINO_GOLD, 2));
        return cardsPanel;
    }

    private JButton createTopButtons(String caption) {
        JButton button = new JButton(caption);
        button.setFont(CASINO_FONT);
        button.setBackground(CASINO_GREEN);
        button.setForeground(CASINO_GOLD);
        button.setFocusable(false);
        button.setBorder(new CompoundBorder(BorderFactory.createLineBorder(CASINO_GOLD, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        button.addActionListener(this);
        return button;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = createTopButtons("Start Game");
        saveButton = createTopButtons("Save Game");
        loadButton = createTopButtons("Load Game");
        topPanel.setBackground(CASINO_RED);
        topPanel.add(startButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        playerBalanceLabel.setFont(CASINO_FONT);
        topPanel.add(playerBalanceLabel);
        return topPanel;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions here
        if (e.getSource() == startButton) {
            ConfirmDialog.showConfirm(this, "youre bust!");
        } else if (e.getSource() == saveButton) {
            // Save game logic
//            NameInputDialog.nameInput(this);
            System.out.println("Save Game button clicked");
        } else if (e.getSource() == loadButton) {
            BetInputDialog.promptCurrencyInput(this);
            // Load game logic
//            int returnValue = fileChooser.showOpenDialog(this);
//            if (returnValue == JFileChooser.APPROVE_OPTION) {
//                // Handle file selection
//                String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
//                Path filePath = Paths.get(selectedFile);
//                SaveUtils.loadSavedGame(filePath);
//            } else {
//                System.out.println("File selection cancelled.");
//            }
        }
    }

    @Override
    public void displayWelcomeMsg() {

    }

    @Override
    public String promptPlayerName() {
        playerHandLabel.setText(NameInputDialog.nameInput(this) + "'s Hand");
        return null;
    }

    @Override
    public void showPlayerBalance(BigDecimal balance) {

    }

    @Override
    public BigDecimal promptPlayerBet(BigDecimal maxBet) {

        return null;
    }

    @Override
    public void showPlayerHand(Player player) {

    }

    @Override
    public void showDealerHand(Dealer dealer, boolean hideFirstCard) {
        Hand hand = dealer.getHand();
        for (Card card: hand.getCards()) {
            dealerCardsPanel.add(CardRenderer.createCardView(card, hideFirstCard));
            hideFirstCard = false;
        }
    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public boolean promptYesNo(String message) {
        return false;
    }

    @Override
    public void showCardDrawn(AbstractPlayer abstractPlayer) {

    }

    @Override
    public void displayBuyInMsg() {
        ConfirmDialog.showConfirm(this, Constants.BUY_IN_MSG);
    }
}
