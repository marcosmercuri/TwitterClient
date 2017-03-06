package course.com.twitterclient;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import course.com.twitterclient.images.di.DaggerImagesComponent;
import course.com.twitterclient.images.di.ImagesComponent;
import course.com.twitterclient.images.di.ImagesModule;
import course.com.twitterclient.images.ui.ImagesView;
import course.com.twitterclient.images.ui.adapters.OnItemClickListener;
import course.com.twitterclient.lib.di.LibsModule;
import io.fabric.sdk.android.Fabric;


public class TwitterClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();

    }

    private void initFabric() {
        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(twitterAuthConfig));
    }

    public ImagesComponent getImagesComponent(Fragment fragment, ImagesView imagesView, OnItemClickListener listener) {
        return DaggerImagesComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .imagesModule(new ImagesModule(imagesView, listener))
                .build();
    }
}
