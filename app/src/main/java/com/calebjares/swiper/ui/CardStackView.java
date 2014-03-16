package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.calebjares.swiper.logic.CardStackLifetimeListener;
import com.calebjares.swiper.model.Card;
import com.calebjares.swiper.util.ScreenMetricUtil;

import java.util.Stack;

public class CardStackView extends RelativeLayout implements CardView.CardEventListener {
    CardView.CardEventListener cardEventListener;
    CardStackLifetimeListener lifetimeListener;
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
        pushCardsUnder(card);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        if (lifetimeListener != null) lifetimeListener.onCardStackCountChange(this);
    }

    public void pushCardsUnder(Card... cards) {
        CardView[] cardViews = new CardView[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardViews[i] = CardView_.build(getContext(), cards[i]);
            cardViews[i].addCardEventListener(getCardEventListener());
            cardViews[i].addCardEventListener(this);
            cardViews[i].setLayoutParams(getCardViewLayoutParams());
        }

        for (int i = 0; i < cards.length; i++) {
            viewStack.add(i, cardViews[cards.length - i - 1]);
            addView(cardViews[cards.length - i - 1]);
        }

        for (View view : viewStack) {
            view.bringToFront();
        }
        if (lifetimeListener != null) lifetimeListener.onCardStackCountChange(this);
    }

    private LayoutParams getCardViewLayoutParams() {
        int size = ScreenMetricUtil.convertPixelToDp(getContext(), CARD_SIZE);
        LayoutParams params = new LayoutParams(size, size);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    public void popCard() {
        View pop = viewStack.pop();
        this.removeView(pop);
        if (lifetimeListener != null) lifetimeListener.onCardStackCountChange(this);
    }

    public int getStackSize() {
        return viewStack.size();
    }

    public CardView.CardEventListener getCardEventListener() {
        return cardEventListener;
    }

    public void setCardEventListener(CardView.CardEventListener cardEventListener) {
        this.cardEventListener = cardEventListener;
    }

    public CardStackLifetimeListener getLifetimeListener() {
        return lifetimeListener;
    }

    public void setLifetimeListener(CardStackLifetimeListener lifetimeListener) {
        this.lifetimeListener = lifetimeListener;
    }

    @Override public void onYes(Card card) {
        popCard();
    }

    @Override public void onNo(Card card) {
        popCard();
    }
}
