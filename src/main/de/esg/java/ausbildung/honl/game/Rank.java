package de.esg.java.ausbildung.honl.game;

public enum Rank {
    TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
    JACK(10, "J"), QUEEN(10, "Q"), KING(10, "K"), ACE(11, "A")//, SOFT_ACE(1, "A");
;
    private final int cardValue;
    private final String label;

    private Rank(int cardValue, String label) {
        this.cardValue = cardValue;
        this.label = label;
    }

    public int getCardValue(){
        return cardValue;
    }
    
    public String getLabel () {
    	return label;
    }
}