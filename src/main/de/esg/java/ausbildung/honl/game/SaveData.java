package de.esg.java.ausbildung.honl.game;

import java.math.BigDecimal;
import java.util.ArrayList;

public record SaveData(String playerName, BigDecimal balance, ArrayList<Card> cardStack) {
}
