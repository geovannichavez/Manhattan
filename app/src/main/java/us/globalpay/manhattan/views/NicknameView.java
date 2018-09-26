package us.globalpay.manhattan.views;

import android.content.Intent;

import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface NicknameView
{
    void initializeViews();
    void showLoading(String label);
    void hideLoading();
    void showGenericMessage(DialogModel dialog);
    void navigateNext(Intent nextActivity);
    void showGenericToast(String content);
    void createSnackbar(String content);
}
