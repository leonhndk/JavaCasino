package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements GameView {

private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayWelcomeMsg() {
        System.out.println(Constants.WELCOME_MSG);
    }

    public void displayBuyInMsg() {
        System.out.println(Constants.BUY_IN_MSG);
    }

    @Override
    public void displayForcedHit(Player player) {
        System.out.println(player.getPlayerName() + " is forced to hit");
    }

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

    @Override
    public void showPlayerBalance(BigDecimal balance) {
        System.out.println("Your current balance is: " + balance + " €");
    }

    @Override
    public BigDecimal promptPlayerBet(BigDecimal maxBet) {
        BigDecimal bet;
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

    @Override
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
     * Display dealer's hand
     * @param dealer instance
     * @param hideFirstCard true to hide first card on initial deal
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

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prompt user for yes/no input
     * @param message to prompt with
     * @return user choice
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

    @Override
    public boolean promptSaveGame() {
        return promptYesNo("Would you like to save your game? ");
    }

    @Override
    public void updatePlayerName(String name) {
        System.out.println("Game loaded for player: " + name);
    }

    @Override
    public void showTotalBets(BigDecimal totalBets) {}

    /**
     * Log game events with timestamp to standard error stream
     * @param actor who performed action
     * @param action description
     */
    @Override
    public void logEvent(String actor, String action) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(formatter);
        System.err.println(timestamp + actor + action);
    }

    public void closeScanner() {
        scanner.close();
    }
}