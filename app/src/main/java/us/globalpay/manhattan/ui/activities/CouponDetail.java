package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.presenters.CouponDetailPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.views.CouponDetailView;

public class CouponDetail extends AppCompatActivity implements CouponDetailView
{
    private static final String TAG = CouponDetail.class.getSimpleName();

    private ImageView btnBack;
    private ImageView ivExchangeCode;
    private ImageView ivCategoryIcon;
    private ImageView ivBrandPhoto;
    private ImageView ivBackground;
    private ImageView ivBrandLogo;
    private CheckBox swFavorite;
    private TextView tvTitle;
    private TextView tvSub;
    private TextView tvDescription;
    private TextView tvExchangeMethod;
    private TextView tvAvailability;
    private ProgressDialog mProgressDialog;

    private boolean mBrandCoupon;

    private CouponDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        ivExchangeCode = (ImageView) findViewById(R.id.ivExchangeCode);
        ivCategoryIcon = (ImageView) findViewById(R.id.ivCategoryIcon);
        ivBrandPhoto = (ImageView) findViewById(R.id.ivBrandPhoto);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ivBrandLogo = (ImageView) findViewById(R.id.ivBrandLogo);
        swFavorite = (CheckBox) findViewById(R.id.swFavorite);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvExchangeMethod = (TextView) findViewById(R.id.tvExchangeMethod);
        tvAvailability = (TextView) findViewById(R.id.tvAvailability);
        tvSub = (TextView) findViewById(R.id.tvSub);

        mBrandCoupon = false;
        mBrandCoupon = getIntent().getBooleanExtra(Constants.INTENT_BACKSTACK_BRAND_COUPON, false);

        mPresenter = new CouponDetailPresenter(this, this, this);
        mPresenter.initialize();
        mPresenter.loadDetails();

    }

    @Override
    public void initalize()
    {
        View toolbar = findViewById(R.id.toolbarCoupDetail);
        ImageView ivTitleIcon = toolbar.findViewById(R.id.ivTitleIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.lblTitle);
        ivTitleIcon.setImageResource(R.drawable.ic_coupons_icon);
        title.setText(getString(R.string.title_activity_coupons));

        Glide.with(this).load(R.drawable.bg_white_gray).into(ivBackground);

        btnBack = toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(backListener);

    }

    @Override
    public void loadDetails(final Bundle details)
    {
        try
        {
            Glide.with(this).load(details.getString(Constants.BUNDLE_COUPON_URL_CATEGORY_ICON)).into(ivCategoryIcon);
            Glide.with(this).load(details.getString(Constants.BUNDLE_COUPON_URL_BACKGROUND_BRAND)).into(ivBrandPhoto);
            Glide.with(this).load(details.getString(Constants.BUNDLE_COUPON_URL_LOGO_DESC)).into(ivBrandLogo);

            tvTitle.setText(details.getString(Constants.BUNDLE_COUPON_TITLE));
            tvSub.setText(details.getString(Constants.BUNDLE_COUPON_EXCHANGE_PLACE));
            tvDescription.setText(details.getString(Constants.BUNDLE_COUPON_DESCRIPTION));
            tvExchangeMethod.setText(details.getString(Constants.BUNDLE_COUPON_EXCHANGE_METHOD));

            swFavorite.setChecked(details.getBoolean(Constants.BUNDLE_COUPON_FAVORITE));

            swFavorite.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.floatingButton(CouponDetail.this, v);
                    mPresenter.markAsFavorite(details.getInt(Constants.BUNDLE_PIN_ID), details.getBoolean(Constants.BUNDLE_COUPON_FAVORITE));
                }
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String message, boolean isCancelable)
    {
        mProgressDialog = DialogGenerator.showProgressDialog(CouponDetail.this, message, isCancelable);
    }

    @Override
    public void hideLoadingDialog()
    {
        DialogGenerator.dismissProgressDialog(mProgressDialog);
    }

    @Override
    public void showDialog(DialogModel dialog, DialogInterface.OnClickListener clickListener)
    {
        DialogGenerator.showDialog(CouponDetail.this, dialog, clickListener);
    }

    @Override
    public void toggleFavorite(boolean isFavorite)
    {
        swFavorite.setChecked(isFavorite);
    }

    @Override
    public void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.backButton(CouponDetail.this, v);
            navigateBack();
        }
    };


    /*
    *
    *
    *
    *   OTHER METHODS
    *
    *
    * */

    private void navigateBack()
    {
        try
        {
            Intent main = null;
            if(mBrandCoupon)
                main = new Intent(CouponDetail.this, BrandsCoupons.class);
            else
                main = new Intent(CouponDetail.this, Coupons.class);

            NavFlagsUtil.addFlags(main);
            startActivity(main);
            finish();
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
    }
}
