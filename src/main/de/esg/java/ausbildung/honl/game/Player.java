package de.esg.java.ausbildung.honl.game;

public class Player extends AbstractPlayer {

    private float balance;
    private float bet;
    private final GameView gameView;

    public Player(float startingBalance, GameView gameView) {
        this.balance = startingBalance;
        this.gameView = gameView;
    }

    public void placeBet(float bet) {
        if (bet > balance) {
            System.out.println("Insufficient balance to place this bet.");
        } else {
            this.bet = bet;
            balance -= bet;
            System.out.printf("Bet placed: %.2f. Remaining balance: %.2f\n", bet, balance);
        }
    }

    public void chargeToBalance(float amount) {
        if (amount < 0) {
            System.out.println("Cannot charge a negative amount.");
        } else {
            balance -= amount;
            System.out.printf("Charged to balance: %.2f. New balance: %.2f\n", amount, balance);
        }
    }

    @Override
    public void takeTurn(Deck deck, GameEngine gameEngine) {
        System.out.printf("Your turn! Current hand value: %d\n", hand.getHandValue());
    }
}
