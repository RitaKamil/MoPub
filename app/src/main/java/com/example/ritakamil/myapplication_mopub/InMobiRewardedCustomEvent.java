package com.example.ritakamil.myapplication_mopub;

import static com.inmobi.sdk.InMobiSdk.IM_GDPR_CONSENT_AVAILABLE;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiAdRequestStatus.StatusCode;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;
import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class InMobiRewardedCustomEvent extends CustomEventRewardedVideo
        implements InMobiInterstitial.InterstitialAdListener2 {

    private boolean mIsInMobiSdkInitialized = false;
    private InMobiInterstitial inmobiInterstitial;
    private JSONObject serverParams;
    private String accountId = "";
    private String placementId = "";
    private static final String TAG = InMobiRewardedCustomEvent.class.getSimpleName();


    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity activity, @NonNull Map<String, Object> localExtras,
            @NonNull Map<String, String> serverExtras) throws Exception {

        try {
            serverParams = new JSONObject(serverExtras);
        } catch (Exception e) {
            Log.e(TAG, "Could not parse server parameters");
            e.printStackTrace();
        }

        try {
            accountId = serverParams.getString("accountid");
            placementId = serverParams.getString("placementid");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        JSONObject gdprJson = new JSONObject();
        try {
            gdprJson.put(IM_GDPR_CONSENT_AVAILABLE, InMobiGDPR.getConsent());
            gdprJson.put("gdpr", InMobiGDPR.isGDPR());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!mIsInMobiSdkInitialized) {
            try {
                InMobiSdk.init(activity, accountId, gdprJson);
                mIsInMobiSdkInitialized = true;
            } catch (Exception e) {
                e.printStackTrace();
                mIsInMobiSdkInitialized = false;
                return false;
            }
        }

        return true;

    }


    @Override
    @NonNull
    protected String getAdNetworkId() {
        return placementId;
    }


    @Override
    @Nullable
    protected LifecycleListener getLifecycleListener() {
        return null;
    }


    @Override
    protected boolean hasVideoAvailable() {
        return inmobiInterstitial != null && inmobiInterstitial.isReady();
    }


    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras,
            @NonNull Map<String, String> serverExtras) throws Exception {

        /*
         * You may also pass the Placement ID by
         * specifying Custom Event Data in MoPub's web interface.
         */
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        inmobiInterstitial = new InMobiInterstitial(activity, Long.parseLong(getAdNetworkId()), this);

		/*
		Sample for setting up the InMobi SDK Demographic params.
        Publisher need to set the values of params as they want.

		InMobiSdk.setAreaCode("areacode");
		InMobiSdk.setEducation(Education.HIGH_SCHOOL_OR_LESS);
		InMobiSdk.setGender(Gender.MALE);
		InMobiSdk.setAge(23);
		InMobiSdk.setPostalCode("postalcode");
		InMobiSdk.setLogLevel(LogLevel.DEBUG);
		InMobiSdk.setLocationWithCityStateCountry("blore", "kar", "india");
		InMobiSdk.setLanguage("ENG");
		InMobiSdk.setInterests("dance");
		InMobiSdk.setYearOfBirth(1980);
		*/

        Map<String, String> map = new HashMap<String, String>();
        map.put("tp", "c_mopub");
        map.put("tp-ver", MoPub.SDK_VERSION);
        inmobiInterstitial.setExtras(map);
        inmobiInterstitial.load();
    }


    @Override
    protected void onInvalidate() {
    }


    @Override
    protected void showVideo() {
        if (this.hasVideoAvailable()) {
            inmobiInterstitial.show();
        } else {
            MoPubRewardedVideoManager.onRewardedVideoPlaybackError(InMobiRewardedCustomEvent.class, placementId,
                    MoPubErrorCode.VIDEO_PLAYBACK_ERROR);
        }
    }


    @Override
    public void onAdDismissed(InMobiInterstitial ad) {
        Log.v(TAG, "Ad dismissed");
        MoPubRewardedVideoManager.onRewardedVideoClosed(InMobiRewardedCustomEvent.class, placementId);
    }


    @Override
    public void onAdDisplayed(InMobiInterstitial ad) {
        Log.v(TAG, "Ad displayed");
        MoPubRewardedVideoManager.onRewardedVideoStarted(InMobiRewardedCustomEvent.class, placementId);
    }


    @Override
    public void onAdInteraction(InMobiInterstitial ad, Map<Object, Object> params) {
        Log.v(TAG, "Ad interaction");
        MoPubRewardedVideoManager.onRewardedVideoClicked(InMobiRewardedCustomEvent.class, placementId);
    }


    @Override
    public void onAdLoadFailed(InMobiInterstitial ad, InMobiAdRequestStatus status) {

        Log.v(TAG, "Ad failed to load:" + status.getStatusCode().toString());
        if (status.getStatusCode() == StatusCode.INTERNAL_ERROR) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.INTERNAL_ERROR);
        } else if (status.getStatusCode() == StatusCode.REQUEST_INVALID) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.ADAPTER_CONFIGURATION_ERROR);
        } else if (status.getStatusCode() == StatusCode.NETWORK_UNREACHABLE) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.NETWORK_INVALID_STATE);
        } else if (status.getStatusCode() == StatusCode.NO_FILL) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.NO_FILL);
        } else if (status.getStatusCode() == StatusCode.REQUEST_TIMED_OUT) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.NETWORK_TIMEOUT);
        } else if (status.getStatusCode() == StatusCode.SERVER_ERROR) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.SERVER_ERROR);
        } else {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(InMobiRewardedCustomEvent.class, getAdNetworkId(),
                    MoPubErrorCode.UNSPECIFIED);
        }
    }


    @Override
    public void onAdReceived(InMobiInterstitial ad) {
        Log.d(TAG, "InMobi Adserver responded with an Ad");
    }


    @Override
    public void onAdLoadSucceeded(InMobiInterstitial ad) {
        Log.v(TAG, "Ad load succeeded");
        if (ad != null) {
            MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(InMobiRewardedCustomEvent.class, placementId);
        }
    }


    @Override
    public void onAdRewardActionCompleted(InMobiInterstitial ad, Map<Object, Object> rewards) {
        Log.d(TAG, "InMobi Rewarded video onRewardActionCompleted.");
        if (null != rewards) {
            Iterator<Object> iterator = rewards.keySet().iterator();
            String key = "", value = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                value = rewards.get(key).toString();
                Log.d("Rewards: ", key + ":" + value);
            }
            MoPubRewardedVideoManager.onRewardedVideoCompleted(InMobiRewardedCustomEvent.class, null,
                    MoPubReward.success(key, Integer.parseInt(value)));
        }
    }


    @Override
    public void onAdDisplayFailed(InMobiInterstitial ad) {
        Log.d(TAG, "Rewarded video ad failed to display.");
    }


    @Override
    public void onAdWillDisplay(InMobiInterstitial ad) {
        Log.d(TAG, "Rewarded video ad will display.");
    }


    @Override
    public void onUserLeftApplication(InMobiInterstitial ad) {
        Log.v(TAG, "User left application");
    }

}
