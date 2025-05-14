package de.esg.java.ausbildung.honl.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
    return Collections.unmodifiableList(cards);
    }

    public Card getLastCard() {
        try {
            return cards.get(cards.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getHandValue () {
        int aces = 0;
        int sum = 0;
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
            sum += card.getCardValue();
        }
        while (aces > 0 && sum <= 21) {
            sum -= 10;
            aces--;
        }
        return sum;
    }


    public boolean isBlackjack () {
        return cards.size() == 2 && getHandValue() == 21;
    }

    public boolean isBust () {
        return getHandValue() > 21;
    }

    public void clearHand () {
        cards.clear();
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append("\t");
        }
        return sb.toString().trim();
    }

    public String consoleString (boolean hideFirstCard) {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            if(hideFirstCard)
                sb.append(card.consoleString());
        }
        sb.append("Total hand value: ").append(getHandValue());
        return sb.toString().trim();
    }
}
