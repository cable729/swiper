package com.calebjares.swiper.ui;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class BaseFragment extends SherlockFragment {
    protected MainActivity getBaseActivity() {
        return (MainActivity) this.getActivity();
    }
}
