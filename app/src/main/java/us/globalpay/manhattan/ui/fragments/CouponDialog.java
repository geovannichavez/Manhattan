package us.globalpay.manhattan.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import us.globalpay.manhattan.R;

/**
 * Created by Josué Chávez on 12/10/2018.
 */
public class CouponDialog extends DialogFragment
{
    private static final String TAG = CouponDialog.class.getSimpleName();

    LinearLayout layer4;
    LinearLayout layer5;
    ImageView btnCouponDialogClose;
    ImageView btnCouponDialogAccept;
    ImageView ivCouponDialogLogo;
    ImageView ivCouponDialogExchangeType;
    ImageView ivCouponDialogExchangeTypeCoins;
    TextView tvCouponDesc;
    TextView tvExchangeType;
    TextView tvExchangePrice;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanteState)
    {
        View view = inflater.inflate(R.layout.custom_coupon_dialog, container, false);

        layer4 = (LinearLayout) view.findViewById(R.id.layer4);
        layer5 = (LinearLayout) view.findViewById(R.id.layer5);
        btnCouponDialogClose = (ImageView) view.findViewById(R.id.btnCouponDialogClose);
        btnCouponDialogAccept = (ImageView) view.findViewById(R.id.btnCouponDialogAccept);
        ivCouponDialogLogo = (ImageView) view.findViewById(R.id.ivCouponDialogLogo);
        ivCouponDialogExchangeType = (ImageView) view.findViewById(R.id.ivCouponDialogExchangeType);
        ivCouponDialogExchangeTypeCoins = (ImageView) view.findViewById(R.id.ivCouponDialogExchangeTypeCoins);
        tvCouponDesc = (TextView) view.findViewById(R.id.tvCouponDesc);
        tvExchangeType = (TextView) view.findViewById(R.id.tvExchangeType);
        tvExchangePrice = (TextView) view.findViewById(R.id.tvExchangePrice);

        return view;
    }

    public void dimissDialog()
    {

    }
}
