package course.com.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import course.com.twitterclient.main.ui.MainActivity;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.twitterLoginButton)
    TwitterLoginButton twitterLoginButton;
    @Bind(R.id.activity_login_container)
    RelativeLayout activityLoginContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Twitter.getSessionManager().getActiveSession()!=null) {
            // already logged in
            navigateToMainScreen();
        } else {
            twitterLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    navigateToMainScreen();
                }

                @Override
                public void failure(TwitterException e) {
                    String error = String.format(getString(R.string.login_error_message), e.getLocalizedMessage());
                    Snackbar.make(activityLoginContainer, error, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
