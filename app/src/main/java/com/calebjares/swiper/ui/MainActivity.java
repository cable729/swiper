package com.calebjares.swiper.ui;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.calebjares.swiper.R;
import com.calebjares.swiper.SwiperApp;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends SherlockFragmentActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Don't add to back stack
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, SwiperApp.getGraph().get(SwipeFrameFragment_.class))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            navigateUp();
            return true;
        }
        return false;
    }

    @Override public void onBackPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onBackPressed();
    }

    public void navigateUp() {
        // No up navigation now, just use back
        onBackPressed();
    }
    public void navigateBack() {
        onBackPressed();
    }

    public <T extends BaseFragment> void navigateTo(Class<T> type) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, SwiperApp.getGraph().get(type))
                .addToBackStack(null)
                .commit();
    }
}
