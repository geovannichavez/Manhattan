package us.globalpay.manhattan.presenters.interfaces;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;

/**
 * Created by Josué Chávez on 24/09/2018.
 */
public interface IAuthenticatePresenter
{
    void init();
    void checkPlayServices();
    void setupFacebookAuth(LoginButton pLoginButton);
    void setupGoogleAuth();
    void checkGoogleSignedIn();
    void facebookSigin();
    void googleSignin();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
