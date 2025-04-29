package de.esg.java.ausbildung.honl.game;

public enum Suit {
	
    HEARTS("\u2665", "\u001b[31m"), DIAMONDS("\u2666", "\u001b[31m"), SPADES("\u2660", "\u001b[30m"), CLUBS("\u2663", "\u001b[30m");
	
	private final String icon;
	private final String iconColor;
	
	private Suit (String symbol, String iconColor) {
		this.icon = symbol;
		this.iconColor = iconColor;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconColor() {
		return iconColor;
	}
	
}