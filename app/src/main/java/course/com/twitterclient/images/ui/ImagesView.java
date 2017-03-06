package course.com.twitterclient.images.ui;


import java.util.List;

import course.com.twitterclient.entities.Image;

public interface ImagesView {
    void showElements();
    void hideElements();
    void showProgress();
    void hideProgress();

    void onError(String error);
    void setContent(List<Image> items);
}
