package com.calebjares.swiper.ui;

import android.widget.FrameLayout;
import android.widget.Toast;

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
    @ViewById(R.id.stack) FrameLayout stack;


    @AfterViews
    void setup() {
        addCard();
    }

    private void addCard() {
        if (cards.hasNext()) {
            CardView card = CardView_.build(getActivity().getBaseContext(), cards.getNext());
            card.setCardEventListener(this);

            stack.addView(card);
        }
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onYes(Card card) {
        Toast.makeText(getActivity().getBaseContext(), "Congratulations, we're glad!", Toast.LENGTH_SHORT).show();
        addCard();
    }

    @Override public void onNo(Card card) {
        Toast.makeText(getActivity().getBaseContext(), "No problem!", Toast.LENGTH_SHORT).show();
        addCard();
    }
}
