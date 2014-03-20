package com.calebjares.swiper.logic;

import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.rest.ApiCard;
import com.calebjares.swiper.rest.ServeItRestWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ServeItCardProvider implements BulkCardProvider {

    private final ServeItRestWrapper api;

    @Inject public ServeItCardProvider(ServeItRestWrapper api) {
        this.api = api;
    }

    @Override public List<Card> getCards(int amount) {
        List<Card> cards = new ArrayList<Card>();
        for (ApiCard c : api.getCards(amount)) {
            cards.add(new Card(c.description));
        }
        return cards;
    }
}
