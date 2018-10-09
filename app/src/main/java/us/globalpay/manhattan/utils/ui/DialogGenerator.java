package us.globalpay.manhattan.utils.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 28/09/2018.
 */
public class DialogGenerator
{
    private static final String TAG = DialogGenerator.class.getSimpleName();

    public static void showDialog(Context context, DialogModel content, DialogInterface.OnClickListener clickListener)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        builder.setTitle(content.getTitle());
        builder.setMessage(content.getContent());
        builder.setPositiveButton(content.getAcceptButton(), clickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static ProgressDialog showProgressDialog(Context context, String message, boolean isCancelable)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.show();
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);

        return dialog;
    }

    public static void dismissProgressDialog(ProgressDialog dialog)
    {
        try
        {
            if(dialog != null && dialog.isShowing())
            {
                dialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
