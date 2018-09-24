package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.PhoneValidationListener;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface IPhoneValidationInteractor
{
    void retrieveCountries(PhoneValidationListener listener);
}
