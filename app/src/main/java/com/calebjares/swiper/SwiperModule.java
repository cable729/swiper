package com.calebjares.swiper;

import com.calebjares.swiper.logic.CardProvider;
import com.calebjares.swiper.logic.CardStackFullMaintainer;
import com.calebjares.swiper.logic.MockCardProvider;
import com.calebjares.swiper.ui.SwipeFrameFragment_;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                SwipeFrameFragment_.class,

                CardProvider.class,
                CardStackFullMaintainer.class
        }
)
public class SwiperModule {
    @Provides CardStackFullMaintainer.CardStackMaintainerParams providesCardStackMaintainerParams() {
        return new CardStackFullMaintainer.CardStackMaintainerParams(5, 3);
    }
    @Provides CardProvider providesCardProvider() {
        return new MockCardProvider();
    }
}
