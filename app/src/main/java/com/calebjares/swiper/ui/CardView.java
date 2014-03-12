package com.calebjares.swiper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calebjares.swiper.R;
import com.calebjares.swiper.model.Card;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_card)
public class CardView extends RelativeLayout {
    @ViewById(R.id.card_text) TextView text;
    private float lastX;
    private float lastY;
    private int originalLeftMargin;
    private int originalTopMargin;
    private boolean hasSetOriginalMargins;

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
        RelativeLayout.LayoutParams params = (LayoutParams) this.getLayoutParams();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getRawX();
                lastY = event.getRawY();
                if (!hasSetOriginalMargins) {
                    originalLeftMargin = params.leftMargin;
                    originalTopMargin = params.topMargin;
                    hasSetOriginalMargins = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float dx = event.getRawX() - lastX;
                float dy = event.getRawY() - lastY;
                params.leftMargin += dx;
                params.topMargin += dy;
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                params.leftMargin = originalLeftMargin;
                params.topMargin = originalTopMargin;
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                params.leftMargin = originalLeftMargin;
                params.topMargin = originalTopMargin;
                break;
            }
        }
        this.setLayoutParams(params);
        return true;
    }
}
