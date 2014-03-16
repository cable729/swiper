package com.calebjares.swiper.ui;

import com.calebjares.swiper.R;
import com.calebjares.swiper.logic.CardProvider;
import com.calebjares.swiper.logic.CardStackFullMaintainer;
import com.calebjares.swiper.model.Card;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_swipeframe)
public class SwipeFrameFragment extends BaseFragment implements CardView.CardEventListener {
    @Inject CardProvider cards;
    @Inject CardStackFullMaintainer maintainer;
    @ViewById(R.id.swipeframe_container) CardStackView cardStackView;

    @AfterViews
    void setup() {
        cardStackView.setCardEventListener(this);
        cardStackView.setLifetimeListener(maintainer);
        maintainer.onCardStackCountChange(cardStackView);
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onYes(Card card) {
    }

    @Override public void onNo(Card card) {
    }
}
