package us.globalpay.manhattan.interactors;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.globalpay.manhattan.api.ApiClient;
import us.globalpay.manhattan.api.ApiInterface;
import us.globalpay.manhattan.interactors.interfaces.IAuthenticationInteractor;
import us.globalpay.manhattan.models.api.AuthenticateReqBody;
import us.globalpay.manhattan.models.api.AuthenticateResponse;
import us.globalpay.manhattan.presenters.AuthenticatePresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.VersionName;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public class AuthenticationInteractor implements IAuthenticationInteractor
{
    private static final String TAG = AuthenticationInteractor.class.getSimpleName();

    private Context mContext;
    //Firebase
    private FirebaseAuth mFirebaseAuth;

    public AuthenticationInteractor(Context context)
    {
        this.mContext = context;
        this.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void authenticateUser(AuthenticateReqBody requestBody, final AuthenticationListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AuthenticateResponse> call = apiService.authenticateConsumer(requestBody,
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, VersionName.getPackageName(mContext, TAG) );

        call.enqueue(new Callback<AuthenticateResponse>()
        {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response)
            {
                if (response.isSuccessful())
                {
                    AuthenticateResponse authResult = response.body();
                    listener.onAuthenticateConsumerSuccess(authResult);
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();
                        listener.onAuthenticateConsumerError(codeResponse, null, response.errorBody().string());

                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t)
            {
                listener.onAuthenticateConsumerError(0, t, null);
            }
        });
    }

    @Override
    public void requestFacebookEmail(final AuthenticationListener listener, final LoginResult loginResult)
    {
        GraphRequest mGraphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject me, GraphResponse response)
            {
                if (response.getError() == null)
                {
                    try
                    {
                        String facebookEmail = !me.isNull("email") ? me.getString("email") : "";
                        listener.onFacebookEmailSuccess(facebookEmail, loginResult);
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    listener.onFacebookEmailError();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email, name");
        mGraphRequest.setParameters(parameters);
        mGraphRequest.executeAsync();
    }

    @Override
    public void authenticateFirebase(final AuthenticationListener listener, String providerToken, final String email)
    {
        AuthCredential credential = null;

        try
        {
            switch (UserData.getInstance(mContext).getAuthModeSelected())
            {
                case Constants.FACEBOOK:
                    credential = FacebookAuthProvider.getCredential(providerToken);
                    break;
                case Constants.GOOGLE:
                    credential = GoogleAuthProvider.getCredential(providerToken, null);
                    break;
            }

            if(credential != null)
            {
                mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            if(user != null)
                            {
                                listener.onFirebaseAuthSuccess(email, UserData.getInstance(mContext).getAuthModeSelected());
                            }
                        }
                        else
                        {
                            listener.onFirebaseAuthError();
                        }
                    }
                });
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void logoutFirebase()
    {
        try
        {
            FirebaseAuth.getInstance().signOut();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
