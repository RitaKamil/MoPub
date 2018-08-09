package com.example.ritakamil.myapplication_mopub;

public class InMobiGDPR {
    private static String gdpr = "0";
    private static boolean consent = false;


    /**
     * Call InMobiGDPR.grantConsent() to provide GDPR consent for the user on each request basis.
     */
    public static void grantConsent() {
        consent = true;
    }


    /**
     * Call InMobiGDPR.revokeConsent() to remove GDPR consent for the user on each request basis.
     */
    public static void revokeConsent() {
        consent = false;
    }


    /**
     * Call InMobiGDPR.isGDPRApplicable() to let InMobi know if GDPR is applicable for the user.
     */
    public static void isGDPRApplicable(final boolean isGDPRApplicable) {
        if (isGDPRApplicable) {
            gdpr = "1";
        } else {
            gdpr = "0";
        }
    }


    static String isGDPR() {
        return gdpr;
    }


    static boolean getConsent() {
        return consent;
    }
}
