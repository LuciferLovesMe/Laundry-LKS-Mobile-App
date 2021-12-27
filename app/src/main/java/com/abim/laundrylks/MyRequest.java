package com.abim.laundrylks;

public class MyRequest {
    private static final String baseUrl = "http://192.168.1.37:8282/";
    private static final String customerUrl = "api/customer";
    private static final String packageUrl = "api/package";
    private static final String loginUrl = "api/employee";
    private static final String checkoutUrl = "api/post";
    private static final String notifUrl = "api/notif/";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getCustomerUrl() {
        return getBaseUrl() + customerUrl;
    }

    public static String getPackageUrl() {
        return getBaseUrl() + packageUrl;
    }

    public static String getLoginUrl() {
        return getBaseUrl() + loginUrl;
    }

    public static String getCheckoutUrl() {
        return getBaseUrl() + checkoutUrl;
    }

    public static String getNotifUrl() {
        return getBaseUrl() + notifUrl;
    }
}
