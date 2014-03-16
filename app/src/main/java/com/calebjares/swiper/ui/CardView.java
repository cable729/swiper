package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EViewGroup(R.layout.view_card)
public class CardView extends RelativeLayout {
    @ViewById(R.id.card_text) TextView text;
    @ViewById(R.id.no_textview) TextView noText;
    @ViewById(R.id.yes_textview) TextView yesText;

    private static final float BARRIER_WIDTH = 1f/5f;

    private float clickX;
    private float clickY;
    private float halfVerticalSreenDistance;
    private DisplayMetrics windowSize = new DisplayMetrics();
    private Card card;
    private ArrayList<CardEventListener> cardEventListeners = new ArrayList<CardEventListener>();
    private float absoluteBarrierWidth;
    private float screenWidth;

    public CardView(Context context, Card card) {
        super(context);
        this.card = card;
    }

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @AfterViews
    void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(windowSize);

        screenWidth = (float) windowSize.widthPixels;
        float height = (float) windowSize.heightPixels;
        halfVerticalSreenDistance = (float) (Math.sqrt(screenWidth*screenWidth + height*height) / 2);
        absoluteBarrierWidth = screenWidth * BARRIER_WIDTH;

        if (card != null) {
            this.text.setText(card.text);
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    clickX = event.getRawX();
                    clickY = event.getRawY();
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    float dx = event.getRawX() - clickX;
                    float dy = event.getRawY() - clickY;
                    float alpha = calculateAlpha(dx, dy);
                    ViewPropertyAnimator.animate(this)
                            .setDuration(0)
                            .translationX(dx)
                            .translationY(dy)
                            .start();

                    float textAlpha = dx / (screenWidth / 2 - absoluteBarrierWidth);
                    yesText.setAlpha(dx > 0 ? textAlpha : 0);
                    noText.setAlpha(dx < 0 ? -textAlpha : 0);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    handleDragEnd(event);
                    break;
                }
                case MotionEvent.ACTION_CANCEL: {
                    handleDragEnd(event);
                    break;
                }
                case MotionEvent.ACTION_OUTSIDE: {
                    handleDragEnd(event);
                    break;
                }
            }
        }
        return true;
    }

    float calculateAlpha(float dx, float dy) {
        double distance = Math.sqrt(dx * dx + dy * dy);

        return (halfVerticalSreenDistance - (float) distance) / halfVerticalSreenDistance;
    }

    void handleDragEnd(MotionEvent event) {
        if (event.getRawX() <= absoluteBarrierWidth) {
            setEnabled(false);
            ViewPropertyAnimator.animate(this)
                    .setDuration(200)
                    .translationX(-screenWidth)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            notifyCardEventListenersOfYes(card);
        } else if (event.getRawX() >= (float) windowSize.widthPixels - absoluteBarrierWidth) {
            setEnabled(false);
            ViewPropertyAnimator.animate(this)
                    .setDuration(200)
                    .translationX(screenWidth)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            notifyCardEventListenersOfYes(card);
        } else {
            noText.setAlpha(0);
            yesText.setAlpha(0);
            float dx = event.getRawX() - clickX;
            float dy = event.getRawY() - clickY;
            ViewPropertyAnimator.animate(this)
                    .setDuration(180)
                    .translationX(0)
                    .translationY(0)
                    .setInterpolator(new OvershootInterpolator())
                    .start();
        }
    }

    private void notifyCardEventListenersOfYes(Card card) {
        for (CardEventListener eventListener : cardEventListeners) {
            eventListener.onYes(card);
        }
    }

    private void notifyCardEventListenersOfNo(Card card) {
        for (CardEventListener eventListener : cardEventListeners) {
            eventListener.onNo(card);
        }
    }

    public void addCardEventListener(CardEventListener cardEventListener) {
        cardEventListeners.add(cardEventListener);
    }

    public static interface CardEventListener {
        public abstract void onYes(Card card);
        public abstract void onNo(Card card);
    }
}
