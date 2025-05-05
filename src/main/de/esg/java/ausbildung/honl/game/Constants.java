package de.esg.java.ausbildung.honl.game;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {


	static final String FILE_NAME = "Kartenspiel";
	static final String USER_HOME = System.getProperty("user.home");
	static final String DIRECTORY = USER_HOME + File.separator + "JavaJack";
	//static final String FILE_PATH_SAFE = SaveUtils.createAndGetDirectory(Paths.get(DIRECTORY)) + File.separator + FILE_NAME;
	static final Path filePath = Paths.get(DIRECTORY, Constants.FILE_NAME);
	static final String PLAYER_SCORE = "Player score:";
	static final String DEALER_SCORE = "Dealer score:";
	static final String WELCOME_MSG = "Welcome to the Blackjack Table at ESG-Casino!";
	static final Double MAX_BET = 2.0;
	static final double BUY_IN = 0.50;
	static final String RESHUFFLE_MSG = "Deck depleted, reshuffling...";
	static final String INSUFFICIENT_FUNDS_MSG = "Insufficient funds to process payment.";
}