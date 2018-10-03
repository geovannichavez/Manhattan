package us.globalpay.manhattan.views;

import java.util.List;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Country;
import us.globalpay.manhattan.models.api.RegisterClientResponse;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface AddPhoneView
{
    void initializeViews();
    void renderCountries(List<Country> countries);
    void retypePhoneView();
    void setTypedPhone(String phone);
    void showLoading(String content);
    void hideLoading();
    void showGenericMessage(DialogModel errorMessage);
    void navigateTokenInput(RegisterClientResponse pResponse, String stringExtra);
}
