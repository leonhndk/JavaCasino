package de.esg.java.ausbildung.honl.game;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Run the GUI creation and game logic on the Event Dispatch Thread
        Suit suit = Suit.HEARTS; // Example usage of Suit enum
        System.out.println(suit.name());
        System.out.println(suit.getName());
        SwingUtilities.invokeLater(() -> {
            MainFrame view = new MainFrame();
            GameEngine gameEngine = new GameEngine(view);

            // Now that the view is created, make it visible
            view.setVisible(true);

            // Start the game
            // Note: Long-running tasks like playGame() can freeze the GUI.
            // You might later want to run the game loop in a separate thread.
            gameEngine.playGame();
        });
    }
}