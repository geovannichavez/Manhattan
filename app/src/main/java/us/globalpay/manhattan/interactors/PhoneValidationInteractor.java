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
import us.globalpay.manhattan.interactors.interfaces.IPhoneValidationInteractor;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.RegisterClientResponse;
import us.globalpay.manhattan.models.api.RegisterPhoneConsumerReqBody;
import us.globalpay.manhattan.models.api.SmsValidationReqBody;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

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

    @Override
    public void validatePhone(final PhoneValidationListener listener, String msisdn, String countryID)
    {
        RegisterPhoneConsumerReqBody registerConsumerBody = new RegisterPhoneConsumerReqBody();
        registerConsumerBody.setPhone(msisdn);
        registerConsumerBody.setCountryID(countryID);
        registerConsumerBody.setDeviceID(UserData.getInstance(mContext).getDeviceID());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<RegisterClientResponse> call = apiService.registerConsumer(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG),
                VersionName.getDeviceName(),
                registerConsumerBody);

        call.enqueue(new Callback<RegisterClientResponse>()
        {
            @Override
            public void onResponse(Call<RegisterClientResponse> call, Response<RegisterClientResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onPhoneValSuccess(response.code(), response.body());
                }
                else
                {
                    try
                    {
                        listener.onPhoneValError(response.code(), null, response.errorBody().string());
                    }
                    catch (IOException ex)
                    {
                        Log.e(TAG, "Error: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterClientResponse> call, Throwable t)
            {
                listener.onPhoneValError(0, t, "");
            }
        });

    }

    @Override
    public void validateSmsCode(SmsValidationReqBody request, final PhoneValidationListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.requestSmsValidation(request,
                UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM,
                VersionName.getPackageName(mContext, TAG),
                VersionName.getDeviceName());

        call.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(response.isSuccessful())
                {
                    listener.onSmsCodeValidated(response.body());
                }
                else
                {
                    listener.onSmsCodeError(response.code(), response.errorBody().toString(), null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onSmsCodeError(0, "", t);
            }
        });
    }
}
