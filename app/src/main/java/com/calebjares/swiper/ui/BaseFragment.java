package com.calebjares.swiper.ui;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.calebjares.swiper.SwiperApp;

public abstract class BaseFragment extends SherlockFragment {
    protected MainActivity getBaseActivity() {
        return (MainActivity) this.getActivity();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        SwiperApp.getGraph().inject(this);
        super.onCreate(savedInstanceState);
    }
}
