package course.com.twitterclient.images;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import course.com.twitterclient.api.CustomTwitterApiClient;
import course.com.twitterclient.entities.Image;
import course.com.twitterclient.lib.base.EventBus;

public class ImagesRepositoryImpl implements ImagesRepository {
    private static final int TWEET_COUNT = 100;
    private EventBus eventBus;
    private CustomTwitterApiClient twitterApiClient;

    public ImagesRepositoryImpl(EventBus eventBus, CustomTwitterApiClient twitterApiClient) {
        this.eventBus = eventBus;
        this.twitterApiClient = twitterApiClient;
    }

    @Override
    public void getImages() {
        Callback<List<Tweet>> callback = new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                callbackSuccess(result);
            }

            @Override
            public void failure(TwitterException e) {
                post(e.getLocalizedMessage());
            }
        };
        twitterApiClient.getTimelineService().homeTimeline(TWEET_COUNT, true, true, true, true, callback);
    }

    private void callbackSuccess(Result<List<Tweet>> result) {
        List<Image> items = new ArrayList<>();
        for (Tweet tweet : result.data) {
            if (containsImages(tweet)) {
                Image tweetModel = new Image();

                tweetModel.setId(tweet.idStr);
                tweetModel.setFavoriteCount(tweet.favoriteCount);

                String tweetText = tweet.text;
                int index = tweetText.indexOf("http");
                if (index > 0) {
                    tweetText = tweetText.substring(0, index);
                }
                tweetModel.setTweetText(tweetText);

                MediaEntity currentPhoto = tweet.entities.media.get(0);
                String imageURL = currentPhoto.mediaUrl;
                tweetModel.setImageUrl(imageURL);

                items.add(tweetModel);
            }
        }
        Collections.sort(items, new Comparator<Image>() {
            public int compare(Image t1, Image t2) {
                return t2.getFavoriteCount() - t1.getFavoriteCount();
            }
        });
        post(items);
    }

    private boolean containsImages(Tweet tweet) {
        return tweet.entities!=null &&
                tweet.entities.media != null &&
                ! tweet.entities.media.isEmpty();
    }

    private void post(String error) {
        this.post(null, error);
    }

    private void post(List<Image> images) {
        this.post(images, null);
    }

    private void post(List<Image> images, String error) {
        ImagesEvent event = new ImagesEvent();
        if (images!=null) {
            event.setImages(images);
        }
        if (error!=null) {
            event.setError(error);
        }
        eventBus.post(event);
    }
}
