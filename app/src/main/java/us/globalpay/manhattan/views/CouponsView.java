package us.globalpay.manhattan.views;

import android.content.DialogInterface;

import java.util.List;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public interface CouponsView
{
    void initialize();
    void renderCoupons(List<Cupon> couponsList);
    void showGenericDialog(DialogModel dialog, DialogInterface.OnClickListener listener);
    void showLoadingDialog(String message);
    void hideLoadingDialog();
}
