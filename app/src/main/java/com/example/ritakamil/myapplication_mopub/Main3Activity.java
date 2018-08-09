package com.example.ritakamil.myapplication_mopub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubRewardedVideoListener;

import java.util.Set;

public class Main3Activity extends AppCompatActivity {

    //declarations
    private Button mShowButton;
    private MoPubRewardedVideos moPubRewardedVideos;
    private MoPubRewardedVideoListener rewardedVideoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mShowButton = (Button)findViewById(R.id.button);

        MoPubRewardedVideos.initializeRewardedVideo(this);
        MoPub.onCreate(this);

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoPubRewardedVideos.loadRewardedVideo("0b192107237d4fceaf152703aa9cef92");
                MoPubRewardedVideos.hasRewardedVideo("0b192107237d4fceaf152703aa9cef92");
                MoPubRewardedVideos.showRewardedVideo("0b192107237d4fceaf152703aa9cef92");
            }
        });

        rewardedVideoListener = new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {

            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {

            }

            @Override
            public void onRewardedVideoStarted(@NonNull String adUnitId) {

            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {

            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {

            }

            @Override
            public void onRewardedVideoClosed(@NonNull String adUnitId) {

            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {

            }
        };

    }

}