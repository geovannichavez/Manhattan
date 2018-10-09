package us.globalpay.manhattan.views;

import android.content.DialogInterface;

import java.util.List;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Promo;
import us.globalpay.manhattan.models.api.PromosResponse;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public interface PromosView
{
    void initialize();
    void renderPromos(List<Promo> response);
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener);
}
