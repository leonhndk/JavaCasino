package de.esg.java.ausbildung.honl.game;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveUtils {
	public static boolean gameSaveExists() {
		return Files.exists(Constants.filePath);
	}

	public static SaveData loadSavedGame(Path filePath) {
		ArrayList<String> savedGame = readSaveFile(filePath);
		ArrayList<Card> cardStack;
		// check file before trying to parse cards and balance, savedGame may be null

        if (!validateSaveData(savedGame)) {
			// handle logging here or in gameView instance?
			return null;
		}
		BigDecimal balance = readBalance(savedGame.get(0));
		cardStack = loadCardStack(savedGame);
		if (balance == null || cardStack.isEmpty()) {
			// handle logging here or in gameView instance?
			return null;
		}
		return new SaveData(balance, cardStack);
	}
	
	private static BigDecimal readBalance (String line) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d{2}");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			try {
				return new BigDecimal(matcher.group());
			} catch (NumberFormatException e) {
				// logging ?
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * load last saved card stack
	 *
	 * @return ArrayList
	 */
	private static ArrayList<Card> loadCardStack(ArrayList<String> savedGame) {
		ArrayList<Card> cardStack = new ArrayList<>();
			for (int i = 3; i < savedGame.size(); i++) {
				Card card = readCard(savedGame.get(i));
				if (card != null) {
					cardStack.add(card);
				}
			}
			return cardStack;
	}

	/**
	 * @param card representation of card from save file read in loadCardStack
	 *               method
	 * @return card object
	 */
	private static Card readCard(String card) {
		// split card card along whitespace e.g. THREE | of | CLUBS
		String[] arr = card.split("\\s");
		if (arr.length != 3) {
			System.out.println("Error! Save file corrupted");
			return null;
		}
		String rankStr = arr[0];
		String suitStr = arr[2];
		Rank rankComp = null;
		Suit suitComp = null;
		for (Rank rank : Rank.values()) {
			if (rankStr.equals(rank.name())) {
				rankComp = rank;
			}
		}
		for (Suit suit : Suit.values()) {
			if (suitStr.equals(suit.name())) {
				suitComp = suit;
			}
		}
		if (suitComp != null && rankComp != null) {
			return new Card(rankComp, suitComp);
		}
		return null;
	}

	/**
	 * check for uncorrupted save game file
	 *
	 * @param  gameSave read from the file
	 * @return boolean
	 */
	private static boolean validateSaveData(ArrayList<String> gameSave) {
		boolean balanceMatches = false;
		boolean stackMatches = true;
		// match pattern for saved balance
		if (gameSave.get(0).matches(Constants.BALANCE_REGEX)) {
			balanceMatches = true;
		}
		// match pattern for saved card stack
		for (int i = 1; i < gameSave.size(); i++) {
			if (!gameSave.get(i).matches(Constants.CARD_REGEX)) {
				// logging ?
				stackMatches = false;
				break;
			}
		}
		return balanceMatches && stackMatches;
	}

	private static ArrayList<String> readSaveFile (Path filePath) {
		ArrayList<String> gameSave = new ArrayList<>();
		String line;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
			while ((line = reader.readLine()) != null) {
				gameSave.add(line);
			}
		} catch (IOException e) {
			// logging ?
			e.printStackTrace();
			return null;
		}
		return gameSave;
	}


	private static ArrayList<String> createSaveData (BigDecimal playerBalance, String playerName, Deck deck) {
		ArrayList<String> gameSave = new ArrayList<>();
		ArrayList<Card> cards = deck.getCards();
		gameSave.add(0, playerName + "'s balance: " + playerBalance + " €");
		for (Card card : cards) {
			gameSave.add(card.toString());
		}
		return gameSave;
	}
	private static boolean writeSaveData (ArrayList<String> saveData, String fileName) {
		try (FileWriter writer = new FileWriter(fileName)) {
			for (String line : saveData) {
				writer.write(line + "\n");
			}
			writer.close();
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

//	/**
//	 * create directory under user files to write save data to while checking
//	 * existence thereof first
//	 *
//	 * @param dirPath
//	 *
//	 * @return String
//	 */
	public static String createDirectory(Path directoryPath) {
		if (Files.exists(directoryPath)) {
			// logging to be added later?
			return directoryPath.toString();
		}
		// create directory if it does not exist
		try {
			Files.createDirectories(directoryPath);
			return directoryPath.toString();
		} catch (IOException e) {
			// logging ?
			e.printStackTrace();
			return null;
		}
	}

	public static boolean saveGame(Deck deck, String playerName, BigDecimal balance) {
		Path path = Paths.get(System.getProperty("user.home") + File.separator + "JavaJack");
		ArrayList<String> saveData = createSaveData(balance, playerName, deck);
		String filePath = createDirectory(path) + File.separator + Constants.FILE_NAME;
		return writeSaveData(saveData, filePath);
	}
}