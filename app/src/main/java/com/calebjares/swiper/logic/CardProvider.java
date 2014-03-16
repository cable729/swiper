package com.calebjares.swiper.logic;

import com.calebjares.swiper.model.Card;

public interface CardProvider {
    public Card getNext();
    boolean hasNext();
}
