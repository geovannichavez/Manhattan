package us.globalpay.manhattan.interactors;

import com.google.gson.JsonObject;

import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.RegisterClientResponse;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface PhoneValidationListener
{
    void onRetrieveSuccess(Countries response);
    void onError(int codeResponse, Throwable throwable, String rawResponse);
    void onPhoneValSuccess(int codeStatus, RegisterClientResponse response);
    void onPhoneValError(int codeResponse, Throwable throwable, String rawResponse);
    void onSmsCodeValidated(JsonObject rawResponse);
    void onSmsCodeError(int codeStatus, String rawResponse, Throwable throwable);
}
