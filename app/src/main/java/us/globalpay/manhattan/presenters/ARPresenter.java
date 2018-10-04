package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.interactors.ARInteractor;
import us.globalpay.manhattan.presenters.interfaces.IARPresenter;
import us.globalpay.manhattan.views.ARView;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ARPresenter implements IARPresenter
{
    private static final String TAG = ARPresenter.class.getSimpleName();

    private Context mContext;
    private ARView mView;
    private ARInteractor mInteractor;

    public ARPresenter(Context context, AppCompatActivity activity, ARView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new ARInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.setClickListeners();

        //Udpate indicators
        /*mView.updatePrizeButton(UserData.getInstance(mContext).getCurrentCoinsProgress());
        String prizes = String.valueOf(UserData.getInstance(mContext).GetConsumerPrizes());
        String souvs = String.valueOf(UserData.getInstance(mContext).getSavedSouvenirsCount());
        int coins = UserData.getInstance(mContext).getTotalWonCoins();

        mView.updateIndicators(prizes, coins, souvs);

        this.mView.obtainUserProgress();

        mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.ONE_METTER_DISPLACEMENT);
        new initializeGoogleMapsCallback().execute();

        this.mFirebaseInteractor = new FirebasePOIInteractor(mContext, this);

        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializePOIGeolocation();

        m3Dcompatible = mUserData.Is3DCompatibleDevice();

        mView.switchRecarcoinVisible(false);

        if (m3Dcompatible)
        {
            mView.on3DChestClick();
        }
        else
        {
            mView.onCoinLongClick();
            mView.hideArchViewLoadingMessage();
        }

        String nickname = UserData.getInstance(mContext).getNickname();
        if(!TextUtils.isEmpty(nickname))
        {
            Crashlytics.setUserIdentifier(nickname);
        }*/
    }

    @Override
    public void resume()
    {

    }

    @Override
    public void chestPointsQuery(LatLng pLocation)
    {

    }

    @Override
    public void updateChestPntCriteria(LatLng pLocation)
    {

    }

    @Override
    public void openChest(String pArchitectURL)
    {

    }

    @Override
    public void open2DChest(LatLng pLocation, String pFirebaseID, int pChestType)
    {

    }

    @Override
    public void retrieveUserTracking()
    {

    }

    @Override
    public void redeemPrize()
    {

    }

    @Override
    public void handle2DCoinTouch()
    {

    }

    @Override
    public void handleCoinExchangeKeyUp()
    {

    }

    @Override
    public void touchWildcard_2D(String pFirebaseID, int chestType)
    {

    }

    @Override
    public void deleteFirstKeySaved()
    {

    }

    @Override
    public void registerKeyEntered(String pKey, LatLng location, String chestType)
    {

    }
}
