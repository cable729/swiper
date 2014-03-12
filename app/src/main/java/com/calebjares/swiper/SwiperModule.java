package com.calebjares.swiper;

import com.calebjares.swiper.provider.CardProvider;
import com.calebjares.swiper.provider.MockCardProvider;
import com.calebjares.swiper.ui.SwipeFrameFragment_;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                SwipeFrameFragment_.class,

                CardProvider.class
        }
)
public class SwiperModule {
    @Provides CardProvider providesCardProvider() {
        return new MockCardProvider();
    }
}
