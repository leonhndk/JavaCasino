package de.esg.java.ausbildung.honl.game;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {


	public static final String BLACKJACK_MSG = "Blackjack!";
	public static final String FILE_NAME = "Kartenspiel";
	public static final String BALANCE_REGEX = "^Balance: \\d+\\.\\d{2} €";
	public static final String CARD_REGEX = "[A-Z]{3,5} of [A-Z]{5,8}";
	public static final String WELCOME_MSG = "Welcome to the Blackjack Table at ESG-Casino!";
	public static final BigDecimal MAX_BET = new BigDecimal("2.00");
	public static final BigDecimal BUY_IN = new BigDecimal("0.50");
	public static final String RESHUFFLE_MSG = "Deck depleted, reshuffling...";
	public static final String PLAY_AGAIN_MSG = "Do you want to play again?";
	public static final String INSUFFICIENT_FUNDS_MSG = "Insufficient funds to place bet.";
	public static final String INITIAL_DEAL_MSG = "Dealing initial cards...";
	public static final BigDecimal STARTING_BALANCE = new BigDecimal("10.00");
	public static final String BUY_IN_MSG = "Buy in of " + BUY_IN + " € will be charged to your balance.";
	public static final Color CASINO_GREEN = new Color(0x2d543d);
	public static final Color CASINO_RED = new Color(0x952d28);
	public static final Color CASINO_GOLD = new Color(0xD4AF37);
	public static final String DRAW_CARD_MSG = "Draws a card";
	public static final String WIN_ROUND_MSG = "Win round";
}