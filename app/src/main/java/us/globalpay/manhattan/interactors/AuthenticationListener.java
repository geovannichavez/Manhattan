package us.globalpay.manhattan.interactors;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import us.globalpay.manhattan.models.api.AuthenticateResponse;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public interface AuthenticationListener
{
    void onAuthenticateConsumerSuccess(AuthenticateResponse response);
    void onAuthenticateConsumerError(int code, Throwable throwable, String rawResponse);
    void onFacebookEmailSuccess(String email, LoginResult loginResult);
    void onFacebookEmailError();
    void onFirebaseAuthSuccess(String email, String authProvider);
    void onFirebaseAuthError();

}
