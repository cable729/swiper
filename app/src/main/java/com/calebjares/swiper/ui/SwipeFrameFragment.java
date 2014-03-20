package com.calebjares.swiper.ui;

import android.view.View;

import com.calebjares.swiper.R;
import com.calebjares.swiper.logic.CardStackFullMaintainer;
import com.calebjares.swiper.logic.CardStackLifetimeListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_swipeframe)
public class SwipeFrameFragment extends BaseFragment implements CardStackLifetimeListener {
    @Inject CardStackFullMaintainer maintainer;
    @ViewById(R.id.swipeframe_container) CardStackView cardStackView;
    @ViewById(R.id.loading_frame) View loadingFrame;
    @ViewById(R.id.out_of_cards_frame) View outOfCardsFrame;

    private boolean loadingCards = true;

    @AfterViews
    void setup() {
        cardStackView.addLifetimeListeners(maintainer);
        cardStackView.addLifetimeListeners(this);
        maintainer.onCardStackCountChange(cardStackView);
    }

    @Override public void onCardStackCountChange(CardStackView cardStackView) {
        if (loadingCards && cardStackView.getStackSize() != 0) {
            loadingCards = false;
            loadingFrame.setVisibility(View.GONE);
        }
        else if (!loadingCards && cardStackView.getStackSize() == 0) {
            outOfCardsFrame.setVisibility(View.VISIBLE);
        }
    }
}
