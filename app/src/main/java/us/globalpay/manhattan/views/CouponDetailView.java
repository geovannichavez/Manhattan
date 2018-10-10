package us.globalpay.manhattan.views;

import android.content.DialogInterface;
import android.os.Bundle;

import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public interface CouponDetailView
{
    void initalize();
    void loadDetails(Bundle details);
    void showLoadingDialog(String message, boolean isCancelable);
    void hideLoadingDialog();
    void showDialog(DialogModel dialog, DialogInterface.OnClickListener clickListener);
}
