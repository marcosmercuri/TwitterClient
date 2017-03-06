package course.com.twitterclient.images.di;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import course.com.twitterclient.api.CustomTwitterApiClient;
import course.com.twitterclient.entities.Image;
import course.com.twitterclient.images.ImagesInteractor;
import course.com.twitterclient.images.ImagesInteractorImpl;
import course.com.twitterclient.images.ImagesPresenter;
import course.com.twitterclient.images.ImagesPresenterImpl;
import course.com.twitterclient.images.ImagesRepository;
import course.com.twitterclient.images.ImagesRepositoryImpl;
import course.com.twitterclient.images.ui.ImagesView;
import course.com.twitterclient.images.ui.adapters.ImagesAdapter;
import course.com.twitterclient.images.ui.adapters.OnItemClickListener;
import course.com.twitterclient.lib.base.EventBus;
import course.com.twitterclient.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class ImagesModule {
    private ImagesView view;
    private OnItemClickListener listener;

    public ImagesModule(ImagesView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    OnItemClickListener provideOnItemClickListener() {
        return this.listener;
    }

    @Provides
    @Singleton
    List<Image> provideImages() {
        return new ArrayList<>();
    }

    @Provides
    ImagesAdapter provideAdapter(List<Image> items, OnItemClickListener clickListener, ImageLoader imageLoader) {
        return new ImagesAdapter(items, imageLoader, clickListener);
    }

    @Provides
    @Singleton
    ImagesView provideImagesView() {
        return this.view;
    }

    @Provides
    @Singleton
    ImagesPresenter provideImagesPresenter(ImagesView view, ImagesInteractor interactor, EventBus eventBus) {
        return new ImagesPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    ImagesInteractor provideImagesInteractor(ImagesRepository repository) {
        return new ImagesInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ImagesRepository provideImagesRepository(CustomTwitterApiClient client, EventBus eventBus) {
        return new ImagesRepositoryImpl(eventBus, client);
    }

    @Provides
    @Singleton
    CustomTwitterApiClient provideTwitterApiClient(TwitterSession session) {
        return new CustomTwitterApiClient(session);
    }

    @Provides
    @Singleton
    TwitterSession provideTwitterSession() {
        return Twitter.getSessionManager().getActiveSession();
    }
}
