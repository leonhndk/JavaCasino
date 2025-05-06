package de.esg.java.ausbildung.honl.game;

public class Dealer extends AbstractPlayer {

    @Override
    public void drawCard(Deck deck) {
        if (hand.getHandValue() < 17) {
            // Dealer must hit if hand value is less than 17
            hand.addCard(deck.drawCard());
//        } else {
//            // Dealer stands if hand value is 17 or more
//            System.out.println("Dealer forced to stand with hand value: " + hand.getHandValue());
        }
    }
}
