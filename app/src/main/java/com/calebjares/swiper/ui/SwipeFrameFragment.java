package com.calebjares.swiper.ui;

import com.calebjares.swiper.R;
import com.calebjares.swiper.provider.CardProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_swipeframe)
public class SwipeFrameFragment extends BaseFragment {
    @Inject CardProvider cards;

    @ViewById(R.id.the_card) CardView theCard;

    @AfterViews
    void setup() {
        theCard.setCard(cards.getNext());
    }
}
