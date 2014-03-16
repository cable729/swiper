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
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EViewGroup(R.layout.view_card)
public class CardView extends RelativeLayout implements Animator.AnimatorListener {
    @ViewById(R.id.card_text) TextView text;
    @ViewById(R.id.no_textview) TextView noText;
    @ViewById(R.id.yes_textview) TextView yesText;

    private static final float BARRIER_WIDTH = 1f/5f;

    private float clickX;
    private float clickY;
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

    public Card getCard() {
        return card;
    }

    @AfterViews
    void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(windowSize);

        screenWidth = (float) windowSize.widthPixels;
        absoluteBarrierWidth = screenWidth * BARRIER_WIDTH;

        if (card != null) {
            this.text.setText(card.text);
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    noText.setVisibility(VISIBLE);
                    yesText.setVisibility(VISIBLE);
                    clickX = event.getRawX();
                    clickY = event.getRawY();
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    float dx = event.getRawX() - clickX;
                    float dy = event.getRawY() - clickY;

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

    private void animateOut(float amount) {
        ValueAnimator a = ObjectAnimator.ofFloat(this, "translationX", amount);
        a.setDuration(150);
        a.addListener(this);
        a.setInterpolator(new AccelerateInterpolator());
        a.start();
    }

    void handleDragEnd(MotionEvent event) {
        if (isEnabled()) {
            if (event.getRawX() <= absoluteBarrierWidth) {
                setEnabled(false);
                yesText.setVisibility(GONE);
                noText.setAlpha(1);
                animateOut(-screenWidth);
                notifyCardEventListenersOfNo(card);
            } else if (event.getRawX() >= (float) windowSize.widthPixels - absoluteBarrierWidth) {
                setEnabled(false);
                noText.setVisibility(GONE);
                yesText.setAlpha(1);
                animateOut(screenWidth);
                notifyCardEventListenersOfYes(card);
            } else {
                noText.setVisibility(GONE);
                yesText.setVisibility(GONE);

                ViewPropertyAnimator.animate(this)
                        .setDuration(200)
                        .translationX(0)
                        .translationY(0)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
            }
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

    @Override public void onAnimationEnd(Animator animation) {
        if (!isEnabled()) {
            for (CardEventListener eventListener : cardEventListeners) {
                eventListener.onFinish(card);
            }
        }
    }
    @Override public void onAnimationStart(Animator animation) { }
    @Override public void onAnimationCancel(Animator animation) { }
    @Override public void onAnimationRepeat(Animator animation) { }

    public static interface CardEventListener {
        public abstract void onYes(Card card);
        public abstract void onNo(Card card);
        public abstract void onFinish(Card card);
    }
}
