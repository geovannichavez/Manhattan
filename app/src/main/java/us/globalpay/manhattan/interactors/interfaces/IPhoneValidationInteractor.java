package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.PhoneValidationListener;
import us.globalpay.manhattan.models.api.SmsValidationReqBody;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface IPhoneValidationInteractor
{
    void retrieveCountries(PhoneValidationListener listener);
    void validatePhone(PhoneValidationListener listener, String msisdn, String countryID);
    void validateSmsCode(SmsValidationReqBody request, PhoneValidationListener listener);
}
