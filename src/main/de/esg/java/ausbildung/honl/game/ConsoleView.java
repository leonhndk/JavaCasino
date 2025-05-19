package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements GameView {

private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     *
     */
    @Override
    public void displayWelcomeMsg() {
        System.out.println(Constants.WELCOME_MSG);
    }

    /**
     * @return String playerName
     */
    @Override
    public String promptPlayerName() {
        boolean validInput = false;
        String name = "";
        System.out.println("Please enter your name:");
        while (!validInput) {
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Please re-enter your name.");
                continue;
            }
            if (promptYesNo("Are you happy with the name " + name + "?")) {
                validInput = true;
            }
            else {
                System.out.println("Please re-enter your name.");
            }
        }
        return name;
    }

    /**
     * @param balance
     */
    @Override
    public void showPlayerBalance(BigDecimal balance) {
        System.out.println("Your current balance is: " + balance + " €");
    }

    @Override
    public BigDecimal promptPlayerBet(BigDecimal maxBet) {
        BigDecimal bet = BigDecimal.ZERO;
        System.out.println("Please specify amount you wish to bet (Maximum bet: " + maxBet + "€).");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input! Please enter an amount in the format: x.xx");
                continue;
            }
            try {
                bet = new BigDecimal(input);
                if (bet.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Bet must be a positive value!");
                    continue;
                }
                if (bet.compareTo(maxBet) > 0) {
                    System.out.println("Amount too high, continuing with maximum bet of " + maxBet + " €");
                    bet = maxBet;
                }
                return bet;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input! Please enter only the amount in the format: x.xx");
            }
        }
    }

    /**
     * @param player
     */
    // make method abstract?
    public void showPlayerHand(Player player) {
        System.out.printf("%s's hand: ", player.getPlayerName());
        StringBuilder sb = new StringBuilder();
        for (Card card : player.getHand().getCards()) {
            sb.append(card.toString()).append("\t");
        }
        sb.append("Hand value: ").append(player.getHand().getHandValue());
        System.out.println(sb);
    }

    /**
     * @param dealer
     * @param hideFirstCard
     */
    @Override
    public void showDealerHand(Dealer dealer, boolean hideFirstCard) {
        StringBuilder sb = new StringBuilder();
        List<Card> cards = dealer.getHand().getCards();
        System.out.print("Dealer's hand: ");
        for (int i = 0; i < cards.size(); i++) {
            if (hideFirstCard && i == 0) {
                sb.append("???" + "\t");
            } else {
                sb.append(cards.get(i).toString()).append("\t");
            }
        }
        if (!hideFirstCard) {
        sb.append("Hand value: ").append(dealer.getHand().getHandValue());
        }
        System.out.println(sb);
    }

    /**
     * @param message
     */
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * @param message
     * @return
     */
    @Override
    public boolean promptYesNo(String message) {
        if(!message.isBlank()) {
            System.out.println(message + " (Y/N): ");
        }
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty() || (!input.startsWith("Y") && !input.startsWith("N"))) {
                System.out.println("Invalid choice! Please enter yes or no.");
                continue;
            }
            return input.startsWith("Y");
        }
    }

    public void showCardDrawn (AbstractPlayer abstractPlayer) {
        if (abstractPlayer instanceof Player) {
            System.out.printf("%s drew %s", ((Player) abstractPlayer).getPlayerName(), abstractPlayer.getHand().getLastCard().toString());
        } else if (abstractPlayer instanceof Dealer) {
            System.out.printf("%s drew %s", "Dealer", abstractPlayer.getHand().getLastCard().toString());
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}