package de.esg.java.ausbildung.honl.game;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        GameEngine gameEngine = new GameEngine(view);
        gameEngine.playGame();
    }
}
