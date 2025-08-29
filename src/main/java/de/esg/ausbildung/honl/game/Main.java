package de.esg.ausbildung.honl.game;

import de.esg.ausbildung.honl.game.gui.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // use command line arguments to determine mode
        if (args.length > 0) {
            // Run in console mode
            consoleMode();
        }
        else {
            // Run in GUI mode
            GUI_Mode();
        }
    }

    private static void consoleMode() {
        ConsoleView consoleView = new ConsoleView();
        GameEngine gameEngine = new GameEngine(consoleView);
        if (SaveUtils.gameSaveExists()) {
            if (consoleView.promptYesNo("Saved game found. Do you want to load it? (y/n) ")) {
                SaveData saveData = SaveUtils.loadSavedGame(SaveUtils.getSavePath());
                if (saveData != null) {
                    gameEngine.loadAndPlay(SaveUtils.getSavePath());
                } else {
                    gameEngine.playNewGame();
                }
            } else {
                gameEngine.playNewGame();
            }
        }
    }

    private static void GUI_Mode() {
        SwingUtilities.invokeLater(() -> {
            MainFrame view = new MainFrame();
            GameEngine gameEngine = new GameEngine(view);
            view.setGameEngine(gameEngine);
            view.setVisible(true);
        });
    }
}