package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.presenters.BrandCouponsPresenter;
import us.globalpay.manhattan.ui.adapters.CouponsAdapter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.utils.ui.RecyclerClickListener;
import us.globalpay.manhattan.utils.ui.RecyclerTouchListener;
import us.globalpay.manhattan.views.BrandsCouponsView;

public class BrandsCoupons extends AppCompatActivity implements BrandsCouponsView
{
    private static final String TAG = BrandsCoupons.class.getSimpleName();

    private ImageView btnBack;
    private ImageView ivBrandPhoto;
    private ImageView ivBackground;
    private ImageView ivBrandLogo;
    private ImageView ivCategoryIcon;
    private ImageView spLocation;
    private ImageView ivStores;
    private ImageView ivIcon;
    private TextView lblCoupons;
    private TextView tvStores;
    private TextView tvSpinnerText;
    private RecyclerView gvBrandCoupons;
    private ProgressDialog mProgressDialog;

    private CouponsAdapter mAdapter;
    private BrandCouponsPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands_coupons);

        ivBrandPhoto = (ImageView) findViewById(R.id.ivBrandPhoto);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ivBrandLogo = (ImageView) findViewById(R.id.ivBrandLogo);
        ivCategoryIcon = (ImageView) findViewById(R.id.ivCategoryIcon);
        spLocation = (ImageView) findViewById(R.id.spLocation);
        ivStores = (ImageView) findViewById(R.id.ivStores);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        lblCoupons = (TextView) findViewById(R.id.lblCoupons);
        tvStores = (TextView) findViewById(R.id.tvStores);
        tvSpinnerText = (TextView) findViewById(R.id.tvSpinnerText);
        gvBrandCoupons = (RecyclerView) findViewById(R.id.gvBrandCoupons);

        int brandID = getIntent().getIntExtra(Constants.INTENT_BUNDLE_BRAND_ID, 0);
        String categoryIcon = getIntent().getStringExtra(Constants.INTENT_BUNDLE_CATEGORY_ICON);

        mPresenter = new BrandCouponsPresenter(this, this, this);
        mPresenter.initialize(categoryIcon);
        mPresenter.retrieveCoupons(brandID);
    }

    @Override
    public void initialize()
    {
        View toolbar = findViewById(R.id.toolbarBrandCoupons);
        ImageView ivTitleIcon = toolbar.findViewById(R.id.ivTitleIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.lblTitle);
        ivTitleIcon.setImageResource(R.drawable.ic_brands_icon);
        title.setText(getString(R.string.title_activity_brands));

        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);

        btnBack = toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(backListener);

        spLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(BrandsCoupons.this, v);
            }
        });
    }

    @Override
    public void loadBrand(Brand brand, String icon)
    {
        try
        {
            Glide.with(this).load(brand.getUrlBackground()).into(ivBrandPhoto);
            Glide.with(this).load(brand.getLogoColor()).into(ivBrandLogo);

            if(!TextUtils.isEmpty(icon))
                Glide.with(this).load(icon).into(ivCategoryIcon);
            else
                ivCategoryIcon.setVisibility(View.INVISIBLE);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void renderCoupons(final List<Cupon> couponsList)
    {
        try
        {
            mAdapter = new CouponsAdapter(this, couponsList, true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);

            gvBrandCoupons.setLayoutManager(layoutManager);
            gvBrandCoupons.setItemAnimator(new DefaultItemAnimator());
            gvBrandCoupons.getRecycledViewPool().setMaxRecycledViews(1, 0);
            gvBrandCoupons.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

            gvBrandCoupons.addOnItemTouchListener(new RecyclerTouchListener(this, gvBrandCoupons, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    Cupon cupon = couponsList.get(position);

                    if(cupon.isPurchasable())
                    {
                        if(!cupon.isUnlocked())
                            mPresenter.couponActions(cupon);
                        else
                        {
                            mPresenter.selectCouponDetails(cupon);
                            navigateDetails(cupon.getCuponID(), true);
                        }

                    }
                    else
                    {
                        navigateDetails(cupon.getCuponID(), true);
                    }

                }
            }));
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void navigateDetails(int cuponID, boolean fromBrandCoupon)
    {




        Intent details = new Intent(BrandsCoupons.this, CouponDetail.class);
        details.putExtra(Constants.INTENT_BUNDLE_COUPON_ID, Integer.valueOf(cuponID));
        details.putExtra(Constants.INTENT_BACKSTACK_BRAND_COUPON, fromBrandCoupon);
        startActivity(details);
        finish();
    }

    @Override
    public void showLoadingDialog(String message, boolean isCancelable)
    {
        mProgressDialog = DialogGenerator.showProgressDialog(this, message, isCancelable);
    }

    @Override
    public void hideLoadingDialog()
    {
        DialogGenerator.dismissProgressDialog(mProgressDialog);
    }

    @Override
    public void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener)
    {
        DialogGenerator.showDialog(this, model, clickListener);
    }

    @Override
    public void showCouponDialog(Cupon coupon, View.OnClickListener clickListener)
    {
        DialogGenerator.showCouponDialog(this, coupon, clickListener);
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
            ButtonAnimator.backButton(BrandsCoupons.this, v);
            navigateBack();
        }
    };


    /*
    *
    *
    *
    * OTHER METHODS
    *
    *
    *
    * */

    private void navigateBack()
    {
        try
        {
            Intent main = new Intent(BrandsCoupons.this, Brands.class);
            NavFlagsUtil.addFlags(main);
            startActivity(main);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
