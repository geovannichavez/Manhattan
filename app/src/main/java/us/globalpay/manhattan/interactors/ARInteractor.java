package us.globalpay.manhattan.interactors;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IARInteractor;
import us.globalpay.manhattan.models.api.GetCouponReq;
import us.globalpay.manhattan.models.api.GetCouponResponse;
import us.globalpay.manhattan.models.api.OpenGiftReq;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ARInteractor implements IARInteractor
{
    private static final String TAG = ARInteractor.class.getSimpleName();

    private Context mContext;

    public ARInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveConsumerTracking()
    {

    }

    @Override
    public void openCoinsChest(final OpenGiftReq request, final ARListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.openGift(request,
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG));

        call.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                try
                {
                    if(response.isSuccessful())
                        listener.onOpenGiftSuccess(response.body(), request.getLocationID());
                    else
                        listener.onOpenGiftError(response.code(), null, response.errorBody().string());
                }
                catch (IOException ex) {  Log.e(TAG, "Error: " + ex.getMessage());  }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onOpenGiftError(0, t, null);
            }
        });
    }


    @Override
    public void atemptRedeemCoupon(final ARListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<GetCouponResponse> call = apiService.getCouponByCoins(
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG));

        call.enqueue(new Callback<GetCouponResponse>()
        {
            @Override
            public void onResponse(Call<GetCouponResponse> call, Response<GetCouponResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onRedeemCouponSuccess(response.body());
                }
                else
                {
                    try
                    {
                        listener.onRedeemCouponError(response.code(), null, response.errorBody().string());
                    }
                    catch (IOException e) { Log.e(TAG, "Error trying get response as String: " + e.getMessage()); }
                }
            }

            @Override
            public void onFailure(Call<GetCouponResponse> call, Throwable t)
            {
                listener.onRedeemCouponError(0, t, "");
            }
        });
    }

    @Override
    public void getBrandCoupon(GetCouponReq request, final ARListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getBrandCoupon(request,
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG));

        call.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                try
                {
                    if(response.isSuccessful())
                        listener.onBrandCoupon(response.body());
                    else
                        listener.onBrandCouponError(response.code(), response.errorBody().string());
                }
                catch (IOException ex) {    Log.e(TAG, "Error: " + ex.getMessage());   }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onBrandCouponError(0, null);
            }
        });
    }
}
