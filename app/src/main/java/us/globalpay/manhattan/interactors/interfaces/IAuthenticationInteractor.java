package us.globalpay.manhattan.interactors.interfaces;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

import us.globalpay.manhattan.interactors.AuthenticationListener;
import us.globalpay.manhattan.models.api.AuthenticateReqBody;
import us.globalpay.manhattan.presenters.AuthenticatePresenter;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public interface IAuthenticationInteractor
{
    void authenticateUser(AuthenticateReqBody authentication, AuthenticationListener listener);
    void requestFacebookEmail(AuthenticationListener listener, LoginResult loginResult);
    void authenticateFirebase(AuthenticationListener listener, String providerToken, String email);
    void logoutFirebase();
}
