package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.AuthenticationInteractor;
import us.globalpay.manhattan.interactors.AuthenticationListener;
import us.globalpay.manhattan.models.Consumer;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.AuthenticateReqBody;
import us.globalpay.manhattan.models.api.AuthenticateResponse;
import us.globalpay.manhattan.presenters.interfaces.IAuthenticatePresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.AuthenticateView;

/**
 * Created by Josué Chávez on 24/09/2018.
 */
public class AuthenticatePresenter implements IAuthenticatePresenter, AuthenticationListener
{
    private static final String TAG = AuthenticatePresenter.class.getSimpleName();

    private Context mContext;
    private AuthenticateView mView;
    private AppCompatActivity mActivity;
    private AuthenticationInteractor mInteractor;
    private int MNH_SIGNIN = 11;

    //Facebook
    private CallbackManager mCallbackManager;
    private LoginButton mFacebookLoginBtn;

    //Google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleAccount;

    //Firebase
    private FirebaseAuth mFirebaseAuth;

    public AuthenticatePresenter(Context context, AppCompatActivity activity, AuthenticateView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mInteractor = new AuthenticationInteractor(mContext);
    }

    @Override
    public void onAuthenticateConsumerSuccess(AuthenticateResponse response)
    {
        mView.hideLoadingDialog();
        UserData.getInstance(mContext).saveUserGeneralInfo(response.getFirstName(), response.getLastName(), response.getEmail(), response.getPhone());
        UserData.getInstance(mContext).saveAuthenticationKey(response.getAuthenticationKey());
        UserData.getInstance(mContext).saveNickname(response.getNickname());
        UserData.getInstance(mContext).setAuthenticated(true);
        UserData.getInstance(mContext).saveCountryID(String.valueOf(response.getCountryID()));

        //Saves if user has nickname
        if(!TextUtils.isEmpty(response.getNickname()))
            UserData.getInstance(mContext).hasSetNickname(true);
        else if (response.getNickname() != null)
            UserData.getInstance(mContext).hasSetNickname(true);
        else
            UserData.getInstance(mContext).hasSetNickname(false);


        if(UserData.getInstance(mContext).getUserPhone() == null || TextUtils.isEmpty(UserData.getInstance(mContext).getUserPhone()))
        {
            mView.navigateValidatePhone();
        }
        else if(UserData.getInstance(mContext).getNickname() == null || TextUtils.isEmpty(UserData.getInstance(mContext).getNickname()))
        {
            mView.navigateSetNickname();
        }
        else
        {
            UserData.getInstance(mContext).setConfirmedPhone(true);
            UserData.getInstance(mContext).setSelectedRegCountry(true);
            mView.navigateHome();
        }
    }

    @Override
    public void onAuthenticateConsumerError(int code, Throwable throwable, String rawResponse)
    {
        mView.hideLoadingDialog();
        DialogModel model = new DialogModel();
        model.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        model.setContent(mContext.getString(R.string.error_label_something_went_wrong));
        model.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericDialog(model);
    }

    @Override
    public void onFacebookEmailSuccess(String email, LoginResult loginResult)
    {
        mInteractor.authenticateFirebase(this, loginResult.getAccessToken().getToken(), email);
    }

    @Override
    public void onFacebookEmailError()
    {
        //Logs out the user if first was setAuthenticated and then happened some error
        logoutFacebookUser();
    }

    @Override
    public void onFirebaseAuthSuccess(String email, String authProvider)
    {
        Consumer consumer = new Consumer();

        try
        {
            switch (authProvider)
            {
                case Constants.FACEBOOK:

                    Profile profile = Profile.getCurrentProfile();
                    String firstname = (!TextUtils.isEmpty(profile.getFirstName())) ? profile.getFirstName() : "NotFound";
                    String lastname = (!TextUtils.isEmpty(profile.getLastName())) ? profile.getLastName() : "NotFound";
                    String facebookUrl = (!TextUtils.isEmpty(profile.getLinkUri().toString())) ? profile.getLinkUri().toString() : "NotFound";
                    String profileId = (!TextUtils.isEmpty(profile.getId())) ? profile.getId() : "NotFound";
                    String middlename = (!TextUtils.isEmpty(profile.getMiddleName())) ? profile.getMiddleName() : "NotFound";
                    String name = (!TextUtils.isEmpty(profile.getName())) ? profile.getName() : "NotFound";

                    consumer.setFirstName(firstname);
                    consumer.setLastName(lastname);
                    consumer.setDeviceID(UserData.getInstance(mContext).getDeviceID());
                    consumer.setURL(facebookUrl);
                    consumer.setProfileID(profileId);
                    consumer.setEmail(email);
                    consumer.setMiddleName(middlename);
                    consumer.setUserID(profileId);

                    break;

                case Constants.GOOGLE:
                    /*String googleUrl = (TextUtils.isEmpty(mGoogleAccount.getPhotoUrl().toString())) ? "NotFound" : mGoogleAccount.getPhotoUrl().toString();

                    UserData.getInstance(mContext).saveGooglePhotoUrl(googleUrl);

                    consumer.setFirstName(mGoogleAccount.getGivenName());
                    consumer.setLastName(mGoogleAccount.getFamilyName());
                    consumer.setDeviceID(UserData.getInstance(mContext).getDeviceID());
                    consumer.setURL("NotFound");
                    consumer.setProfileID(mGoogleAccount.getId());
                    consumer.setEmail(mGoogleAccount.getEmail());
                    consumer.setMiddleName("NotFound");
                    consumer.setUserID(mGoogleAccount.getId());*/

                    break;
            }

            consumer.setAuthProvider(UserData.getInstance(mContext).getAuthModeSelected());
            UserData.getInstance(mContext).saveAuthData(consumer.getProfileID(), consumer.getURL());

            String name = consumer.getFirstName() + " " + consumer.getLastName();

            //Saves user email in CrashLytics
            Crashlytics.setUserEmail(email);
            Crashlytics.setUserName(name);

            UserData.getInstance(mContext).saveAuthProviderFullname(name);

            //Registers user using API
            AuthenticateReqBody request = createBodyRequest(consumer);
            request.setAuthenticationProvider(authProvider);
            mInteractor.authenticateUser(request, this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFirebaseAuthError()
    {
        try
        {
            mInteractor.logoutFirebase();
            logoutFacebookUser();
            //mInteractor.logoutGoogleUser(this, mGoogleSignInClient);

            mView.hideLoadingDialog();
            DialogModel dialog = new DialogModel();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setContent(mContext.getString(R.string.error_content_facebook_graph));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void init()
    {
        mView.initialize();
    }

    @Override
    public void checkPlayServices()
    {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(mActivity);

        if (result != ConnectionResult.SUCCESS)
        {
            //Any random request code
            int PLAY_SERVICES_RESOLUTION_REQUEST = 1001;

            if (googleAPI.isUserResolvableError(result))
            {
                googleAPI.getErrorDialog(mActivity, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                mView.enableLoginFacebookButton(false);
            }
            else
            {
                mView.enableLoginFacebookButton(true);
            }
        }
    }

    @Override
    public void setupFacebookAuth(LoginButton loginButton)
    {
        UserData.getInstance(mContext).saveAuthModeSelected(Constants.FACEBOOK);

        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginBtn = loginButton;
        mFacebookLoginBtn.setReadPermissions("email", "public_profile");
    }

    @Override
    public void setupGoogleAuth()
    {
        try
        {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestServerAuthCode(Constants.GOOGLE_OAUTH_CLIENT_ID)
                    .requestServerAuthCode(Constants.GOOGLE_OAUTH_CLIENT_ID, false)
                    .requestIdToken(Constants.GOOGLE_OAUTH_CLIENT_ID)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on GoogleSignInOptions: "+ ex.getMessage());
        }
    }

    @Override
    public void checkGoogleSignedIn()
    {

    }

    @Override
    public void facebookSigin()
    {
        try
        {
            mFacebookLoginBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
                    mInteractor.requestFacebookEmail(AuthenticatePresenter.this, loginResult);
                }

                @Override
                public void onCancel()
                {

                }

                @Override
                public void onError(FacebookException error)
                {
                    logoutFacebookUser();
                    DialogModel dialog = new DialogModel();
                    dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                    dialog.setContent(mContext.getString(R.string.error_content_facebook_graph));
                    dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                    mView.showGenericDialog(dialog);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void googleSignin()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
        UserData.getInstance(mContext).saveAuthModeSelected(Constants.GOOGLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, MNH_SIGNIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logoutFacebookUser()
    {
        try
        {
            LoginManager.getInstance().logOut();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error loging out: " + ex.getMessage());
        }
    }

    private AuthenticateReqBody createBodyRequest(Consumer consumer)
    {
        AuthenticateReqBody requestBody = new AuthenticateReqBody();
        try
        {
            requestBody.setFirstName(consumer.getFirstName());
            requestBody.setLastName(consumer.getLastName());
            requestBody.setMiddleName(consumer.getMiddleName());
            requestBody.setEmail(consumer.getEmail());
            requestBody.setDeviceID(consumer.getDeviceID());
            requestBody.setUserID(consumer.getUserID());
            requestBody.setProfileID(consumer.getProfileID());
            requestBody.setUrl(consumer.getURL());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

        return requestBody;
    }
}
