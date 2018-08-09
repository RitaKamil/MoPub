package com.example.ritakamil.myapplication_mopub;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.mopub.common.Constants.UNUSED_REQUEST_CODE;
import static com.mopub.mobileads.MoPubView.BannerAdListener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.DefaultBannerAdListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;
import com.mopub.common.MediationSettings;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.common.MediationSettings;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.util.DeviceUtils;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;
import com.inmobi.sdk.InMobiSdk.LogLevel;

public class MainActivity extends AppCompatActivity {

    //@Override

    private com.mopub.mobileads.MoPubView MoPubView;

    private Button mShowButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ritakamil.myapplication_mopub.R.layout.activity_main);

        MoPubView = (MoPubView) findViewById(com.example.ritakamil.myapplication_mopub.R.id.adview);
        MoPubView.setAdUnitId("1b249f9fe5ad49d18303b991cc303b4e"); // Enter your Ad Unit ID from www.mopub.com
        MoPubView.loadAd();

        mShowButton = (Button)findViewById(R.id.button);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });
        mShowButton = (Button)findViewById(R.id.button2);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(i);
            }
        });
    };
}

