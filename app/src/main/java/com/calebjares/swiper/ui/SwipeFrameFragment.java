package com.calebjares.swiper.ui;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.provider.CardProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_swipeframe)
public class SwipeFrameFragment extends BaseFragment implements CardView.CardEventListener {
    @Inject CardProvider cards;
    @ViewById(R.id.swipeframe_container) CardStackView cardStackView;

    @AfterViews
    void setup() {
        cardStackView.setListener(this);
        addCard();
    }

    private boolean addCard() {
        if (cards.hasNext()) {
            cardStackView.pushCard(cards.getNext());
            return true;
        }
        return false;
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onYes(Card card) {
        addCard();
    }

    @Override public void onNo(Card card) {
        addCard();
    }
}
