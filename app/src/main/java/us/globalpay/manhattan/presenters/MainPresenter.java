package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.FirebasePointInteractor;
import us.globalpay.manhattan.interactors.FirebasePointListener;
import us.globalpay.manhattan.interactors.MainInteractor;
import us.globalpay.manhattan.interactors.MainListener;
import us.globalpay.manhattan.location.GoogleLocationApiManager;
import us.globalpay.manhattan.location.LocationCallback;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.models.api.MainDataResponse;
import us.globalpay.manhattan.models.geofire.PrizePointData;
import us.globalpay.manhattan.models.geofire.WildcardPointData;
import us.globalpay.manhattan.presenters.interfaces.IMainPresenter;
import us.globalpay.manhattan.ui.activities.AddPhone;
import us.globalpay.manhattan.ui.activities.Authenticate;
import us.globalpay.manhattan.ui.activities.Nickname;
import us.globalpay.manhattan.ui.activities.Permissions;
import us.globalpay.manhattan.ui.activities.SmsCodeInput;
import us.globalpay.manhattan.ui.activities.Terms;
import us.globalpay.manhattan.utils.BitmapUtils;
import us.globalpay.manhattan.utils.BrandedChest;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.MetricsUtils;
import us.globalpay.manhattan.utils.MockLocationUtility;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.MainView;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class MainPresenter implements IMainPresenter, MainListener, FirebasePointListener, LocationCallback
{
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Context mContext;
    private MainView mView;
    private MainInteractor mInteractor;
    private AppCompatActivity mActivity;
    private FirebasePointInteractor mFirebaseInteractor;

    private Gson mGson;
    private Map<String, Bitmap> mMarkerMap;
    private BrandedChest mBrandedChest;
    private GoogleLocationApiManager mGoogleLocationApiManager;

    private int mBrandMaxWidthPx;
    private int mBrandMaxHeightPx;

    public MainPresenter(Context context, AppCompatActivity activity, MainView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mGson = new Gson();
        this.mInteractor = new MainInteractor(mContext);
        this.mFirebaseInteractor = new FirebasePointInteractor(mContext, this);

        this.mGoogleLocationApiManager = new GoogleLocationApiManager(activity, mContext, Constants.FOUR_METTERS_DISPLACEMENT);
        this.mGoogleLocationApiManager.setLocationCallback(this);

        mBrandMaxWidthPx = MetricsUtils.pixelsFromDp(mContext, Constants.BRAND_ICMARKER_DP_WIDTH_SIZE);
        mBrandMaxHeightPx = MetricsUtils.pixelsFromDp(mContext, Constants.BRAND_ICMARKER_DP_HEIGHT_SIZE);

        this.mMarkerMap = new HashMap<>();
    }

    @Override
    public void initialize()
    {
        mView.renderMap();
        mView.initialize();
        mBrandedChest = new BrandedChest(mContext);

        MainDataResponse mainDataResponse = mGson.fromJson(UserData.getInstance(mContext).getHomeData(), MainDataResponse.class);

        if(mainDataResponse != null)
        {
            mView.renderCoupons(mainDataResponse.getData().getCupon());

            //Score
            String coins = String.valueOf(mainDataResponse.getData().getTotalCoins());
            String promos = String.valueOf(mainDataResponse.getData().getTotalNewPromo());
            String store = (!TextUtils.isEmpty(mainDataResponse.getData().getStore().get(0).getName()))
                    ? mainDataResponse.getData().getStore().get(0).getName() : "";
            mView.loadInitialValues(coins, promos);
        }
    }

    @Override
    public void checkUserDataCompleted()
    {
        if(!UserData.getInstance(mContext).hasAcceptedTerms())
        {
            Intent acceptTerms = new Intent(mActivity, Terms.class);
            NavFlagsUtil.addFlags(acceptTerms);
            mContext.startActivity(acceptTerms);
        }
        else if(!UserData.getInstance(mContext).hasGrantedDevicePermissions())
        {
            Intent permissions = new Intent(mActivity, Permissions.class);
            NavFlagsUtil.addFlags(permissions);
            mContext.startActivity(permissions);
        }
        else if (!UserData.getInstance(mContext).isUserAuthenticated())
        {
            Intent authenticate = new Intent(mActivity, Authenticate.class);
            NavFlagsUtil.addFlags(authenticate);
            mContext.startActivity(authenticate);
        }
        else if (!UserData.getInstance(mContext).hasSelectedCountry())
        {
            Intent selectCountry = new Intent(mActivity, AddPhone.class);
            NavFlagsUtil.addFlags(selectCountry);
            mContext.startActivity(selectCountry);
        }
        else if(!UserData.getInstance(mContext).hasVerifiedPhone())
        {
            Intent inputToken = new Intent(mActivity, SmsCodeInput.class);
            NavFlagsUtil.addFlags(inputToken);
            mContext.startActivity(inputToken);
        }
        else if(TextUtils.isEmpty(UserData.getInstance(mContext).getNickname()))
        {
            Intent nickname = new Intent(mActivity, Nickname.class);
            NavFlagsUtil.addFlags(nickname);
            mContext.startActivity(nickname);
        }

    }

    @Override
    public void chekcLocationServiceEnabled()
    {
        LocationManager locationManager;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try
        {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(!gpsEnabled && !networkEnabled)
        {
            mView.displayActivateLocationDialog();
        }
    }

    @Override
    public void checkPermissions()
    {
        mView.checkPermissions();
    }

    @Override
    public void connnectToLocationService()
    {
        mGoogleLocationApiManager.connect();
    }

    @Override
    public void onMapReady()
    {
        connnectToLocationService();
    }

    @Override
    public void intializeGeolocation()
    {
        try
        {
            //Checks if mock locations are active
            if(!MockLocationUtility.isMockSettingsON(mContext))
            {
                //mView.getMarkerBitmaps(mMarkerMap);
                //mInteractor.initializeGeolocation();
                mFirebaseInteractor.initializeGeolocation();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void retrieveData()
    {
        mInteractor.retrieveInitialData(this);
    }

    @Override
    public void prizePointsQuery(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mFirebaseInteractor.goldPointsQuery(location, Constants.GOLD_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.silverPointsQuery(location, Constants.SILVER_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.bronzePointsQuery(location, Constants.BRONZE_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.wildcardPointsQuery(location, Constants.BRONZE_CHESTS_QUERY_RADIUS_KM);
    }

    @Override
    public void updatePrizePntCriteria(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mFirebaseInteractor.goldPointsUpdateCriteria(location, Constants.GOLD_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.silverPointsUpdateCriteria(location, Constants.SILVER_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.bronzePointsUpdateCriteria(location, Constants.BRONZE_CHESTS_QUERY_RADIUS_KM);
        mFirebaseInteractor.wildcardPointsUpdateCriteria(location, Constants.BRONZE_CHESTS_QUERY_RADIUS_KM);
    }

    @Override
    public void saveSelectedCoupon(Cupon selected)
    {
        try
        {
            String serialized = mGson.toJson(selected);
            UserData.getInstance(mContext).saveDetailedCoupon(serialized);
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
    }

    @Override
    public void onSuccess(JsonObject rawResponse)
    {
        try
        {
            String rawData = mGson.toJson(rawResponse);

            if(!TextUtils.equals(rawData, UserData.getInstance(mContext).getHomeData()))
            {
                UserData.getInstance(mContext).saveHomeData(rawData);

                MainDataResponse mainDataResponse = mGson.fromJson(rawData, MainDataResponse.class);

                //Save sponsored marker icon
                for(final Brand item : mainDataResponse.getData().getBrand())
                {
                    Glide.with(mContext).load(item.getUrlLogo()).into(new Target<Drawable>()
                    {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder)
                        {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable)
                        {

                        }

                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition)
                        {
                            Bitmap bitmap = BitmapUtils.drawableToBitmap(resource);
                            String iconName = Constants.PREFIX_BRAND_ICON_MARKER + item.getName()
                                    .replaceAll(" ", "_")
                                    .replaceAll("%20", "_");

                            Bitmap resizedBrandIcon =  BitmapUtils.scaleBrandIcon(bitmap, mBrandMaxWidthPx, mBrandMaxHeightPx);
                            BitmapUtils.save(mContext, resizedBrandIcon, iconName);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder)
                        {

                        }

                        @Override
                        public void getSize(@NonNull SizeReadyCallback cb)
                        {

                        }

                        @Override
                        public void removeCallback(@NonNull SizeReadyCallback cb)
                        {

                        }

                        @Override
                        public void setRequest(@Nullable Request request)
                        {

                        }

                        @Nullable
                        @Override
                        public Request getRequest()
                        {
                            return null;
                        }

                        @Override
                        public void onStart()
                        {

                        }

                        @Override
                        public void onStop()
                        {

                        }

                        @Override
                        public void onDestroy()
                        {

                        }
                    });
                }

                //Display new data
                String coins = String.valueOf(mainDataResponse.getData().getTotalCoins());
                String promos = String.valueOf(mainDataResponse.getData().getTotalNewPromo());


                mView.loadInitialValues(coins, promos);
                mView.renderCoupons(mainDataResponse.getData().getCupon());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onError(int codeStatus, Throwable t, String rawResponse)
    {

    }


    @Override
    public void query_goldPoint_onKeyEntered(String key, LatLng location)
    {
        try
        {
            Bitmap goldMarker = retrieveBitmap(Constants.NAME_CHEST_TYPE_GOLD);
            Bitmap brand = mBrandedChest.getRandomBrandBitmap();

            Bitmap marker = BitmapUtils.getSponsoredMarker(mContext, goldMarker, brand);

            mView.addGoldPoint(key, location, marker);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void query_goldPoint_onKeyExited(String key)
    {
        mView.removeGoldPoint(key);
    }

    @Override
    public void query_goldPoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_silverPoint_onKeyEntered(String key, LatLng location)
    {
         Bitmap silverMarker = retrieveBitmap(Constants.NAME_CHEST_TYPE_SILVER);
        Bitmap brand = mBrandedChest.getRandomBrandBitmap();

        Bitmap marker = BitmapUtils.getSponsoredMarker(mContext, silverMarker, brand);
        mView.addSilverPoint(key, location, marker);
    }

    @Override
    public void query_silverPoint_onKeyExited(String key)
    {
        mView.removeSilverPoint(key);
    }

    @Override
    public void query_silverPoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_bronzePoint_onKeyEntered(String key, LatLng location)
    {
        Bitmap bronzeBitmap = retrieveBitmap(Constants.NAME_CHEST_TYPE_BRONZE);
        Bitmap brand = mBrandedChest.getRandomBrandBitmap();

        Bitmap marker = BitmapUtils.getSponsoredMarker(mContext, bronzeBitmap, brand);
        mView.addBronzePoint(key, location, marker);
    }

    @Override
    public void query_bronzePoint_onKeyExited(String key)
    {
        mView.removeBronzePoint(key);
    }

    @Override
    public void query_bronzePoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_wildcardPoint_onKeyEntered(String key, LatLng location)
    {
        Bitmap wildcardBitmap = retrieveBitmap(Constants.NAME_CHEST_TYPE_WILDCARD);
        Bitmap brand = mBrandedChest.getRandomBrandBitmap();

        Bitmap marker = BitmapUtils.getSponsoredMarker(mContext, wildcardBitmap, brand);
        mView.addWildcardPoint(key, location, marker);
    }

    @Override
    public void query_wildcardPoint_onKeyExited(String key)
    {
        mView.removeWildcardPoint(key);
    }

    @Override
    public void query_wildcardPoint_onGeoQueryReady()
    {

    }

    @Override
    public void goldPoint_onDataChange(String key, PrizePointData goldPointData)
    {
        if(goldPointData != null)
            mView.addGoldPointData(key, goldPointData.getCoins(), goldPointData.getDetail());
    }

    @Override
    public void goldPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void silverPoint_onDataChange(String key, PrizePointData silverPointData)
    {
        if(silverPointData != null)
            mView.addSilverPointData(key, silverPointData.getCoins(), silverPointData.getDetail());
    }

    @Override
    public void silverPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void bronzePoint_onDataChange(String key, PrizePointData bronzePointData)
    {
        if(bronzePointData != null)
            mView.addBronzePointData(key, bronzePointData.getCoins(), bronzePointData.getDetail());
    }

    @Override
    public void bronzePoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void wildcardPoint_onDataChange(String key, WildcardPointData wildcardYCRData)
    {
        String title = mContext.getString(R.string.title_wildcard_pointer);
        String message = mContext.getString(R.string.label_wildcard_pointer);
        if(wildcardYCRData != null)
            mView.addWildcardPointData(key, wildcardYCRData.getBrand(), title, message);
    }

    @Override
    public void wildcardPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void detachFirebaseListeners()
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        try
        {
            if(location != null)
            {
                //Checks if location received is fake
                if(!MockLocationUtility.isMockLocation(location, mContext))
                {
                    //Checks apps blacklist
                    if(MockLocationUtility.isMockAppInstalled(mContext) <= 0 )
                    {
                        mView.updateUserLocationOnMap(location);
                    }
                    else
                    {
                        mView.showToast(mContext.getString(R.string.toast_mock_apps_may_be_installed));
                    }
                }

                mView.updateUserLocationOnMap(location);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            if(location != null)
            {
                //Checks if location received is fake
                if(!MockLocationUtility.isMockLocation(location, mContext))
                {
                    //Checks apps in blaclist
                    if(MockLocationUtility.isMockAppInstalled(mContext) <= 0)
                        mView.setInitialUserLocation(location);
                    else
                        mView.showToast(mContext.getString(R.string.toast_mock_apps_may_be_installed));
                }
                mView.setInitialUserLocation(location);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLocationApiManagerDisconnected()
    {

    }


    private Bitmap retrieveBitmap(String chestName)
    {
        Bitmap bitmap = null;

        try
        {
            File f = new File(Environment.getExternalStorageDirectory() + "/2898018mhn759rsrth4n/" + chestName + ".png");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "FileNotFoundException: " + e.getLocalizedMessage());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving bitmap: " + ex.getMessage());
        }

        return bitmap;

    }
}
