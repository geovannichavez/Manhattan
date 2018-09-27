package us.globalpay.manhattan.interactors;

import android.content.Context;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IMainInteractor;
import us.globalpay.manhattan.ui.activities.Main;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class MainInteractor implements IMainInteractor
{
    private static final String TAG = MainInteractor.class.getSimpleName();

    private Context mContext;

    public MainInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveInitialData(final MainListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getInitialData(
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG));

        call.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(response.isSuccessful())
                {
                    listener.onSuccess(response.body());
                }
                else
                {
                    listener.onError(response.code(), null, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onError(0, t, "");
            }
        });

    }
}
