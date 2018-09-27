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

    private static final String KEY_AUTH_MODE_SELECTED = "key_auth_mode_selected";

    //App Preferences and Settings
    private static final String KEY_HAS_ACCEPTED_TERMS = "usr_has_accepted_terms";
    private static final String KEY_HAS_SELECTED_COUNTRY = "usr_has_selected_country";
    private static final String KEY_HAS_CONFIRMED_PHONE = "usr_has_confirmed_phone";
    private static final String KEY_HAS_GRANTED_DEVICE_PERMISSIONS = "usr_has_granted_device_permissions";
    private static final String KEY_HAS_AUTHENTICATED = "usr_has_authenticated";
    private static final String KEY_AUTHENTICATION_KEY = "usr_authentication_key";
    private static final String KEY_HAS_READ_INTRO = "usr_has_read_intro";
    private static final String KEY_HAS_CONFIRMED_LIMITED_FUNCTIONALIITY = "usr_has_confirmed_limited_functionalty";
    private static final String KEY_HAS_SEEN_INTRO = "usr_has_seen_intro";
    private static final String KEY_HAS_SELECTED_ERA = "usr_has_selected_era";
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

    //RAW Data
    private static final String KEY_BRANDS_RAW_DATA = "rsbrn-rw-dta260918305-ser-manh";
    private static final String KEY_HOME_RAW_DATA = "rshm-rw-dta2709018948-ser-manh";

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
        mEditor.putInt(KEY_CONSUMER_COUNTRY_ID, Integer.valueOf(country.get$id()));
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

    public void hasAuthenticated(boolean authenticated)
    {
        mEditor.putBoolean(KEY_HAS_AUTHENTICATED, authenticated);
        mEditor.commit();
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

    public void HasAccpetedTerms(boolean pAccepted)
    {
        mEditor.putBoolean(KEY_HAS_ACCEPTED_TERMS, pAccepted);
        mEditor.commit();
    }

    public void HasSelectedCountry(boolean pSelectedCountry)
    {
        mEditor.putBoolean(KEY_HAS_SELECTED_COUNTRY, pSelectedCountry);
        mEditor.commit();
    }

    public void HasConfirmedPhone(boolean pConfirmedPhone)
    {
        mEditor.putBoolean(KEY_HAS_CONFIRMED_PHONE, pConfirmedPhone);
        mEditor.commit();
    }

    public void HasGrantedDevicePermissions(boolean pGrantedPermissions)
    {
        mEditor.putBoolean(KEY_HAS_GRANTED_DEVICE_PERMISSIONS, pGrantedPermissions);
        mEditor.commit();
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

    public void hasAccpetedTerms(boolean accepted)
    {
        mEditor.putBoolean(KEY_HAS_ACCEPTED_TERMS, accepted);
        mEditor.commit();
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


}
