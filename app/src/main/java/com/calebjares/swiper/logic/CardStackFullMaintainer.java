package com.calebjares.swiper.logic;

import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.ui.CardStackView;

import java.util.ArrayList;

import javax.inject.Inject;

public class CardStackFullMaintainer implements CardStackLifetimeListener {

    private final CardStackMaintainerParams params;
    private final CardProvider cardProvider;

    @Inject public CardStackFullMaintainer(CardStackMaintainerParams params, CardProvider cardProvider){
        this.params = params;
        this.cardProvider = cardProvider;
    }

    @Override public void onCardStackCountChange(CardStackView cardStackView) {
        if (cardStackView.getStackSize() < params.minAmount) {
            ArrayList<Card> fetchedCards = new ArrayList<Card>();
            for (int i = 0; i < params.fetchAmount && cardProvider.hasNext(); i++) {
                fetchedCards.add(cardProvider.getNext());
            }

            if (!fetchedCards.isEmpty()) {
                Card[] cards = new Card[fetchedCards.size()];
                fetchedCards.toArray(cards);

                cardStackView.pushCardsUnder(cards);
            }
        }
    }

    public static class CardStackMaintainerParams {
        private final int minAmount;
        private final int fetchAmount;

        public CardStackMaintainerParams(int fetchAmount, int minAmount) {
            this.fetchAmount = fetchAmount;
            this.minAmount = minAmount;
        }
    }
}
