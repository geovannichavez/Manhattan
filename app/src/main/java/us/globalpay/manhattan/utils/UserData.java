package us.globalpay.manhattan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import us.globalpay.manhattan.models.api.Country;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public class UserData
{
    private static final String TAG = UserData.class.getSimpleName();

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static Context mContext;
    private int PRIVATE_MODE = 0;
    private static UserData singleton;

    private static final String PREFERENCES_NAME = "manh4200918-451-t4n";

    //User Info
    private static final String KEY_CONSUMER_COUNTRY_ID = "usr_country_id";
    private static final String KEY_CONSUMER_COUNTRY_PHONE_CODE = "usr_country_phone_code";
    private static final String KEY_CONSUMER_COUNTRY_IS3CODE = "usr_country_iso3code";
    private static final String KEY_CONSUMER_COUNTRY_NAME = "usr_country_name";
    private static final String KEY_CONSUMER_FIRSTNAME = "usr_consumer_firstname";
    private static final String KEY_CONSUMER_LASTNAME = "usr_consumer_lastname";
    private static final String KEY_CONSUMER_EMAIL = "usr_consumer_email";
    private static final String KEY_CONSUMER_PHONE = "usr_consumer_phone";
    private static final String KEY_CONSUMER_NICKNAME = "key_consumer_nickname";
    private static final String KEY_CONSUMER_SIMPLE_PHONE = "usr_simple_phone";
    private static final String KEY_CONSUMER_ID = "usr_consumer_id";

    private static final String KEY_AUTH_MODE_SELECTED = "key_auth_mode_selected";

    //App Preferences and Settings
    private static final String KEY_HAS_ACCEPTED_TERMS = "usr_has_accepted_terms";
    private static final String KEY_HAS_SELECTED_COUNTRY = "usr_has_selected_country";
    private static final String KEY_HAS_CONFIRMED_PHONE = "usr_has_confirmed_phone";
    private static final String KEY_HAS_GRANTED_DEVICE_PERMISSIONS = "usr_has_granted_device_permissions";
    private static final String KEY_HAS_AUTHENTICATED = "usr_has_authenticated";
    private static final String KEY_AUTHENTICATION_KEY = "usr_authentication_key";
    private static final String KEY_HAS_CONFIRMED_LIMITED_FUNCTIONALIITY = "usr_has_confirmed_limited_functionalty";
    private static final String KEY_HAS_SET_NICKNAME = "usr_has_set_nickname";
    private static final String KEY_WELCOME_CHEST_AVAILABLE = "key_welcome_chest_available";

    //First time settings
    private static final String KEY_FIRTTIME_SIMPLE_INSTRUCTIONS_SHOWED = "usr_firsttime_simple_instructions";
    private static final String KEY_WELCOME_CHEST_LATITUDE = "key_welcome_chest_latitude";
    private static final String KEY_WELCOME_CHEST_LONGITUDE = "key_welcome_chest_longitude";

    //Device
    private static final String KEY_UNIQUE_DEVICE_ID = "app_unique_device_id";
    private static final String KEY_3D_COMPATIBLE_DEVICE = "app_3d_compatible_device";

    //Provider Data
    private static final String KEY_FACEBOOK_FIRST_NAME = "usr_facebook_first_name";
    private static final String KEY_FACEBOOK_LAST_NAME = "usr_facebook_last_name";
    private static final String KEY_FACEBOOK_EMAIL = "usr_facebook_email";
    private static final String KEY_AUTH_PROVIDER_FULLNAME = "usr_auth_provider_fullname";
    private static final String KEY_AUTH_PROVIDER_ID = "usr_auth_provider_id";
    private static final String KEY_AUTH_PROVIDER_URL = "usr_auth_provider_url";

    //Coins and Chests
    private static final String KEY_TOTAL_WON_COINS = "usr_total_won_coins";
    private static final String KEY_TOTAL_WON_PRIZES = "usr_total_won_prizes";
    private static final String KEY_CURRENT_COINS_PROGRESS = "usr_current_coins_progress";
    private static final String KEY_LAST_CHEST_EXCHANGED_VALUE = "usr_last_chest_exchanged_value";
    private static final String KEY_AWAIT_TIME_PENDING = "usr_await_time_pending";
    private static final String KEY_TOTAL_SOUVENIR = "usr_winned_souvenir";
    private static final String KEY_LAST_CHEST_ID = "usr_last_chest_exchanged";
    private static final String KEY_FIRST_CHEST_KEY_ENTERED = "key_last_chest_key_entered";
    private static final String KEY_LAST_CHEST_LOCATION_LATITUDE = "key_last_chest_location_latitude";
    private static final String KEY_LAST_CHEST_LOCATION_LONGITUDE = "key_last_chest_location_longitude";
    private static final String KEY_LAST_CHEST_LOCATION_TIME = "key_last_chest_location_time";
    private static final String KEY_LAST_WILDCARD_TOUCHED_FIREBASE_ID = "usr_last_wildcard_touched_firebase_id";
    private static final String KEY_LAST_WILDCARD_TOUCHED_CHEST_TYPE = "usr_last_wildcard_touched_chest_type";

    //Coupon
    private static final String KEY_LAST_COUPON_TITLE = "key_last_coupon_type";
    private static final String KEY_LAST_COUPON_DESCRIPTION = "key_last_coupon_description";
    private static final String KEY_LAST_COUPON_URL_BACKGROUND = "key_last_coupon_url_background";
    private static final String KEY_LAST_COUPON_URL_BACKGROUND_HISTORY = "key_last_coupon_url_background_history";
    private static final String KEY_LAST_COUPON_PURCHASABLE = "key_last_coupon_purchasable";
    private static final String KEY_LAST_COUPON_RESPONSE_CODE = "key_last_coupon_response_code";
    private static final String KEY_LAST_COUPON_PIN_LEVEL = "key_last_coupon_pin_level";
    private static final String KEY_LAST_COUPON_CODE = "key_last_coupon_code";

    //Brands
    private static final String KEY_SELECTED_BRAND = "key_selected_brand";

    //RAW Data
    private static final String KEY_BRANDS_RAW_DATA = "rsbrn-rw-dta260918305-ser-manh";
    private static final String KEY_HOME_RAW_DATA = "rshm-rw-dta2709018948-ser-manh";
    private static final String KEY_PROMOS_RAW_DATA = "rshm-rw-dta0910018706-ser-manh";
    private static final String KEY_COUPONS_RAW_DATA = "rscp-rw-dta06100181737-ser-manh";

    private UserData(Context context)
    {
        UserData.mContext = context;
        mPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public static synchronized UserData getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new UserData(context);
        }
        return singleton;
    }

    public void saveSelectedCuntry(Country country)
    {
        mEditor.putString(KEY_CONSUMER_COUNTRY_ID, country.get$id());
        mEditor.putString(KEY_CONSUMER_COUNTRY_PHONE_CODE, country.getPhoneCode());
        mEditor.putString(KEY_CONSUMER_COUNTRY_IS3CODE, country.getCode());
        mEditor.putString(KEY_CONSUMER_COUNTRY_NAME, country.getName());
        mEditor.commit();
    }

    public void saveAuthModeSelected(String mode)
    {
        mEditor.putString(KEY_AUTH_MODE_SELECTED, mode);
        mEditor.commit();
    }

    public String getAuthModeSelected()
    {
        return mPreferences.getString(KEY_AUTH_MODE_SELECTED, "none");
    }

    public String getDeviceID()
    {
        return mPreferences.getString(KEY_UNIQUE_DEVICE_ID, "");
    }

    public void saveAuthData(String authProviderID, String url)
    {
        mEditor.putString(KEY_AUTH_PROVIDER_ID, authProviderID);
        mEditor.putString(KEY_AUTH_PROVIDER_URL, url);
        mEditor.commit();
    }

    public void saveAuthProviderFullname(String name)
    {
        mEditor.putString(KEY_AUTH_PROVIDER_FULLNAME, name);
        mEditor.commit();
    }

    public void saveUserGeneralInfo(String firstname, String lastname, String email, String userPhone)
    {
        try
        {
            mEditor.putString(KEY_CONSUMER_FIRSTNAME, firstname);
            mEditor.putString(KEY_CONSUMER_LASTNAME, lastname);
            mEditor.putString(KEY_CONSUMER_EMAIL, email);
            mEditor.putString(KEY_CONSUMER_PHONE, userPhone); //Valor null. Se guardará en conf. de SMS
            mEditor.commit();
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    public void saveAuthenticationKey(String authKey)
    {
        mEditor.putString(KEY_AUTHENTICATION_KEY, authKey);
        mEditor.commit();
    }

    public String getUserAuthenticationKey()
    {
        return mPreferences.getString(KEY_AUTHENTICATION_KEY, "");
    }

    public void saveNickname(String nickname)
    {
        mEditor.putString(KEY_CONSUMER_NICKNAME, nickname);
        mEditor.commit();
    }

    public void setAuthenticated(boolean authenticated)
    {
        mEditor.putBoolean(KEY_HAS_AUTHENTICATED, authenticated);
        mEditor.commit();
    }

    public boolean isUserAuthenticated()
    {
        return mPreferences.getBoolean(KEY_HAS_AUTHENTICATED, false);
    }

    public void saveCountryID(String countryID)
    {
        mEditor.putString(KEY_CONSUMER_COUNTRY_ID, countryID);
        mEditor.commit();
    }

    public void hasSetNickname(boolean hasSetNickname)
    {
        mEditor.putBoolean(KEY_HAS_SET_NICKNAME, hasSetNickname);
        mEditor.commit();
    }

    public void deleteNickname()
    {
        mEditor.remove(KEY_CONSUMER_NICKNAME);
        mEditor.commit();
    }

    public String getUserPhone()
    {
        return mPreferences.getString(KEY_CONSUMER_PHONE, "");
    }

    public String getNickname()
    {
        return mPreferences.getString(KEY_CONSUMER_NICKNAME, "");
    }


    public void setSelectedRegCountry(boolean pSelectedCountry)
    {
        mEditor.putBoolean(KEY_HAS_SELECTED_COUNTRY, pSelectedCountry);
        mEditor.commit();
    }

    public boolean hasSelectedCountry()
    {
        return mPreferences.getBoolean(KEY_HAS_SELECTED_COUNTRY, false);
    }

    public void setConfirmedPhone(boolean pConfirmedPhone)
    {
        mEditor.putBoolean(KEY_HAS_CONFIRMED_PHONE, pConfirmedPhone);
        mEditor.commit();
    }

    public boolean hasVerifiedPhone()
    {
        return mPreferences.getBoolean(KEY_HAS_CONFIRMED_PHONE, false);
    }

    public void save3DCompatibleValue(boolean hasAllRequirements)
    {
        mEditor.putBoolean(KEY_3D_COMPATIBLE_DEVICE, hasAllRequirements);
        mEditor.commit();
    }

    public void saveSimpleInstructionsSetting(boolean shown)
    {
        mEditor.putBoolean(KEY_FIRTTIME_SIMPLE_INSTRUCTIONS_SHOWED, shown);
        mEditor.commit();
    }

    public void hasGrantedDevicePermissions(boolean granted)
    {
        mEditor.putBoolean(KEY_HAS_GRANTED_DEVICE_PERMISSIONS, granted);
        mEditor.commit();
    }

    public boolean hasGrantedDevicePermissions()
    {
        return mPreferences.getBoolean(KEY_HAS_GRANTED_DEVICE_PERMISSIONS, false);
    }

    public void accpetedTerms(boolean accepted)
    {
        mEditor.putBoolean(KEY_HAS_ACCEPTED_TERMS, accepted);
        mEditor.commit();
    }

    public boolean hasAcceptedTerms()
    {
        return mPreferences.getBoolean(KEY_HAS_ACCEPTED_TERMS, false);
    }

    public void saveDeviceID(String deviceID)
    {
        mEditor.putString(KEY_UNIQUE_DEVICE_ID, deviceID);
        mEditor.commit();
    }


    public void saveBrandsData(String rawData)
    {
        mEditor.putString(KEY_BRANDS_RAW_DATA, rawData);
        mEditor.commit();
    }

    public String getBrandsData()
    {
        return mPreferences.getString(KEY_BRANDS_RAW_DATA, "");
    }

    public void saveHomeData(String rawData)
    {
        mEditor.putString(KEY_HOME_RAW_DATA, rawData);
        mEditor.commit();
    }

    public String getHomeData()
    {
        return mPreferences.getString(KEY_HOME_RAW_DATA, "");
    }

    public String getAuthProviderId()
    {
        return mPreferences.getString(KEY_AUTH_PROVIDER_ID, "");
    }

    public void saveSimpleUserPhone(String pUserPhone)
    {
        mEditor.putString(KEY_CONSUMER_SIMPLE_PHONE, pUserPhone);
        mEditor.commit();
    }

    public Country getSelectedCountry()
    {
        Country country = new Country();
        try
        {
            country.setCode(mPreferences.getString(KEY_CONSUMER_COUNTRY_ID, ""));
            country.setPhoneCode(mPreferences.getString(KEY_CONSUMER_COUNTRY_PHONE_CODE, ""));
            country.setCountryCode(mPreferences.getString(KEY_CONSUMER_COUNTRY_IS3CODE, ""));
            country.setName(mPreferences.getString(KEY_CONSUMER_COUNTRY_NAME, ""));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return country;
    }

    public void saveUserPhoneInfo(String countryID, String countryPhoneCode, String iso3Code, String countryName, String phone, int consumerID)
    {
        mEditor.putString(KEY_CONSUMER_COUNTRY_ID, countryID);
        mEditor.putString(KEY_CONSUMER_COUNTRY_PHONE_CODE, countryPhoneCode);
        mEditor.putString(KEY_CONSUMER_COUNTRY_IS3CODE, iso3Code);
        mEditor.putString(KEY_CONSUMER_COUNTRY_NAME, countryName);
        mEditor.putString(KEY_CONSUMER_PHONE, phone);
        mEditor.putInt(KEY_CONSUMER_ID, consumerID);
        mEditor.commit();
    }

    public boolean deviceFullCompatible()
    {
        return mPreferences.getBoolean(KEY_3D_COMPATIBLE_DEVICE, false);
    }

    public void setHasConfirmedLimitedFunctionality(boolean confirmed)
    {
        mEditor.putBoolean(KEY_HAS_CONFIRMED_LIMITED_FUNCTIONALIITY, confirmed);
        mEditor.commit();
    }

    public void saveUserTrackingProgess(int pCoins, int pPrizes, int pCoinsProgress, int pSouvenirs, int pEraID)
    {
        //int coins = (pCoins < 0) ? 0 : pCoins;
        //mEditor.putInt(KEY_TOTAL_WON_COINS, coins);
        mEditor.putInt(KEY_TOTAL_WON_COINS, pCoins);
        mEditor.putInt(KEY_TOTAL_WON_PRIZES, pPrizes);
        mEditor.putInt(KEY_CURRENT_COINS_PROGRESS, pCoinsProgress);
        mEditor.putInt(KEY_TOTAL_SOUVENIR, pSouvenirs);
        mEditor.commit();
    }


    public int getCurrentCoinsProgress()
    {
        return mPreferences.getInt(KEY_CURRENT_COINS_PROGRESS, 0);
    }

    public int getSavedSouvenirsCount()
    {
        return mPreferences.getInt(KEY_TOTAL_SOUVENIR, 0);
    }

    public int getConsumerPrizes()
    {
        return mPreferences.getInt(KEY_TOTAL_WON_PRIZES, 0);
    }

    public int getTotalWonCoins()
    {
        return mPreferences.getInt(KEY_TOTAL_WON_COINS, 0);
    }

    public void saveLastWildcardTouched(String pFirebaseID, int chestType)
    {
        mEditor.putString(KEY_LAST_WILDCARD_TOUCHED_FIREBASE_ID, pFirebaseID);
        mEditor.putInt(KEY_LAST_WILDCARD_TOUCHED_CHEST_TYPE, chestType);
        mEditor.commit();
    }

    public void deleteFirstKeyEntered()
    {
        mEditor.remove(KEY_FIRST_CHEST_KEY_ENTERED);
        mEditor.commit();
    }

    public String getFirstKeyEntered()
    {
        return mPreferences.getString(KEY_FIRST_CHEST_KEY_ENTERED, "");
    }

    public void saveFirstKeyEntered(String key)
    {
        mEditor.putString(KEY_FIRST_CHEST_KEY_ENTERED, key);
        mEditor.commit();
    }

    public String getLastExchangedChestID()
    {
        return mPreferences.getString(KEY_LAST_CHEST_ID, "");
    }

    public void saveLastCouponTitle(String title)
    {
        mEditor.putString(KEY_LAST_COUPON_TITLE, title);
        mEditor.commit();
    }

    public String getLastCouponTitle()
    {
        return mPreferences.getString(KEY_LAST_COUPON_TITLE, "");
    }

    public void saveLastCouponDescription(String description)
    {
        mEditor.putString(KEY_LAST_COUPON_DESCRIPTION, description);
        mEditor.commit();
    }

    public String getLastCouponDescription()
    {
        return mPreferences.getString(KEY_LAST_COUPON_DESCRIPTION, "");
    }

    public void saveLastCouponUrlBackground(String urlBackground)
    {
        mEditor.putString(KEY_LAST_COUPON_URL_BACKGROUND, urlBackground);
        mEditor.commit();
    }

    public String getLastCouponUrlBackground()
    {
        return mPreferences.getString(KEY_LAST_COUPON_URL_BACKGROUND, "");
    }

    public void saveLastCouponUrlBackgroundHistory(String urlBackground)
    {
        mEditor.putString(KEY_LAST_COUPON_URL_BACKGROUND_HISTORY, "");
        mEditor.commit();
    }

    public String getLastCouponUrlBackgroundHistory()
    {
        return mPreferences.getString(KEY_LAST_COUPON_URL_BACKGROUND_HISTORY, "");
    }

    public void saveLastCouponPurchasable(boolean isPurchasable)
    {
        mEditor.putBoolean(KEY_LAST_COUPON_PURCHASABLE, isPurchasable);
        mEditor.commit();
    }

    public boolean getLastCouponIsPurchasable()
    {
        return mPreferences.getBoolean(KEY_LAST_COUPON_PURCHASABLE, false);
    }

    public void saveLastCouponCode(String couponCode)
    {
        mEditor.putString(KEY_LAST_COUPON_CODE, couponCode);
        mEditor.commit();
    }

    public String getLastCouponCode()
    {
        return mPreferences.getString(KEY_LAST_COUPON_CODE, "");
    }

    public void saveLastCouponPinLevel(int pinLevel)
    {
        mEditor.putInt(KEY_LAST_COUPON_PIN_LEVEL, pinLevel);
        mEditor.commit();
    }

    public int getLastCouponPinLevel()
    {
        return mPreferences.getInt(KEY_LAST_COUPON_PIN_LEVEL, 0);
    }

    public void saveLastCouponResponseCode(String responseCode)
    {
        mEditor.putString(KEY_LAST_COUPON_RESPONSE_CODE, responseCode);
        mEditor.commit();
    }

    public String getLastChanceResponseCode()
    {
        return mPreferences.getString(KEY_LAST_COUPON_RESPONSE_CODE, "");
    }

    public void saveAwaitTime(String pTime)
    {
        mEditor.putString(KEY_AWAIT_TIME_PENDING, pTime);
        mEditor.commit();
    }

    public String getAwaitTimePending()
    {
        return mPreferences.getString(KEY_AWAIT_TIME_PENDING, "");
    }

    public void savePromosData(String data)
    {
        mEditor.putString(KEY_PROMOS_RAW_DATA, data);
        mEditor.commit();
    }

    public String getPromosData()
    {
        return mPreferences.getString(KEY_PROMOS_RAW_DATA, "");
    }

    public void saveCouponsData(String couponsData)
    {
        mEditor.putString(KEY_COUPONS_RAW_DATA, couponsData);
        mEditor.commit();
    }


    public String getCouponsData()
    {
        return mPreferences.getString(KEY_COUPONS_RAW_DATA, "");
    }

    public void saveSelectedBrand(String serialized)
    {
        mEditor.putString(KEY_SELECTED_BRAND, serialized);
        mEditor.commit();
    }

    public String getSelectedBrand()
    {
        return mPreferences.getString(KEY_SELECTED_BRAND, "");
    }
}
