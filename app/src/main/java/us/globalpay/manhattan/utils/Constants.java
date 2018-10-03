package us.globalpay.manhattan.utils;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public class Constants
{
    public static final String PLATFORM = "ANDROID";
    public static final String FACEBOOK = "Facebook";
    public static final String GOOGLE = "Google";

    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final String  INTENT_BUNDLE_AUTH_TYPE = "intent_bundle_auth_type";
    public static final String BUNDLE_PHONE_RETYPE = "bundle_data_phone_retype";

    //Location
    public static final int LOCATION_REQUEST_INTERVAL = 8000;
    public static final int LOCATION_REQUEST_FASTEST_INTERVAL = 5000;
    public static final int FOUR_METTERS_DISPLACEMENT = 4;
    public static final int ONE_METTER_DISPLACEMENT = 1;
    public static final int GOOGLE_MAPS_ZOOM_CAMERA = 19;

    //Firebase
    public static final double SALES_POINTS_RADIUS_KM = 2;
    public static final double VENDOR_RADIUS_KM = 1;
    public static final double PRIZES_STOP_RADIUS_KM = 2;
    public static final double BRONZE_CHESTS_QUERY_RADIUS_KM = 0.350;
    public static final double SILVER_CHESTS_QUERY_RADIUS_KM = 0.5;
    public static final double GOLD_CHESTS_QUERY_RADIUS_KM = 0.75;

    public static final double PLAYER_RADIUS_KM = 1; // 1,000 meters

    //Chest Types
    public static final int VALUE_CHEST_TYPE_GOLD = 3;
    public static final int VALUE_CHEST_TYPE_SILVER = 2;
    public static final int VALUE_CHEST_TYPE_BRONZE = 1;
    public static final int VALUE_CHEST_TYPE_WILDCARD = 4;
    public static final String NAME_CHEST_TYPE_GOLD = "Gold";
    public static final String NAME_CHEST_TYPE_SILVER = "Silver";
    public static final String NAME_CHEST_TYPE_BRONZE = "Bronze";
    public static final String NAME_CHEST_TYPE_WILDCARD = "Wildcard";

    //Urls
    public final static String TERMS_AND_CONDITIONS_URL = "http://www.recar-go.com/home/terminos/";

    public static final String ONESIGNAL_USER_TAG_KEY = "userid";

    //Intent extras: Notificactions
    public static final String NOTIFICATION_TITLE_EXTRA = "notification_title_extra";
    public static final String NOTIFICATION_BODY_EXTRA = "notification_body_extgra";

    // Google Auth
    public static final String GOOGLE_OAUTH_CLIENT_ID = "764736593889-r30kkh6m26khvn8l8kcdckrvp1e593ol.apps.googleusercontent.com";
}
