package com.calebjares.swiper.util;

import android.content.Context;

public final class ScreenMetricUtil {
    private ScreenMetricUtil() {}

    // Source: http://developer.android.com/guide/practices/screens_support.html#dips-pels
    public static int convertPixelToDp(Context context, int pixels) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * density + 0.5f);
    }
}
