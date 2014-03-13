package com.calebjares.swiper.provider;

import com.calebjares.swiper.model.Card;

import java.util.ArrayList;
import java.util.Random;

public class MockCardProvider implements CardProvider {
    ArrayList<Card> cards = new ArrayList<Card>(){{
        add(new Card("Would you like to go fishing?"));
        add(new Card("Would you like to go skydiving?"));
        add(new Card("Would you like to travel"));
        add(new Card("Would you like to go to Paris?"));
        add(new Card("Want to know where Arbor Day was started?"));
    }};
    ArrayList<Card> usedCards = new ArrayList<Card>();

    static Random random = new Random();

    @Override
    public Card getNext() {
        Card c = cards.get(random.nextInt(cards.size()));
        usedCards.add(c);
        cards.remove(c);
        return c;
    }

    @Override public boolean hasNext() {
        return cards.size() > 0;
    }
}
