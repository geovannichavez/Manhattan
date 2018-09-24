package us.globalpay.manhattan.interactors;

import us.globalpay.manhattan.models.api.Countries;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface PhoneValidationListener
{
    void onRetrieveSuccess(Countries response);
    void onError(int codeResponse, Throwable throwable, String rawResponse);
}
