package us.globalpay.manhattan.presenters.interfaces;

import us.globalpay.manhattan.models.api.Country; /**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface IPhoneValidationPresenter
{
    void initailize();
    void retrieveCountries();
    void saveSelectedCountry(Country mSelectedCountry);
    void requestToken(String phoneNumber, String authType);
    void saveUserGeneralData(String rawPhone, int consumerID);
}
