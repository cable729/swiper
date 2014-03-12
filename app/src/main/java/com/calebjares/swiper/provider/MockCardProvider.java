package com.calebjares.swiper.provider;

import android.annotation.TargetApi;
import android.os.Build;

import com.calebjares.swiper.model.Card;

import java.util.ArrayDeque;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MockCardProvider implements CardProvider {
    ArrayDeque<Card> cards = new ArrayDeque<Card>(){{
        add(new Card("Would you like to go fishing?"));
        add(new Card("Would you like to go skydiving?"));
        add(new Card("Would you like to travel"));
        add(new Card("Would you like to go to Paris?"));
        add(new Card("Want to know where Arbor Day was started?"));
    }};

    @Override
    public Card getNext() {
        Card c = cards.getFirst();
        cards.addLast(c);
        return c;
    }
}
