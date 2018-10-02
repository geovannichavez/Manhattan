package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import java.net.SocketTimeoutException;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.NicknameInteractor;
import us.globalpay.manhattan.interactors.NicknameListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
import us.globalpay.manhattan.presenters.interfaces.INicknamePresenter;
import us.globalpay.manhattan.ui.activities.Main;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.NicknameView;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class NicknamePresenter implements INicknamePresenter, NicknameListener
{
    private static final String TAG = NicknamePresenter.class.getSimpleName();

    private Context mContext;
    private NicknameView mView;
    private NicknameInteractor mInteractor;
    private AppCompatActivity mActivity;

    public NicknamePresenter(Context context, AppCompatActivity activity, NicknameView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new NicknameInteractor(mContext);
        this.mActivity = activity;
    }

    @Override
    public void initialize()
    {
        UserData.getInstance(mContext).hasSetNickname(false);
        mView.initializeViews();
    }

    @Override
    public void sendNickname(String nickname)
    {
        mView.showLoading(mContext.getString(R.string.label_please_wait));
        UserData.getInstance(mContext).hasSetNickname(false);
        mInteractor.validateNickname(nickname, this);
        UserData.getInstance(mContext).saveNickname(nickname.toLowerCase());
    }

    @Override
    public void onValidateNicknameSuccess(SimpleResultResponse pResultResponse, String choosenNickname)
    {
        mView.hideLoading();

        //Saves user identifier
        Crashlytics.setUserIdentifier(choosenNickname);

        UserData.getInstance(mContext).hasSetNickname(true);

        //UserData.getInstance(mContext).setWelcomeChestAvailable(true);
        Intent next = new Intent(mActivity, Main.class);
        NavFlagsUtil.addFlags(next);
        mView.navigateNext(next);
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable, String rawResponse)
    {
        mView.hideLoading();
        handleError(pCodeStatus, pThrowable, rawResponse);
        UserData.getInstance(mContext).hasSetNickname(false);
        UserData.getInstance(mContext).deleteNickname();
    }

    private void handleError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        DialogModel errorResponse = new DialogModel();

        String dialogTitle;
        String dialogMessage;
        String dialogButton;
        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_label_something_went_wrong);
                    dialogButton = mContext.getString(R.string.button_accept);
                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setContent(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_label_something_went_wrong);
                    dialogButton = mContext.getString(R.string.button_accept);
                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setContent(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
            }
            else
            {
                if(pCodeStatus == 406)
                {
                    errorResponse.setTitle(mContext.getString(R.string.title_dialog_nickname_already_exists));
                    errorResponse.setContent(mContext.getString(R.string.validation_nickname_already_exists));
                    errorResponse.setAcceptButton(mContext.getString(R.string.button_accept));
                    this.mView.showGenericMessage(errorResponse);
                }
                else if(pCodeStatus == 403)
                {
                    errorResponse.setTitle(mContext.getString(R.string.title_dialog_invalid_nickname));
                    errorResponse.setContent(mContext.getString(R.string.label_dialog_invalid_nickname));
                    errorResponse.setAcceptButton(mContext.getString(R.string.button_accept));
                    this.mView.showGenericMessage(errorResponse);
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_label_something_went_wrong);
                    dialogButton = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setContent(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
