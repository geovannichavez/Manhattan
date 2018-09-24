package us.globalpay.manhattan.views;

import java.util.List;

import us.globalpay.manhattan.models.api.Country;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface AddPhoneView
{
    void initializeViews();
    void renderCountries(List<Country> countries);
    void showDialog();
}
