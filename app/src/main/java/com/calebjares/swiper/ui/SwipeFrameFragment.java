package com.calebjares.swiper.ui;

import android.widget.RelativeLayout;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.provider.CardProvider;
import com.calebjares.swiper.util.ScreenMetricUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_swipeframe)
public class SwipeFrameFragment extends BaseFragment implements CardView.CardEventListener {
    @Inject CardProvider cards;
    @ViewById(R.id.swipeframe_container) RelativeLayout container;

    @AfterViews
    void setup() {
        addCard();
    }

    private void addCard() {
        if (cards.hasNext()) {
            CardView card = CardView_.build(getActivity().getBaseContext(), cards.getNext());
            card.setCardEventListener(this);

            int size = ScreenMetricUtil.convertPixelToDp(getActivity().getBaseContext(), 300);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            card.setLayoutParams(params);

            container.addView(card);
        }
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
