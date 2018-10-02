package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.SocketTimeoutException;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.PhoneValidationInteractor;
import us.globalpay.manhattan.interactors.PhoneValidationListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.Country;
import us.globalpay.manhattan.models.api.RegisterClientResponse;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
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
    public void requestToken(String phoneNumber, String uthType)
    {
        try
        {
            String simplePhone = new StringBuilder(phoneNumber).insert(phoneNumber.length()-4, "-").toString();
            UserData.getInstance(mContext).saveSimpleUserPhone(simplePhone);

            Country country = UserData.getInstance(mContext).getSelectedCountry();
            String msisdn = country.getPhoneCode() + phoneNumber;
            String countryID = country.getCode();

            mView.showLoading(mContext.getString(R.string.label_please_wait));
            mInteractor.validatePhone(this, msisdn, countryID);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void saveUserGeneralData(String phone, int consumerId)
    {
        try
        {
            Country country = UserData.getInstance(mContext).getSelectedCountry();
            UserData.getInstance(mContext).saveUserPhoneInfo(country.getCode(),
                    country.getCountryCode(), country.getName(), country.getPhoneCode(), phone, consumerId);
        }
        catch (Exception ex) { ex.printStackTrace();    }
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

    @Override
    public void onPhoneValSuccess(int codeStatus, RegisterClientResponse response)
    {

        mView.hideLoading();
        if(codeStatus == 201)
        {
            DialogModel dialog = new DialogModel();
            dialog.setTitle(mContext.getString(R.string.title_await_please));
            dialog.setContent(response.getSecondsRemaining());
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericMessage(dialog);
        }
        else
        {
            mView.navigateTokenInput(response, "");
        }
    }

    @Override
    public void onPhoneValError(int codeResponse, Throwable throwable, String rawResponse)
    {
        try
        {
            mView.hideLoading();

            JsonObject jsonObject =  (JsonObject) new JsonParser().parse(rawResponse);

            if(jsonObject.has("existsPhone"))
            {
                if(jsonObject.get("existsPhone").getAsBoolean())
                {
                    DialogModel model = new DialogModel();
                    model.setTitle(mContext.getString(R.string.title_phone_already_exists));
                    model.setContent(mContext.getString(R.string.label_phone_already_exists));
                    model.setAcceptButton(mContext.getString(R.string.button_accept));
                    mView.showGenericMessage(model);
                }
            }
            else
            {
                processErrorMessage(codeResponse, throwable, rawResponse);
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onSmsCodeValidated(JsonObject rawResponse)
    {

    }

    @Override
    public void onSmsCodeError(int codeStatus, String rawResponse, Throwable throwable)
    {

    }

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        DialogModel errorResponse = new DialogModel();

        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setContent(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.showGenericMessage(errorResponse);

                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setContent(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.showGenericMessage(errorResponse);
                }
            }
            else
            {

                if(pCodeStatus == 426)
                {
                    DialogModel dialog = new DialogModel();
                    dialog.setTitle(mContext.getString(R.string.title_update_required));
                    dialog.setContent(String.format(mContext.getString(R.string.content_update_required), pRequiredVersion));
                    dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                    mView.showGenericMessage(dialog);
                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setContent(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.showGenericMessage(errorResponse);
                }

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
