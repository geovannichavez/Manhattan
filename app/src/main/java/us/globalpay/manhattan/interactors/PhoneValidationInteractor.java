package us.globalpay.manhattan.interactors;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IPhoneValidationInteractor;
import us.globalpay.manhattan.models.api.Countries;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public class PhoneValidationInteractor implements IPhoneValidationInteractor
{
    private static final String TAG = PhoneValidationInteractor.class.getSimpleName();

    private Context mContext;

    public PhoneValidationInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveCountries(final PhoneValidationListener listener)
    {
        try
        {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<Countries> call = apiService.getCountries();

            call.enqueue(new Callback<Countries>()
            {
                @Override
                public void onResponse(Call<Countries> call, Response<Countries> response)
                {
                    if(response.isSuccessful())
                    {

                        Countries countries = response.body();
                        listener.onRetrieveSuccess(countries);
                    }
                    else
                    {
                        int codeResponse = response.code();
                        listener.onError(codeResponse, null, "");
                    }
                }
                @Override
                public void onFailure(Call<Countries> call, Throwable t)
                {
                    listener.onError(0, t, "");
                }
            });
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
