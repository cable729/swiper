package com.calebjares.swiper;

import com.calebjares.swiper.logic.BulkCardProvider;
import com.calebjares.swiper.logic.CardStackFullMaintainer;
import com.calebjares.swiper.logic.ServeItCardProvider;
import com.calebjares.swiper.rest.ServeItRestWrapper;
import com.calebjares.swiper.ui.SwipeFrameFragment_;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = {
                SwipeFrameFragment_.class,

                BulkCardProvider.class,
                CardStackFullMaintainer.class,
                ServeItRestWrapper.class
        }
)
public class SwiperModule {
    @Provides BulkCardProvider providesCardProvider(ServeItRestWrapper api) {
        return new ServeItCardProvider(api);
    }
    @Provides ServeItRestWrapper providesServeitRestWrapper() {
        String endpoint = "http://swipeit.me/api/v1";
        if (BuildConfig.DEBUG) {
            endpoint = "http://192.168.56.1:3000/api/v1";
        }
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build()
                .create(ServeItRestWrapper.class);
    }
}
