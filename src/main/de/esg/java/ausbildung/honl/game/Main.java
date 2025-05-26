package de.esg.java.ausbildung.honl.game;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        // Uncomment the line above to use ConsoleView instead of GUI_View
       // GUI_View view = new GUI_View();
        GameEngine gameEngine = new GameEngine(view);
        gameEngine.playGame();
    }
}
