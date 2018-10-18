package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.ARInteractor;
import us.globalpay.manhattan.interactors.ARListener;
import us.globalpay.manhattan.interactors.FirebasePointInteractor;
import us.globalpay.manhattan.interactors.FirebasePointListener;
import us.globalpay.manhattan.location.GoogleLocationApiManager;
import us.globalpay.manhattan.location.LocationCallback;
import us.globalpay.manhattan.models.ArchViewGeoObject;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.BrandsResponse;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.models.api.GetCouponReq;
import us.globalpay.manhattan.models.api.GetCouponResponse;
import us.globalpay.manhattan.models.geofire.PrizePointData;
import us.globalpay.manhattan.models.geofire.WildcardPointData;
import us.globalpay.manhattan.presenters.interfaces.IARPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.MockLocationUtility;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.ARView;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ARPresenter implements IARPresenter, LocationCallback, FirebasePointListener, ARListener
{
    private static final String TAG = ARPresenter.class.getSimpleName();

    private Context mContext;
    private ARView mView;
    private ARInteractor mInteractor;
    private AppCompatActivity mActivity;
    private boolean mIsRunning;
    private boolean mChestsEntered;
    private Location mCurrentLocation;

    private GoogleLocationApiManager mGoogleLocationApiManager;
    private FirebasePointInteractor mFirebaseInteractor;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Gson mGson;

    private static String mCurrentChestKey;

    public ARPresenter(Context context, AppCompatActivity activity, ARView view)
    {
        mCurrentChestKey = "";

        this.mContext = context;
        this.mView = view;
        this.mGson = new Gson();
        this.mActivity = activity;
        this.mInteractor = new ARInteractor(mContext);
        this.mChestsEntered = false;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
    }

    @Override
    public void initialize()
    {
        mView.setClickListeners();

        //Udpate indicators
        mView.updatePrizeButton(UserData.getInstance(mContext).getCurrentCoinsProgress());


        mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.ONE_METTER_DISPLACEMENT);
        mGoogleLocationApiManager.setLocationCallback(this);

        this.mFirebaseInteractor = new FirebasePointInteractor(mContext, this);

        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializeGeolocation();


        mView.switchChestVisible(false);

        if (!UserData.getInstance(mContext).deviceFullCompatible())
        {
            mView.on2DChestClick();
            //mView.hideArchViewNoContainersMsg(); //TODO: Para que se oculta?
        }

        String nickname = UserData.getInstance(mContext).getNickname();
        if(!TextUtils.isEmpty(nickname))
        {
            Crashlytics.setUserIdentifier(nickname);
        }
    }

    @Override
    public void resume()
    {
        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializeGeolocation();
    }

    @Override
    public void chestPointsQuery(LatLng pLocation)
    {
        try
        {
            double radius = (UserData.getInstance(mContext).deviceFullCompatible()) ? Constants.AR_POI_RADIOS_KM : Constants.RECARSTOP_2D_RADIUS_KM;
            GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
            this.mFirebaseInteractor.goldPointsQuery(location, radius);
            this.mFirebaseInteractor.silverPointsQuery(location, radius);
            this.mFirebaseInteractor.bronzePointsQuery(location, radius);
            this.mFirebaseInteractor.wildcardPointsQuery(location,radius);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void updateChestPntCriteria(LatLng pLocation)
    {
        double radius = (UserData.getInstance(mContext).deviceFullCompatible()) ? Constants.AR_POI_RADIOS_KM : Constants.RECARSTOP_2D_RADIUS_KM;
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        this.mFirebaseInteractor.goldPointsUpdateCriteria(location, radius);
        this.mFirebaseInteractor.silverPointsUpdateCriteria(location, radius);
        this.mFirebaseInteractor.bronzePointsUpdateCriteria(location, radius);
    }

    @Override
    public void openChest(JSONObject jsonObject)
    {
        try
        {
            /*synchronized (this)
            {
                if (mIsRunning)
                    return;
            }

            mIsRunning = true;*/


            // Deserializes object from ArchView
            ArchViewGeoObject model = mGson.fromJson(jsonObject.toString(), ArchViewGeoObject.class);
            Brand brand = getBrandItem( model.getBrand());

            if(model.getType() == Constants.ArActionTypes.Scanning.getType())
            {
                mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));

                if(brand != null)
                {
                    GetCouponReq request = new GetCouponReq();
                    request.setBrandID(brand.getBrandID());
                    request.setMethodID(Constants.GetRaCouponMethods.Scanning.getMethod());
                    mInteractor.getBrandCoupon(request, this);
                }
            }
            else if(model.getType() == Constants.ArActionTypes.Wildcard.getType())
            {
                UserData.getInstance(mContext).saveLastWildcardTouched(model.getKey(), Constants.VALUE_CHEST_TYPE_WILDCARD);
                mView.navigateToWildcard();
            }
            else if(model.getType() == Constants.ArActionTypes.Sponsor.getType())
            {
                mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));

                GetCouponReq request = new GetCouponReq();
                request.setBrandID(brand.getBrandID());
                request.setMethodID(Constants.GetRaCouponMethods.TreasureHunt.getMethod());
                mInteractor.getBrandCoupon(request, this);
            }
            else //Chest (1)
            {
                LatLng location = new LatLng(model.getLatitude(), model.getLongitude());
                mView.showLoadingDialog(mContext.getString(R.string.label_loading_exchanging_chest));
                mInteractor.openCoinsChest(location, model.getKey(), model.getChest());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
            Crashlytics.logException(ex);
        }
    }

    @Override
    public void open2DChest(LatLng pLocation, String pFirebaseID, int pChestType)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
        mInteractor.openCoinsChest(pLocation, pFirebaseID, pChestType);
    }

    @Override
    public void retrieveUserTracking()
    {
        //mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
        mInteractor.retrieveConsumerTracking();
    }

    @Override
    public void redeemPrize()
    {
        //TODO Validar las 20 monedas
       /* if(UserData.getInstance(mContext).getCurrentCoinsProgress() < 20)
        {
            mView.hideLoadingDialog();
            DialogModel dialog = new DialogModel();
            dialog.setTitle(mContext.getString(R.string.title_not_enough_coins));
            dialog.setContent(mContext.getString(R.string.label_not_enough_coins));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
        }
        else
        {*/
            mView.showLoadingDialog(mContext.getString(R.string.label_redeeming_prize_wait));
            mInteractor.atemptRedeemCoupon(this);
        //}
    }

    @Override
    public void handle2DCoinTouch()
    {
        mView.setEnabledChestImage(false);
        mView.on2DChestTouch(Constants.REQUIRED_TIME_TOUCH_MILLISECONDS);
    }

    @Override
    public void handleCoinExchangeKeyUp()
    {
        mView.showToast(mContext.getString(R.string.toast_keep_pressed_three_seconds));
        mView.makeChestBlink();
        mView.removeRunnableCallback();
    }

    @Override
    public void touchWildcard_2D(String pFirebaseID, int chestType)
    {
        UserData.getInstance(mContext).saveLastWildcardTouched(pFirebaseID, chestType);
        mView.navigateToWildcard();
    }

    @Override
    public void deleteFirstKeySaved()
    {
        UserData.getInstance(mContext).deleteFirstKeyEntered();
    }

    @Override
    public void registerKeyEntered(String pKey, LatLng location, String chestType)
    {
        try
        {
            //TODO: Evaluar validacion
            //If there is no key, then is the first entered
            //if(TextUtils.equals(UserData.getInstance(mContext).getFirstKeyEntered(), ""))
            //{
                //Saves first key entered
                this.saveFirstKeyEntered2D(pKey);

                //Draws chests
                switch (chestType)
                {
                    case Constants.NAME_CHEST_TYPE_GOLD:
                        mView.drawChest2D(pKey, location, Constants.VALUE_CHEST_TYPE_GOLD);
                        break;
                    case Constants.NAME_CHEST_TYPE_SILVER:
                        mView.drawChest2D(pKey, location, Constants.VALUE_CHEST_TYPE_SILVER);
                        break;
                    case Constants.NAME_CHEST_TYPE_BRONZE:
                        mView.drawChest2D(pKey, location, Constants.VALUE_CHEST_TYPE_BRONZE);
                        break;
                    case Constants.NAME_CHEST_TYPE_WILDCARD:
                        mView.drawChest2D(pKey, location, Constants.VALUE_CHEST_TYPE_WILDCARD);
                        break;
                }

                mView.switchChestVisible(true);
                mView.makeChestBlink();
            //}
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error registering key: " + ex.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        try
        {
            if(location != null)
            {
                // Validates location from property
                if(!MockLocationUtility.isMockLocation(location, mContext))
                {
                    //Check apps blacklist
                    if(MockLocationUtility.isMockAppInstalled(mContext) <= 0)
                    {
                        mView.updateUserLocation(location.getLatitude(), location.getLongitude(), location.getAccuracy());
                        mCurrentLocation = location;
                    }
                    else
                    {
                        mView.showToast(mContext.getString(R.string.toast_mock_apps_may_be_installed));
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            if(location != null)
            {
                //Checks if location is fake
                if(!MockLocationUtility.isMockLocation(location, mContext))
                {
                    //Checks apps in blacklist
                    if(MockLocationUtility.isMockAppInstalled(mContext) <= 0)
                    {
                        mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
                        mCurrentLocation = location;
                    }
                    else
                    {
                        mView.showToast(mContext.getString(R.string.toast_mock_apps_may_be_installed));
                    }
                }

                mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
                mCurrentLocation = location;
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


    /*
    *
    *
    *
    * ****************************************
    *
    *
    *
    *
    *
    *
    *   FIREBASE LISTENER
    *
    *
    *
    *
    *
    *
    *
    * ****************************************
    *
    *
    *
    * */

    @Override
    public void query_goldPoint_onKeyEntered(String key, LatLng location)
    {
        if(!TextUtils.equals(UserData.getInstance(mContext).getLastExchangedChestID(), key))
        {
            if(!TextUtils.equals(mCurrentChestKey, key))
                mCurrentChestKey = key;

            mView.hideArchViewNoContainersMsg();
            mView.onGoldKeyEntered(key, location, "");
        }
    }

    @Override
    public void query_goldPoint_onKeyExited(String key)
    {
        if(UserData.getInstance(mContext).deviceFullCompatible())
        {
            mView.onGoldKeyExited(key);
        }
        else
        {
            mView.removeBlinkingAnimation();
            mView.switchChestVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(key);
        }
    }

    @Override
    public void query_goldPoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_silverPoint_onKeyEntered(String key, LatLng location)
    {
        if(!TextUtils.equals(UserData.getInstance(mContext).getLastExchangedChestID(), key))
        {
            if(!TextUtils.equals(mCurrentChestKey, key))
                mCurrentChestKey = key;

            mView.hideArchViewNoContainersMsg();
            mView.onSilverKeyEntered(key, location, "");
        }
    }

    @Override
    public void query_silverPoint_onKeyExited(String key)
    {
        if(UserData.getInstance(mContext).deviceFullCompatible())
        {
            mView.onSilverKeyExited(key);
        }
        else
        {
            mView.removeBlinkingAnimation();
            mView.switchChestVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(key);
        }
    }

    @Override
    public void query_silverPoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_bronzePoint_onKeyEntered(String key, LatLng location)
    {
        if(!TextUtils.equals(UserData.getInstance(mContext).getLastExchangedChestID(), key))
        {
            if(!TextUtils.equals(mCurrentChestKey, key))
                mCurrentChestKey = key;

            mView.hideArchViewNoContainersMsg();
            mView.onBronzeKeyEntered(key, location, "");
        }
    }

    @Override
    public void query_bronzePoint_onKeyExited(String key)
    {
        if(UserData.getInstance(mContext).deviceFullCompatible())
        {
            mView.onBronzeKeyExited(key);
        }
        else
        {
            mView.removeBlinkingAnimation();
            mView.switchChestVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(key);
        }
    }

    @Override
    public void query_bronzePoint_onGeoQueryReady()
    {

    }

    @Override
    public void query_wildcardPoint_onKeyEntered(String key, LatLng location)
    {
        if(!TextUtils.equals(UserData.getInstance(mContext).getLastExchangedChestID(), key))
        {
            mView.hideArchViewNoContainersMsg();
            if(!TextUtils.equals(mCurrentChestKey, key))
                mCurrentChestKey = key;

            mView.hideArchViewNoContainersMsg();
            mView.onWildcardKeyEntered(key, location, "");
        }
    }

    @Override
    public void query_wildcardPoint_onKeyExited(String key)
    {
        if(UserData.getInstance(mContext).deviceFullCompatible())
        {
            mView.onWildcardKeyExited(key);
        }
        else
        {
            mView.removeBlinkingAnimation();
            mView.switchChestVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(key);
        }
    }

    @Override
    public void query_wildcardPoint_onGeoQueryReady()
    {

    }

    @Override
    public void goldPoint_onDataChange(String key, PrizePointData pGoldPointData)
    {

    }

    @Override
    public void goldPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void silverPoint_onDataChange(String key, PrizePointData pSilverPointData)
    {

    }

    @Override
    public void silverPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void bronzePoint_onDataChange(String key, PrizePointData pBronzePointData)
    {

    }

    @Override
    public void bronzePoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void wildcardPoint_onDataChange(String key, WildcardPointData wildcardYCRData)
    {

    }

    @Override
    public void wildcardPoint_onCancelled(DatabaseError databaseError)
    {

    }

    @Override
    public void detachFirebaseListeners()
    {
        mFirebaseInteractor.detachFirebaseListeners();
    }


    @Override
    public void onGetUserScoreSuccess()
    {

    }

    @Override
    public void onGetUserScoreError(int codeStatsu, Throwable throwable, String raw)
    {

    }

    @Override
    public void onRedeemCouponSuccess(GetCouponResponse response)
    {
        try
        {
            DialogModel dialog = new DialogModel();
            mView.hideLoadingDialog();


            if(!TextUtils.equals(response.getCupon().getCode(), "00"))
            {
                //Saves last Coupom

                UserData.getInstance(mContext).saveLastCouponTitle(response.getCupon().getTitle());
                UserData.getInstance(mContext).saveLastCouponDescription(response.getCupon().getDescription());
                UserData.getInstance(mContext).saveLastCouponUrlBackground(response.getCupon().getUrlBackground());
                UserData.getInstance(mContext).saveLastCouponUrlBackgroundHistory(response.getCupon().getUrlBackgroundHistory());
                UserData.getInstance(mContext).saveLastCouponPurchasable(response.getCupon().isPurchasable());
                UserData.getInstance(mContext).saveLastCouponCode(response.getCupon().getCode());
                UserData.getInstance(mContext).saveLastCouponResponseCode(response.getCupon().getResponseCode());
                UserData.getInstance(mContext).saveLastCouponPinLevel(response.getCupon().getLevel());

                mView.navigateToCouponDetails();
            }
            else
            {
                UserData.getInstance(mContext).saveAwaitTime("0"); //TODO: Se maneja con respuesta en RG
                dialog.setTitle(mContext.getString(R.string.cant_redeem_title));
                dialog.setContent(String.format(mContext.getString(R.string.redeem_prize_interval),
                        UserData.getInstance(mContext).getAwaitTimePending()));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(dialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
            }


        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onRedeemCouponError(int codeStatus, Throwable throwable, String raw)
    {
        mView.hideLoadingDialog();

        if(!UserData.getInstance(mContext).deviceFullCompatible())
            mView.makeChestBlink();

        processErrorMessage(codeStatus, throwable, raw);
    }

    @Override
    public void onBrandCoupon(JsonObject jsonResponse)
    {
        try
        {
            GetCouponResponse response = mGson.fromJson(jsonResponse, GetCouponResponse.class);

            String couponEarned = mGson.toJson(response.getCupon());
            UserData.getInstance(mContext).saveDetailedCoupon(couponEarned);
            mView.navigateToCouponDetails();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onBrandCouponError(int codeStatus, String raw)
    {
        mView.hideLoadingDialog();
        processErrorMessage(codeStatus, null, raw);
    }

    /*
     *
     *
     *
     *       OTHER METHODS
     *
     *
     * */

    private void saveFirstKeyEntered2D(String key)
    {
        try
        {
            UserData.getInstance(mContext).saveFirstKeyEntered(key);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error saving first key entered: " + ex.getMessage());
        }
    }

    private void deleteSpecificFirstKeyEntered2D(String key)
    {
        try
        {
            //Deletes key saved only if is the same that has left
            if(TextUtils.equals(UserData.getInstance(mContext).getFirstKeyEntered(), key))
            {
                UserData.getInstance(mContext).deleteFirstKeyEntered();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error deleting key from shared preferences: " +ex.getMessage());
        }
    }

    private Brand getBrandItem(String name)
    {
        Brand brand = null;

        try
        {
            BrandsResponse serialized = mGson.fromJson(UserData.getInstance(mContext).getBrandsData(), BrandsResponse.class);

            for (Category category : serialized.getCategories().getCategories())
            {
                for (Brand brnd : category.getBrands())
                {
                    if(TextUtils.equals(brnd.getName(), name))
                    {
                        brand = brnd;
                        break;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

        return brand;
    }

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable, String requiredVersion)
    {
        DialogModel errorResponse = new DialogModel();

        try
        {
            String Titulo;
            String Linea1;
            String Button;

            if (pThrowable != null)
            {
                Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                Button = mContext.getString(R.string.button_accept);
            }
            else
            {
                if (pCodeStatus == 426)
                {
                    Titulo = mContext.getString(R.string.title_update_required);
                    Linea1 = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }

            errorResponse.setTitle(Titulo);
            errorResponse.setContent(Linea1);
            errorResponse.setAcceptButton(Button);
            this.mView.showGenericDialog(errorResponse, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
