package us.globalpay.manhattan.interactors;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IBrandsInteractor;
import us.globalpay.manhattan.models.api.BrandsReqBody;
import us.globalpay.manhattan.models.api.BrandsResponse;
import us.globalpay.manhattan.models.api.SimpleResponse;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class BrandsInteractor implements IBrandsInteractor
{
    private static final String TAG = BrandsInteractor.class.getSimpleName();

    private Context mContext;

    public BrandsInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveBrands(BrandsReqBody request, final BrandsListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<BrandsResponse> call = apiService.getBrands(request,
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG));

        call.enqueue(new Callback<BrandsResponse>()
        {
            @Override
            public void onResponse(Call<BrandsResponse> call, Response<BrandsResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onSuccess(response.body());
                }
                else
                {
                    try
                    {
                        if(response.code() == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onError(response.code(), null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onError(response.code(), null, response.errorBody().string());
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BrandsResponse> call, Throwable t)
            {
                listener.onError(0, t, "");
            }
        });
    }
}
