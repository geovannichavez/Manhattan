package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.UnknownServiceException;

import us.globalpay.manhattan.interactors.MainInteractor;
import us.globalpay.manhattan.interactors.MainListener;
import us.globalpay.manhattan.models.api.MainDataResponse;
import us.globalpay.manhattan.presenters.interfaces.IMainPresenter;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.MainView;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class MainPresenter implements IMainPresenter, MainListener
{
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Context mContext;
    private MainView mView;
    private MainInteractor mInteractor;
    private Gson mGson;

    public MainPresenter(Context context, AppCompatActivity activity, MainView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new MainInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mGson = new Gson();
        mView.initialize();
        mView.renderMap(); //TODO: LLamar en escenario correcto
        mView.renderCoupons(); //TODO: Quitar

        MainDataResponse mainDataResponse = mGson.fromJson(UserData.getInstance(mContext).getHomeData(), MainDataResponse.class);

        if(mainDataResponse != null)
        {
            //Score
            String coins = String.valueOf(mainDataResponse.getData().getTotalCoins());
            String promos = String.valueOf(mainDataResponse.getData().getTotalNewPromo());
            mView.loadInitialValues(coins, promos);
        }
    }

    @Override
    public void retrieveData()
    {
        mInteractor.retrieveInitialData(this);
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

                //Display new data
                String coins = String.valueOf(mainDataResponse.getData().getTotalCoins());
                String promos = String.valueOf(mainDataResponse.getData().getTotalNewPromo());
                mView.loadInitialValues(coins, promos);
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



}
