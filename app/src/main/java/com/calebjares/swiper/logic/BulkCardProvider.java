package com.calebjares.swiper.logic;

import com.calebjares.swiper.model.Card;

import java.util.List;

public interface BulkCardProvider {
    public List<Card> getCards(int amount);
}
