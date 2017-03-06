package course.com.twitterclient.lib.di;


import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;

import javax.inject.Singleton;

import course.com.twitterclient.lib.base.EventBus;
import course.com.twitterclient.lib.base.GlideImageLoader;
import course.com.twitterclient.lib.base.GreenRobotEventBus;
import course.com.twitterclient.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class LibsModule {
    private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    EventBus provideEventBust() {
        return new GreenRobotEventBus(
                org.greenrobot.eventbus.EventBus.getDefault()
        );
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(Fragment fragment) {
        return new GlideImageLoader(Glide.with(fragment));
    }

    @Provides
    @Singleton
    Fragment provideFragment() {
        return this.fragment;
    }
}
