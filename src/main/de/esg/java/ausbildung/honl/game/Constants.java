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
}