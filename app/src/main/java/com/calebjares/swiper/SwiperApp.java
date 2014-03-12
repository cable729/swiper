package com.calebjares.swiper;

import android.app.Application;

import dagger.ObjectGraph;

public class SwiperApp extends Application {
    private static ObjectGraph graph;

    public static ObjectGraph getGraph() {
        return graph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new SwiperModule());
    }
}
