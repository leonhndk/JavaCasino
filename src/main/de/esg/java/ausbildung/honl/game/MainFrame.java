package de.esg.java.ausbildung.honl.game;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame implements GUI_View {
    private JTable logTable;
    private final Color CASINO_GREEN = new Color(0x2d543d);
    private final Color CASINO_RED = new Color(0x952d28);
    private final Color CASINO_GOLD = new Color(0xD4AF37);
    private final JLabel playerBalanceLabel = new JLabel("Player Balance: 100.00 €");
    private final JLabel playerHandValueLabel = new JLabel("Value: 0");
    private final JLabel dealerHandValueLabel = new JLabel("Value: 20");
    private final JPanel playerCardsPanel;
    private final JPanel dealerCardsPanel;


    public MainFrame () {
        // Initialize components that need to be created in the constructor
        String[] columnNames = {"Timestamp", "Player", "Action"};
        DefaultTableModel logTableModel = new DefaultTableModel(columnNames, 10);
        this.logTable = new JTable(logTableModel);
        this.playerCardsPanel = createCardsPanel();
        this.dealerCardsPanel = createCardsPanel();
        initializeGUI();

    }

    public void initializeGUI() {
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        //((JPanel) contentPane).setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel topPanel = createTopPanel();
        JPanel mainPanel = createMainPanel();
        JPanel logPanel = createLogPanel();

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(logPanel, BorderLayout.SOUTH);
       // contentPane.setBackground(POKER_GREEN);
        pack();
        setMinimumSize(getPreferredSize()); // prevent resizing below preferred size to prevent cut off components
        System.out.println("Calculated Minimum Size: " + getPreferredSize());
        setLocationRelativeTo(null);
    }
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());

        //logPanel.setBorder(BorderFactory.createTitledBorder("Game Log"));
        logTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(logTable);

        scrollPane.setPreferredSize(new Dimension(50, 200));
        logPanel.add(scrollPane, BorderLayout.CENTER);
        return logPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Game Table"));
        JLabel playerHandLabel = new JLabel("Player's Hand");
        playerHandLabel.setForeground(CASINO_GOLD);
        JLabel dealerHandLabel = new JLabel("Dealer's Hand");
        dealerHandLabel.setForeground(CASINO_GOLD);
        mainPanel.setBackground(CASINO_RED);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        // Column 1: Player's and Dealer's Hand Labels: centered, no fill, no weight
        // Row 1 (Player)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(playerHandLabel);
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
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5 ));
        cardsPanel.setBackground(CASINO_GREEN);
        cardsPanel.setPreferredSize(new Dimension(100, 150));
        return cardsPanel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton startButton = new JButton("Start Game");
        JButton saveButton = new JButton("Save Game");
        JButton loadButton = new JButton("Load Game");
        topPanel.setBackground(CASINO_RED);
        topPanel.add(startButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(playerBalanceLabel);
        return topPanel;
    }

    @Override
    public void displayWelcomeMsg() {

    }

    @Override
    public String promptPlayerName() {
        return "";
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
}
