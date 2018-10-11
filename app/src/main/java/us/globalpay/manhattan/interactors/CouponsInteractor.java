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
import us.globalpay.manhattan.interactors.interfaces.ICouponsInteractors;
import us.globalpay.manhattan.models.api.BrandCouponsReq;
import us.globalpay.manhattan.models.api.CouponsRequest;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsInteractor implements ICouponsInteractors
{
    private static final String TAG = CouponsInteractor.class.getSimpleName();

    private Context mContext;

    public CouponsInteractor(Context context)
    {
        this.mContext = context;
    }


    @Override
    public void retrieveCoupons(final CouponsRequest request, final CouponsListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getCoupons(request,
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.MjMyMjcxODczNDQxMTQxMg.OTHEyQiIiX3iijPIgHKiQLncRQqtDLX0MUmlgBBz7eE", //TODO: Cmbiar por el mio
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
                        listener.onCoupons(response.body());
                    else
                        listener.onCouponsError(response.code(), null, response.errorBody().string());
                }
                catch (IOException ex) {    Log.e(TAG, "Error: " + ex.getMessage());    }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onCouponsError(0, t, null);
            }
        });
    }

    @Override
    public void retrieveBrandsCoupons(final BrandCouponsReq request, final CouponsListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getBrandCoupons(request,
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
                        listener.onCoupons(response.body());
                    else
                        listener.onCouponsError(response.code(), null, response.errorBody().string());
                }
                catch (IOException ex) {    Log.e(TAG, "Error: " + ex.getMessage());    }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onCouponsError(0, t, null);
            }
        });
    }
}
