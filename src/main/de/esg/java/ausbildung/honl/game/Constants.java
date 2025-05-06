package de.esg.java.ausbildung.honl.game;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {


	public static final String BLACKJACK_MSG = "Blackjack!";
	static final String FILE_NAME = "Kartenspiel";
	static final String USER_HOME = System.getProperty("user.home");
	static final String DIRECTORY = USER_HOME + File.separator + "JavaJack";
	//static final String FILE_PATH_SAFE = SaveUtils.createAndGetDirectory(Paths.get(DIRECTORY)) + File.separator + FILE_NAME;
	static final Path filePath = Paths.get(DIRECTORY, Constants.FILE_NAME);
	static final String PLAYER_SCORE = "Player score:";
	static final String DEALER_SCORE = "Dealer score:";
	static final String WELCOME_MSG = "Welcome to the Blackjack Table at ESG-Casino!";
	static final BigDecimal MAX_BET = new BigDecimal("2.00");
	static final BigDecimal BUY_IN = new BigDecimal("0.50");
	static final String RESHUFFLE_MSG = "Deck depleted, reshuffling...";
	static final String INSUFFICIENT_FUNDS_MSG = "Insufficient funds to place bet.";
	static final String INITIAL_DEAL_MSG = "Dealing initial cards...";
	static final BigDecimal STARTING_BALANCE = new BigDecimal("10.00");
	static final String BUY_IN_MSG = "Buy in of " + BUY_IN + " € will be charged to your balance.";
}