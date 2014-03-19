package com.calebjares.swiper;

import com.calebjares.swiper.logic.CardProvider;
import com.calebjares.swiper.logic.CardStackFullMaintainer;
import com.calebjares.swiper.logic.MockCardProvider;
import com.calebjares.swiper.rest.ServeitRestWrapper;
import com.calebjares.swiper.ui.SwipeFrameFragment_;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = {
                SwipeFrameFragment_.class,

                CardProvider.class,
                CardStackFullMaintainer.class,
                ServeitRestWrapper.class
        }
)
public class SwiperModule {
    @Provides CardStackFullMaintainer.CardStackMaintainerParams providesCardStackMaintainerParams() {
        return new CardStackFullMaintainer.CardStackMaintainerParams(5, 3);
    }
    @Provides CardProvider providesCardProvider() {
        return new MockCardProvider();
    }
    @Provides ServeitRestWrapper providesServeitRestWrapper() {
        String endpoint = "http://swipeit.me/api/v1";
        if (BuildConfig.DEBUG) {
            endpoint = "http://localhost:3000/api/v1";
        }
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build()
                .create(ServeitRestWrapper.class);
    }
}
