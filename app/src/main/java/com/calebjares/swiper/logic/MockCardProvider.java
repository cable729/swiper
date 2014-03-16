package com.calebjares.swiper.logic;

import com.calebjares.swiper.model.Card;

import java.util.ArrayList;
import java.util.Random;

public class MockCardProvider implements CardProvider {
    ArrayList<Card> cards = new ArrayList<Card>(){{
        add(new Card("1"));
        add(new Card("2"));
        add(new Card("3"));
        add(new Card("4"));
        add(new Card("5"));
        add(new Card("6"));
        add(new Card("7"));
        add(new Card("8"));
        add(new Card("9"));
        add(new Card("10"));
        add(new Card("11"));
        add(new Card("12"));
        add(new Card("13"));
        add(new Card("14"));
        add(new Card("15"));
//        add(new Card("Would you like to go fishing?"));
//        add(new Card("Would you like to go skydiving?"));
//        add(new Card("Would you like to travel"));
//        add(new Card("Would you like to go to Paris?"));
//        add(new Card("Want to know where Arbor Day was started?"));
//        add(new Card("Would you like to see a penguin walk on the moon?"));
//        add(new Card("Do lemurs scare you?"));
//        add(new Card("Are you afraid of the dark?"));
//        add(new Card("Do you want to see a magic trick?"));
//        add(new Card("Wouldn't you love to be in a hot tub right now?"));
//        add(new Card("Ever want to go to Vegas?"));
//        add(new Card("Do you take a vacation every year?"));
//        add(new Card("Do you like cheesy pizza?"));
//        add(new Card("Did you see Disney's Frozen?"));
    }};
    ArrayList<Card> usedCards = new ArrayList<Card>();

    static Random random = new Random();

    @Override
    public Card getNext() {
        Card c = cards.get(0);//random.nextInt(cards.size()));
        usedCards.add(c);
        cards.remove(c);
        return c;
    }

    @Override public boolean hasNext() {
        return cards.size() > 0;
    }
}
