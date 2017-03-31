//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.16
//
package com.mysampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobilehelper.auth.IdentityManager;
import com.amazonaws.mobilehelper.auth.IdentityProvider;
import com.amazonaws.mobilehelper.auth.StartupAuthErrorDetails;
import com.amazonaws.mobilehelper.auth.StartupAuthResult;
import com.amazonaws.mobilehelper.auth.StartupAuthResultHandler;
import com.amazonaws.mobilehelper.auth.signin.AuthException;
import com.amazonaws.mobilehelper.auth.signin.ProviderAuthException;

/**
 * Splash Activity is the start-up activity that appears until a delay is expired
 * or the user taps the screen.  When the splash activity starts, various app
 * initialization operations are performed.
 */
public class SplashActivity extends Activity {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();

    private final StartupAuthResultHandler authResultHandler = new StartupAuthResultHandler() {
        @Override
        public void onComplete(final StartupAuthResult authResult) {
            final IdentityManager identityManager = authResult.getIdentityManager();

            if (authResult.isUserSignedIn()) {
                final IdentityProvider provider = identityManager.getCurrentIdentityProvider();
                // If we were signed in previously with a provider indicate that to the user with a toast.
                Toast.makeText(SplashActivity.this, String.format("Signed in with %s",
                    provider.getDisplayName()), Toast.LENGTH_LONG).show();
            } else {
                // Either the user has never signed in with a provider before or refresh failed with a previously
                // signed in provider.

                // Optionally, you may want to check if refresh failed for the previously signed in provider.
                final StartupAuthErrorDetails errors = authResult.getErrorDetails();

                if (errors.didErrorOccurRefreshingProvider()) {
                    final AuthException providerAuthException = errors.getProviderRefreshException();
                    Log.w(LOG_TAG, String.format(
                        "Credentials for Previously signed-in provider %s could not be refreshed.",
                        providerAuthException.getProvider().getDisplayName()), providerAuthException);
                }

                doMandatorySignIn(identityManager);
                return;
            }

            goMain(SplashActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
        final IdentityManager identityManager = AWSMobileClient.defaultMobileClient().getIdentityManager();

        identityManager.doStartupAuth(this, authResultHandler, 2000);

    }

    private void doMandatorySignIn(final IdentityManager identityManager) {
        identityManager.signInOrSignUp(SplashActivity.this, new SignInHandler());
        SplashActivity.this.finish();
    }

    /** Go to the main activity. */
    private void goMain(final Activity callingActivity) {
        callingActivity.startActivity(new Intent(callingActivity, MainActivity.class)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        callingActivity.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch event bypasses waiting for the splash timeout to expire.
        AWSMobileClient.defaultMobileClient()
            .getIdentityManager()
            .expireSignInTimeout();
        return true;
    }
}
