package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_card)
public class CardView extends RelativeLayout {
    @ViewById(R.id.card_text) TextView text;
    private float clickX;
    private float clickY;
    private float halfVerticalSreenDistance;
    private float barrierWidth = 60;
    private DisplayMetrics windowSize = new DisplayMetrics();

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

        float width = (float) windowSize.widthPixels;
        float height = (float) windowSize.heightPixels;
        halfVerticalSreenDistance = (float) (Math.sqrt(width*width + height*height) / 2);
    }

    public void setCard(Card card) {
        text.setText(card.text);
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
                            .alpha(alpha)
                            .start();
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
        if (event.getRawX() <= barrierWidth) {
            setEnabled(false);
            ViewPropertyAnimator.animate(this)
                    .setDuration(200)
                    .alpha(0)
                    .start();
            // alert that we just had a no
        } else if (event.getRawX() >= (float) windowSize.widthPixels - barrierWidth) {
            setEnabled(false);
            ViewPropertyAnimator.animate(this)
                    .setDuration(200)
                    .alpha(0)
                    .start();
            // alert that we just had a yes
        } else {
            ViewPropertyAnimator.animate(this)
                    .setDuration(50)
                    .translationX(0)
                    .translationY(0)
                    .alpha(1)
                    .start();
        }
    }
}
