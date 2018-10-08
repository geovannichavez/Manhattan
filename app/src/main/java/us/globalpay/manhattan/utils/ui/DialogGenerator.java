package us.globalpay.manhattan.utils.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 28/09/2018.
 */
public class DialogGenerator
{
    public static void showDialog(Context context, DialogModel content, DialogInterface.OnClickListener clickListener)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        builder.setTitle(content.getTitle());
        builder.setMessage(content.getContent());
        builder.setPositiveButton(content.getAcceptButton(), clickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
