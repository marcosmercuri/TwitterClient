package course.com.twitterclient.images.di;

import javax.inject.Singleton;

import course.com.twitterclient.images.ImagesPresenter;
import course.com.twitterclient.images.ui.ImagesFragment;
import course.com.twitterclient.lib.di.LibsModule;
import dagger.Component;
import dagger.Module;

@Singleton
@Component(modules = {ImagesModule.class, LibsModule.class})
public interface ImagesComponent {
    void inject(ImagesFragment imagesFragment);
    ImagesPresenter getPresenter();
}
