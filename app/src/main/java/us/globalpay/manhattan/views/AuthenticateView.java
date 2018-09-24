package us.globalpay.manhattan.views;

import us.globalpay.manhattan.models.DialogModel; /**
 * Created by Josué Chávez on 24/09/2018.
 */
public interface AuthenticateView
{
    void initialize();
    void navigateValidatePhone();
    void navigateSetNickname();
    void navigateHome();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void enableLoginFacebookButton(boolean enabled);
    void showGenericDialog(DialogModel dialog);
}
