package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.onesignal.OneSignal;

import java.net.SocketTimeoutException;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.PhoneValidationInteractor;
import us.globalpay.manhattan.interactors.PhoneValidationListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.RegisterClientResponse;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
import us.globalpay.manhattan.models.api.SmsValidationReqBody;
import us.globalpay.manhattan.presenters.interfaces.ISmsCodeValidationPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.SmsCodeInputView;

/**
 * Created by Josué Chávez on 02/10/2018.
 */
public class SmsCodeValidationPresenter implements ISmsCodeValidationPresenter, PhoneValidationListener
{
    private static final String TAG = SmsCodeValidationPresenter.class.getSimpleName();

    private Context mContext;
    private SmsCodeInputView mView;
    private PhoneValidationInteractor mInteractor;
    private Gson mGson;

    public SmsCodeValidationPresenter(Context context, AppCompatActivity activity, SmsCodeInputView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new PhoneValidationInteractor(mContext);
        mGson = new Gson();
    }

    @Override
    public void initialize()
    {
        mView.initialViewsState();
        mView.setCodeSentLabelText(UserData.getInstance(mContext).getUserPhone());
    }

    @Override
    public void validateSmsCode(String code)
    {
        mView.showLoading(mContext.getString(R.string.label_please_wait));
        SmsValidationReqBody request = new SmsValidationReqBody();
        request.setToken(code);
        mInteractor.validateSmsCode(request, this);
    }

    @Override
    public void onRetrieveSuccess(Countries response)
    {

    }

    @Override
    public void onError(int codeResponse, Throwable throwable, String rawResponse)
    {

    }

    @Override
    public void onPhoneValSuccess(int codeStatus, RegisterClientResponse response)
    {

    }

    @Override
    public void onPhoneValError(int codeResponse, Throwable throwable, String rawResponse)
    {

    }

    @Override
    public void onSmsCodeValidated(JsonObject rawResponse)
    {
        try
        {
            mView.vibrateOnSuccess();
            mView.dismissLoading();

            //OneSignal ID tag
            OneSignal.sendTag(Constants.ONESIGNAL_USER_TAG_KEY, UserData.getInstance(mContext).getUserPhone());

            UserData.getInstance(mContext).hasSelectedCountry(true);
            UserData.getInstance(mContext).hasConfirmedPhone(true);

            //If nickname comes empty, is a new user registration. Must complete profile
            if(TextUtils.isEmpty(UserData.getInstance(mContext).getNickname()))
            {
                mView.navigateNickname();
            }
            else
            {
                UserData.getInstance(mContext).hasAuthenticated(true);
                UserData.getInstance(mContext).hasSetNickname(true);

                mView.navigateHome(UserData.getInstance(mContext).is3DCompatibleDevice());
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onSmsCodeError(int codeStatus, String rawResponse, Throwable throwable)
    {
        mView.dismissLoading();
        processErrorMessage(codeStatus, throwable);
    }


    /*
    *
    *
    *   OTHER METHODS
    *
    * */

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable)
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
                    mView.cleanFields();
                    mView.showErrorMessage(errorResponse);

                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setContent(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.cleanFields();
                    mView.showErrorMessage(errorResponse);
                }
            }
            else if(pCodeStatus != 0)
            {
                String Titulo;
                String Linea1;
                String Button;

                switch (pCodeStatus)
                {
                    case 403:
                        Titulo = mContext.getString(R.string.error_title_incorrect_token);
                        Linea1 = mContext.getString(R.string.error_label_incorrect_token);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setContent(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.cleanFields();
                        mView.showErrorMessage(errorResponse);
                        break;
                    case 500:
                        Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                        Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setContent(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.cleanFields();
                        mView.showErrorMessage(errorResponse);
                        break;
                    default:
                        Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                        Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setContent(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.showErrorMessage(errorResponse);
                        break;
                }
            }
            else
            {
                String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                String Button = mContext.getString(R.string.button_accept);

                errorResponse.setTitle(Titulo);
                errorResponse.setContent(Linea1);
                errorResponse.setAcceptButton(Button);
                mView.cleanFields();
                mView.showErrorMessage(errorResponse);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
