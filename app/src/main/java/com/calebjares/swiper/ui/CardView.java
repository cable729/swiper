package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_card)
public class CardView extends RelativeLayout {
    @ViewById(R.id.card_text) TextView text;
    private float clickX;
    private float clickY;
    private float maxDistance = 400000;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCard(Card card) {
        text.setText(card.text);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                clickX = event.getRawX();
                clickY = event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float dx = event.getRawX() - clickX;
                float dy = event.getRawY() - clickY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                float distanceSquared = dx*dx+dy*dy;
                float alpha = (maxDistance - distanceSquared) / maxDistance;
                ViewPropertyAnimator.animate(this)
                        .setDuration(0)
                        .translationX(dx)
                        .translationY(dy)
                        .alpha(alpha)
                        .start();
                break;
            }
            case MotionEvent.ACTION_UP: {
                ViewPropertyAnimator.animate(this)
                        .setDuration(50)
                        .translationX(0)
                        .translationY(0)
                        .alpha(1)
                        .start();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
        }
        return true;
    }
}
