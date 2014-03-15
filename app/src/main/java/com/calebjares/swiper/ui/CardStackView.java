package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.util.ScreenMetricUtil;

import java.util.Stack;

public class CardStackView extends RelativeLayout {
    CardView.CardEventListener listener;
    Stack<View> viewStack = new Stack<View>();

    public static final int CARD_SIZE = 300;

    public CardStackView(Context context) {
        super(context);
    }

    public CardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardStackView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void pushCard(Card card) {
        insertCard(card, 0);
    }

    /**
     * @param index the index, where 0 is the top of the stack
     */
    public void insertCard(Card card, int index) {
        CardView cardView = CardView_.build(getContext(), card);
        cardView.setCardEventListener(getListener());

        int size = ScreenMetricUtil.convertPixelToDp(getContext(), CARD_SIZE);
        LayoutParams params = new LayoutParams(size, size);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        cardView.setLayoutParams(params);

        viewStack.insertElementAt(cardView, viewStack.size() - index);
        this.addView(cardView);
        // fix the z-index of the cards
        for (int i = viewStack.size() - index; i < viewStack.size(); i++) {
            viewStack.get(i).bringToFront();
        }
    }

    public void popCard() {
        View pop = viewStack.pop();
        this.removeView(pop);
    }

    public CardView.CardEventListener getListener() {
        return listener;
    }

    public void setListener(CardView.CardEventListener listener) {
        this.listener = listener;
    }
}
