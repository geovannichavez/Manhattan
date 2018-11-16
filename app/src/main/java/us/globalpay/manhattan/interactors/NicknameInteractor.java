package us.globalpay.manhattan.interactors;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.INicknameInteractor;
import us.globalpay.manhattan.models.api.NicknameReqBody;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
import us.globalpay.manhattan.presenters.interfaces.INicknamePresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class NicknameInteractor implements INicknameInteractor
{
    private static final String TAG = NicknameInteractor.class.getSimpleName();

    private Context mContext;

    public NicknameInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void validateNickname(String nickname, final NicknameListener listener)
    {
        final NicknameReqBody requestBody = new NicknameReqBody();
        requestBody.setNickname(nickname);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleResultResponse> call = apiService.registerNickname(UserData.getInstance(mContext).getUserAuthenticationKey(),
                requestBody,
                VersionName.getVersionName(mContext, TAG),
                Constants.PLATFORM, VersionName.getPackageName(mContext, TAG),
                VersionName.getDeviceName());

        call.enqueue(new Callback<SimpleResultResponse>()
        {
            @Override
            public void onResponse(Call<SimpleResultResponse> call, Response<SimpleResultResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleResultResponse resultResponse = response.body();
                    listener.onValidateNicknameSuccess(resultResponse, requestBody.getNickname());
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onError(codeResponse, null, null);

                }
            }
            @Override
            public void onFailure(Call<SimpleResultResponse> call, Throwable t)
            {
                listener.onError(0, t, null);
            }
        });
    }
}
