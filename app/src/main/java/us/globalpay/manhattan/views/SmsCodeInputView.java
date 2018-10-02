package us.globalpay.manhattan.views;

import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 02/10/2018.
 */
public interface SmsCodeInputView
{
    void initialViewsState();
    void showLoading(String content);
    void dismissLoading();
    void showErrorMessage(DialogModel dialogModel);
    void navigateHome(boolean p3DCompatible);
    void vibrateOnSuccess();
    void cleanFields();
    void setCallcenterContactText();
    void setCodeSentLabelText(String phoneNumber);
    void navigatePhoneValidation(boolean retypePhone);
    void navigateNickname();
}
