package com.example.ritakamil.myapplication_mopub;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.google.android.gms.ads.InterstitialAd;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;



public class Main2Activity extends AppCompatActivity implements InterstitialAdListener {
    private MoPubInterstitial mInterstitial;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_main2);
        mInterstitial = new MoPubInterstitial(this, "fcc1e5bbcdba4a7f959f8839306bebc4");
        mInterstitial.setInterstitialAdListener(Main2Activity.this);
        mInterstitial.load();
        mShowButton = (Button)findViewById(R.id.button);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitial.show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mInterstitial != null) {
            mInterstitial.destroy();
            mInterstitial = null;
        }
    }

    // Defined by your application, indicating that you're ready to show an interstitial ad.
    void yourAppsShowInterstitialMethod() {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        } else {
            // Caching is likely already in progress if `isReady()` is false.
            // Avoid calling `load()` here and instead rely on the callbacks as suggested below.
        }
    }

    // InterstitialAdListener methods
    @Override
    public void onInterstitialLoaded(MoPubInterstitial mInterstitial) {
        // The interstitial has been cached and is ready to be shown.
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        // The interstitial has failed to load. Inspect errorCode for additional information.
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        // The interstitial has been shown. Pause / save state accordingly.
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {}

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        // The interstitial has being dismissed. Resume / load state accordingly.
    }
}
