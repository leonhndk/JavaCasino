package de.esg.java.ausbildung.honl.game;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame implements GUI_View {

    private JTable logTable;
    private JLabel playerBalance;
    private final JLabel playerHandLabel = new JLabel("Player Hand:");
    private final JLabel dealerHandLabel = new JLabel("Dealer Hand:");
    private final JLabel playerHandValueLabel = new JLabel("Player Hand Value:");
    private final JLabel dealerHandValueLabel = new JLabel("Dealer Hand Value:");
    private JLabel dealerHandValue;
    private JLabel playerHandValue;
    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;

    public MainFrame () {
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        // Initialize components that need to be created in the constructor


        String[] columnNames = {"Timestamp", "Player", "Action"};
        this.logTable = new JTable(new DefaultTableModel(columnNames, 10));
        this.playerCardsPanel = createCardsPanel();
        this.dealerCardsPanel = createCardsPanel();

        initializeGUI();

    }

    public void initializeGUI() {
        Container contentPane = getContentPane();
        ((JPanel) contentPane).setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel topPanel = createTopPanel();
        JPanel mainPanel = createMainPanel();
        JPanel logPanel = createLogPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(logPanel, BorderLayout.SOUTH);


        // Additional GUI components can be added here
    }
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        String [] columnNames = {"Timestamp", "Player", "Action"};
        logTable = new JTable(new DefaultTableModel(columnNames, 10));
        logTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(logTable);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        return logPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Game Table"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 0: Player's Hand
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Give this row vertical weight, so it can expand

        // Col 0: "Player Hand:" label (no weight, doesn't stretch)
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(playerHandLabel, gbc);

        // Col 1: Player cards panel (takes all horizontal and vertical weight)
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(playerCardsPanel, gbc);

        // Col 2: Player hand value (no weight, doesn't stretch)
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(playerHandValue, gbc);

        // --- Row 1: Dealer's Hand ---
        gbc.gridy = 1;
        // weighty is already 1.0 from the previous row setting

        // Col 0: "Dealer Hand:" label
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(dealerHandLabel, gbc);

        // Col 1: Dealer cards panel
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(dealerCardsPanel, gbc);

        // Col 2: Dealer hand value
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(dealerHandValue, gbc);

        return mainPanel;
//        playerHandValue = new JLabel("0");
//        dealerHandValue = new JLabel("1");
//        dealerCardsPanel = createCardsPanel();
//        playerCardsPanel = createCardsPanel();
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        // label for player hand
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 0; // Allow horizontal stretching
//        // gbc.weighty = 0.0; // Don't give extra vertical space to the header
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(playerHandLabel, gbc);
//        // label for dealer hand
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.weightx = 0; // Allow horizontal stretching
//        //gbc.weighty = 0.0; // Don't give extra vertical space to the header
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(dealerHandLabel, gbc);
//        // player cards panel
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.weightx = 1.0; // Allow horizontal stretching
//        gbc.weighty = 1.0; // extra vertical space to the header
//        gbc.fill = GridBagConstraints.BOTH;
//        mainPanel.add(playerCardsPanel, gbc);
//        // dealer cards panel
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        gbc.weightx = 1.0; // Allow horizontal stretching
//        gbc.weighty = 1.0; // extra vertical space to the header
//        gbc.fill = GridBagConstraints.BOTH;
//        mainPanel.add(dealerCardsPanel, gbc);
//        // player hand value label
//        gbc.gridx = 2;
//        gbc.gridy = 0;
//        gbc.weightx = 0; // Allow horizontal stretching
//        //gbc.weighty = 1.0; // extra vertical space to the header
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(playerHandValueLabel, gbc);
//        // dealer hand value label
//        gbc.gridx = 2;
//        gbc.gridy = 1;
//        gbc.weightx = 1.0; // Allow horizontal stretching
//        //gbc.weighty = 1.0; // extra vertical space to the header
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(dealerHandValueLabel, gbc);
//        // player hand value
//        gbc.gridx = 3;
//        gbc.gridy = 0;
//        gbc.weightx = 0; // Allow horizontal stretching
//        //gbc.weighty = 1.0;
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(playerHandValue, gbc);
//        // dealer hand value
//        gbc.gridx = 3;
//        gbc.gridy = 1;
//        gbc.weightx = 0; // Allow horizontal stretching
//        //gbc.weighty = 1.0;
//        gbc.fill = GridBagConstraints.NONE;
//        mainPanel.add(dealerHandValue, gbc);
//        return mainPanel;
    }
    private JPanel createCardsPanel() {
        JPanel cardsPanel = new JPanel(new FlowLayout());
        cardsPanel.setBackground(Color.BLUE);
        return cardsPanel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start Game");
        JButton saveButton = new JButton("Save Game");
        JButton loadButton = new JButton("Load Game");
        JPanel balancePanel = new JPanel(new FlowLayout());
        JLabel playerBalanceLabel = new JLabel("Current Balance: 0.00 €");
        playerBalance = new JLabel("10.00 €");
        balancePanel.add(playerBalanceLabel);
        balancePanel.add(playerBalance);
        topPanel.add(startButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(balancePanel);
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
