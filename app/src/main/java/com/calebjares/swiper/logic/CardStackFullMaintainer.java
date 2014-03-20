package com.calebjares.swiper.logic;

import android.os.AsyncTask;

import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.ui.CardStackView;

import java.util.List;

import javax.inject.Inject;

public class CardStackFullMaintainer implements CardStackLifetimeListener {

    public static final int MIN_CARDS = 3;
    public static final int FETCH_AMOUNT = 5;
    private final BulkCardProvider cardProvider;

    @Inject public CardStackFullMaintainer(BulkCardProvider cardProvider) {
        this.cardProvider = cardProvider;
    }

    @Override public void onCardStackCountChange(final CardStackView cardStackView) {
        if (cardStackView.getStackSize() < MIN_CARDS) {
            AsyncTask<Integer, Void, List<Card>> task = new AsyncTask<Integer, Void, List<Card>>() {
                @Override protected List<Card> doInBackground(Integer... ints) {
                    return cardProvider.getCards(ints[0]);
                }

                @Override protected void onPostExecute(List<Card> cards) {
                    super.onPostExecute(cards);
                    cardStackView.pushCardsUnder(cards.toArray(new Card[cards.size()]));
                }
            };
            task.execute(FETCH_AMOUNT);
        }
    }
}
