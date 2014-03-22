package com.calebjares.swiper;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class SwiperApp extends Application {
    private static ObjectGraph graph;
    private static Context context;

    public static ObjectGraph getGraph() {
        return graph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new SwiperModule());
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
