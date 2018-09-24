package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import us.globalpay.manhattan.interactors.PhoneValidationInteractor;
import us.globalpay.manhattan.interactors.PhoneValidationListener;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.Country;
import us.globalpay.manhattan.presenters.interfaces.IPhoneValidationPresenter;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.AddPhoneView;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public class PhoneValidationPresenter implements IPhoneValidationPresenter, PhoneValidationListener
{
    private static final String TAG = PhoneValidationListener.class.getSimpleName();

    private Context mContext;
    private AddPhoneView mView;
    private PhoneValidationInteractor mInteractor;

    public PhoneValidationPresenter(Context context, AddPhoneView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new PhoneValidationInteractor(mContext);
    }

    @Override
    public void initailize()
    {
        mView.initializeViews();
    }

    @Override
    public void retrieveCountries()
    {
        mInteractor.retrieveCountries(this);
    }

    @Override
    public void saveSelectedCountry(Country selectedCountry)
    {
        UserData.getInstance(mContext).saveSelectedCuntry(selectedCountry);
    }

    @Override
    public void onRetrieveSuccess(Countries response)
    {
        mView.renderCountries(response.getCountries());
    }

    @Override
    public void onError(int codeResponse, Throwable throwable, String rawResponse)
    {

    }


}
