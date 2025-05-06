package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleView implements GameView {

private final Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
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
        String name = "Player";
        while (!validInput) {
            name = scanner.nextLine();
            System.out.println("Are you happy with the name " + name + "? (y/n)");
            if (promptYesNo("")) {
                validInput = true;
            }
        }
        return name;
    }

    /**
     *
     */
    @Override
    public void displayBuyIn() {
        System.out.println("Buy in of " + Constants.BUY_IN + " € will be charged to your balance.");
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
        BigDecimal bet = BigDecimal.ZERO;
        System.out.println("Please specify amount you wish to bet (Maximum bet: " + maxBet + "€).");
        while (scanner.hasNext()) {
            String input = scanner.next();
            try {
                bet = new BigDecimal(input);
                if (bet.compareTo(maxBet) > 0) {
                    System.out.println("Amount too high, continuing with maximum bet");
                    bet = maxBet;
                }
                return bet;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input! Please enter only the amount in the following format: x.xx");
            }
        }
        return bet;
    }

    /**
     * @param playerHand
     */
    @Override
    public void showPlayerHand(Hand playerHand) {
        StringBuilder sb = new StringBuilder();
        for (Card card : playerHand.getCards()) {
            sb.append(card.consoleString());
        }
        sb.append("Hand value: ").append(playerHand.getHandValue());
        System.out.println(sb);
    }

    /**
     * @param dealerHand
     * @param hideFirstCard
     */
    @Override
    public void showDealerHand(Hand dealerHand, boolean hideFirstCard) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dealerHand.getCards().size(); i++) {
            if (hideFirstCard && i == 0) {
                sb.append("???" + "\t");
            } else {
                sb.append(dealerHand.getCards().get(i).consoleString());
            }
            i++;
        }
        if (!hideFirstCard) {
        sb.append("Hand value: ").append(dealerHand.getHandValue());
        }
        System.out.println(sb);
    }

    /**
     * @return boolean whether player wants to hit or stand
     */
    @Override
    public boolean promptPlayerAction() { // possibly redundant, use promptYesNo instead to ask for hit/stand
        return false;
    }

    /**
     * @param message
     */
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean promptPlayAgain() {
        return promptYesNo("Do you want to play again?");
    }

    /**
     * @param message
     * @return
     */
    @Override
    public boolean promptYesNo(String message) {
        if(!message.isBlank()) {
            System.out.println(message + " (y/n): ");
        }
        while (scanner.hasNext()) {
            String input = scanner.next().substring(0, 1).toUpperCase();
            if (!input.equals("Y") && !input.equals("N")) {
                System.out.println("Invalid choice! Please enter yes or no.");
            }
            if (input.equals("Y") || input.equals("N")) {
                return input.equals("Y");
            }
        }
        return false;
    }

    public void showCardDrawn (Card card, String playerName) {
        System.out.printf("%s drew %s", playerName, card.consoleString());
    }

    public void closeScanner() {
        scanner.close();
    }
}