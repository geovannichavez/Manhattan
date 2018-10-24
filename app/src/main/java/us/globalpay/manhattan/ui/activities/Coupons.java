package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.presenters.CouponsPresenter;
import us.globalpay.manhattan.ui.adapters.CouponsAdapter;
import us.globalpay.manhattan.utils.interfaces.IActionResult;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.utils.interfaces.RecyclerClickListener;
import us.globalpay.manhattan.utils.ui.RecyclerTouchListener;
import us.globalpay.manhattan.views.CouponsView;

public class Coupons extends AppCompatActivity implements CouponsView
{
    private static final String TAG = Coupons.class.getSimpleName();

    ImageView ivBackground;
    ImageView btnBack;
    ImageView btnFavorites;
    ImageView btnCoupons;
    ImageView btnRedeemed;
    ImageView spLocation;
    TextView lblFavorites;
    TextView lblCoupons;
    TextView lblRedeemed;
    TextView tvSpinnerLocation;

    RecyclerView gvCoupons;
    ProgressDialog mProgressDialog;

    CouponsAdapter mAdapter;

    private CouponsPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        gvCoupons = (RecyclerView) findViewById(R.id.gvCoupons);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnFavorites = (ImageView) findViewById(R.id.btnFavorites);
        btnCoupons = (ImageView) findViewById(R.id.btnCoupons);
        btnRedeemed = (ImageView) findViewById(R.id.btnRedeemed);
        spLocation = (ImageView) findViewById(R.id.spLocation);
        lblFavorites = (TextView) findViewById(R.id.lblFavorites);
        lblCoupons = (TextView) findViewById(R.id.lblCoupons);
        lblRedeemed = (TextView) findViewById(R.id.lblRedeemed);
        tvSpinnerLocation = (TextView) findViewById(R.id.tvSpinnerLocation);

        mPresenter = new CouponsPresenter(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveCoupons(1, 1); //TODO: Cambiar tienda default

    }

    @Override
    public void initialize(String storeName)
    {
        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);

        //Toolbar
        View toolbar = findViewById(R.id.toolbarCoupons);
        ImageView ivTitleIcon = toolbar.findViewById(R.id.ivTitleIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.lblTitle);
        ivTitleIcon.setImageResource(R.drawable.ic_coupons_icon);
        title.setText(getString(R.string.title_activity_coupons));
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.backButton(Coupons.this, v);
                navigateBack();
            }
        });

        btnFavorites.setOnClickListener(favoritesListener);
        btnCoupons.setOnClickListener(couponsListener);
        btnRedeemed.setOnClickListener(redeemedListener);
        spLocation.setOnClickListener(spinnerListener);

        tvSpinnerLocation.setText(storeName);

        // Set initial button set
        btnFavorites.setImageResource(R.drawable.btn_coupon_bar_left_off);
        btnCoupons.setImageResource(R.drawable.btn_coupon_bar_middle_on);
        btnRedeemed.setImageResource(R.drawable.btn_coupon_bar_right_off);
        lblFavorites.setTextColor(getResources().getColor(R.color.color_blue_dark));
        lblCoupons.setTextColor(getResources().getColor(R.color.color_white));
        lblRedeemed.setTextColor(getResources().getColor(R.color.color_blue_dark));

    }

    @Override
    public void renderCoupons(final List<Cupon> couponsList)
    {
        try
        {
            mAdapter = new CouponsAdapter(this, couponsList, false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);

            gvCoupons.setLayoutManager(layoutManager);
            gvCoupons.setItemAnimator(new DefaultItemAnimator());
            gvCoupons.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

            gvCoupons.addOnItemTouchListener(new RecyclerTouchListener(this, gvCoupons, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    try
                    {
                        Cupon cupon = couponsList.get(position);
                        mPresenter.selectCouponDetails(cupon);
                        Intent details = new Intent(Coupons.this, CouponDetail.class);
                        startActivity(details);
                        finish();
                    }
                    catch (IndexOutOfBoundsException ex) { Log.e(TAG, "index: " + String.valueOf(position));}
                }
            }));
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showGenericDialog(DialogModel dialog, DialogInterface.OnClickListener listener)
    {
        DialogGenerator.showDialog(this, dialog, listener);
    }

    @Override
    public void showLoadingDialog(String message)
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void navigateDetails()
    {
        try
        {

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showSelectableDialog(HashMap<String, ?> arrayMap, IActionResult actionResult)
    {
        DialogGenerator.showArrayDialog(this, arrayMap, actionResult);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener favoritesListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.shadowButton(v);

            // Buttons color
            btnFavorites.setImageResource(R.drawable.btn_coupon_bar_left_on);
            btnCoupons.setImageResource(R.drawable.btn_coupon_bar_middle_off);
            btnRedeemed.setImageResource(R.drawable.btn_coupon_bar_right_off);

            //Text color
            lblFavorites.setTextColor(getResources().getColor(R.color.color_white));
            lblCoupons.setTextColor(getResources().getColor(R.color.color_blue_dark));
            lblRedeemed.setTextColor(getResources().getColor(R.color.color_blue_dark));

            //Request
            mPresenter.retrieveCoupons(0, 1); //TODO: Poner tienda

        }
    };

    private View.OnClickListener couponsListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            ButtonAnimator.shadowButton(v);

            // Buttons color
            btnFavorites.setImageResource(R.drawable.btn_coupon_bar_left_off);
            btnCoupons.setImageResource(R.drawable.btn_coupon_bar_middle_on);
            btnRedeemed.setImageResource(R.drawable.btn_coupon_bar_right_off);

            //Text color
            lblFavorites.setTextColor(getResources().getColor(R.color.color_blue_dark));
            lblCoupons.setTextColor(getResources().getColor(R.color.color_white));
            lblRedeemed.setTextColor(getResources().getColor(R.color.color_blue_dark));

            //Request
            mPresenter.retrieveCoupons(1, 1); //TODO: Poner tienda
        }
    };

    private View.OnClickListener redeemedListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            ButtonAnimator.shadowButton(v);

            // Buttons color
            btnFavorites.setImageResource(R.drawable.btn_coupon_bar_left_off);
            btnCoupons.setImageResource(R.drawable.btn_coupon_bar_middle_off);
            btnRedeemed.setImageResource(R.drawable.btn_coupon_bar_right_on);

            //Text color
            lblFavorites.setTextColor(getResources().getColor(R.color.color_blue_dark));
            lblCoupons.setTextColor(getResources().getColor(R.color.color_blue_dark));
            lblRedeemed.setTextColor(getResources().getColor(R.color.color_white));

            //Request
            mPresenter.retrieveCoupons(2, 1); //TODO: Poner tienda
        }
    };

    private View.OnClickListener spinnerListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.floatingButton(Coupons.this, v);
            mPresenter.presentStores();
        }
    };

    /*
    *
    *
    *
    *   OTHER METHODS
    *
    *
    *
    * */

    private void navigateBack()
    {
        try
        {
            Intent back = new Intent(this, Main.class);
            NavFlagsUtil.addFlags(back);
            startActivity(back);
            finish();
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
    }
}
