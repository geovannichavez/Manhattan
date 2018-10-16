package us.globalpay.manhattan.utils.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.utils.Constants;

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

    public static void showCouponDialog(final Context context, Cupon coupon, View.OnClickListener acceptListener)
    {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_coupon_dialog, null);

        LinearLayout layer4 = (LinearLayout) dialogView.findViewById(R.id.layer4);
        LinearLayout layer5 = (LinearLayout) dialogView.findViewById(R.id.layer5);
        ImageView btnCouponDialogClose = (ImageView) dialogView.findViewById(R.id.btnCouponDialogClose);
        ImageView btnCouponDialogAccept = (ImageView) dialogView.findViewById(R.id.btnCouponDialogAccept);
        ImageView ivCouponDialogLogo = (ImageView) dialogView.findViewById(R.id.ivCouponDialogLogo);
        ImageView ivCouponDialogExchangeType = (ImageView) dialogView.findViewById(R.id.ivCouponDialogExchangeType);
        ImageView ivCouponDialogExchangeTypeCoins = (ImageView) dialogView.findViewById(R.id.ivCouponDialogExchangeTypeCoins);
        TextView tvCouponDesc = (TextView) dialogView.findViewById(R.id.tvCouponDesc);
        TextView tvExchangeType = (TextView) dialogView.findViewById(R.id.tvExchangeType);
        TextView tvExchangePrice = (TextView) dialogView.findViewById(R.id.tvExchangePrice);


        tvCouponDesc.setText(coupon.getDescription());
        Glide.with(context).load(coupon.getUrlLogo()).into(ivCouponDialogLogo);

        if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_1_WILDCARD)
        {
            layer4.setVisibility(View.VISIBLE);
            layer5.setVisibility(View.INVISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_method_wildcard));
            Glide.with(context).load(coupon.getUrlLogo()).into(ivCouponDialogExchangeType);
        }
        if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_2_SCANNING)
        {
            layer4.setVisibility(View.VISIBLE);
            layer5.setVisibility(View.INVISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_method_scan));
            Glide.with(context).load(coupon.getUrlLogo()).into(ivCouponDialogExchangeType);
        }
        if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_3_TREASURE_HUNT)
        {
            layer4.setVisibility(View.VISIBLE);
            layer5.setVisibility(View.INVISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_method_scan));
            Glide.with(context).load(coupon.getUrlLogo()).into(ivCouponDialogExchangeType);
        }
        else if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_4_COINS_EXCHANGE)
        {
            layer4.setVisibility(View.VISIBLE);
            layer5.setVisibility(View.INVISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_20_coins));
            Glide.with(context).load(R.drawable.ic_exchange_type_gift_large).into(ivCouponDialogExchangeType);
        }
        else if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_5_SHOPPING)
        {
            layer4.setVisibility(View.INVISIBLE);
            layer5.setVisibility(View.VISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_method_shopping));
            tvExchangePrice.setText(String.valueOf(coupon.getPrice()));
            Glide.with(context).load(R.drawable.ic_exchange_type_coins_large).into(ivCouponDialogExchangeType);
        }
        else if(coupon.getMethodID() == Constants.EXCHANGE_METHOD_6_CHEST)
        {
            layer4.setVisibility(View.VISIBLE);
            layer5.setVisibility(View.INVISIBLE);
            tvExchangeType.setText(context.getString(R.string.label_exchange_method_chest));
            Glide.with(context).load(R.drawable.ic_exchange_type_gift_large).into(ivCouponDialogExchangeType);
        }


        dialog = builder.setView(dialogView).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnCouponDialogAccept.setOnClickListener(acceptListener);

        btnCouponDialogClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(context, v);
                dialog.dismiss();
            }
        });





    }

    public static void showImageDialog(final Context context, @Nullable String imgUrl, int imgResource, DialogModel content, View.OnClickListener clickListener)
    {

        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_image_dialog, null);

        ImageView ivImgDialogBackg = (ImageView) dialogView.findViewById(R.id.ivImgDialogBackg);
        ImageView btnImgDialogClose = (ImageView) dialogView.findViewById(R.id.btnImgDialogClose);
        ImageView btnImgDialogAccept = (ImageView) dialogView.findViewById(R.id.btnImgDialogAccept);
        ImageView ivImgDialogImage = (ImageView) dialogView.findViewById(R.id.ivImgDialogImage);
        TextView tvImgDialogText = (TextView) dialogView.findViewById(R.id.tvImgDialogText);
        TextView tvImgDialogButtonText = (TextView) dialogView.findViewById(R.id.tvImgDialogButtonText);
        TextView tvImgDialogTitle = (TextView) dialogView.findViewById(R.id.tvImgDialogTitle);

        tvImgDialogTitle.setText(content.getTitle());
        tvImgDialogText.setText(content.getContent());
        tvImgDialogButtonText.setText(content.getAcceptButton());

        if(TextUtils.isEmpty(imgUrl))
            Glide.with(context).load(imgResource).into(ivImgDialogImage);
        else
            Glide.with(context).load(imgUrl).into(ivImgDialogImage);


        dialog = builder.setView(dialogView).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnImgDialogAccept.setOnClickListener(clickListener);

        btnImgDialogClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(context, v);
                dialog.dismiss();
            }
        });


    }
}
