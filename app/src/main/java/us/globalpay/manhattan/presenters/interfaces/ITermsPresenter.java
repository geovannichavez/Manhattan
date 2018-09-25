package us.globalpay.manhattan.presenters.interfaces;

/**
 * Created by Josué Chávez on 25/09/2018.
 */
public interface ITermsPresenter
{
    void initialize();
    void acceptTerms();
    void setFirstTimeSettings();
    void grantDevicePermissions();
    void checkDeviceComponents();
    void presentTerms();


}
