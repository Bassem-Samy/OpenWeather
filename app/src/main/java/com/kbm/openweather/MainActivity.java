package com.kbm.openweather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kbm.openweather.ui.HomeActivity;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    CallbackManager facebookCallbackManager;
    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeFacebookLogin();
    }

    private void initializeFacebookLogin() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            HomeActivity.start(this);
            finish();
            return;
        }
        facebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(facebookCallbackManager, mFacebookCallback);
    }

    @OnClick(R.id.btn_login_facebook)
    void onLoginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(getResources().getStringArray(R.array.facebook_permissions)));
    }

    FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e(TAG, loginResult.toString());
            HomeActivity.start(MainActivity.this);
            finish();
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "cancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, e.getMessage());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.btn_skip)
    void skip() {
        HomeActivity.start(this);
        finish();

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
