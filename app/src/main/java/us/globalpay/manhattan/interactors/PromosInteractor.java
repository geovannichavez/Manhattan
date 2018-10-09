package us.globalpay.manhattan.interactors;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IPromosInteractor;
import us.globalpay.manhattan.models.api.PromosRequest;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class PromosInteractor implements IPromosInteractor
{
    private static final String TAG = PromosInteractor.class.getSimpleName();

    private Context mContext;

    public PromosInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrievePromos(final PromosRequest request, final PromosListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getPromos(request,
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
                    {
                        listener.onPromosSucces(response.body(), listener);
                    }
                    else
                    {
                        listener.onPromosError(response.code(), null, response.errorBody().string());
                    }
                }
                catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onPromosError(0, t, "");
            }
        });
    }
}
