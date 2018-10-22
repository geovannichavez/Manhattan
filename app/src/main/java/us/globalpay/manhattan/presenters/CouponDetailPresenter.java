package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.security.auth.login.LoginException;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.CouponsInteractor;
import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.models.api.FavoriteCouponReq;
import us.globalpay.manhattan.models.api.FavoriteCouponResponse;
import us.globalpay.manhattan.presenters.interfaces.ICouponDetailPresenter;
import us.globalpay.manhattan.ui.activities.Coupons;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.CouponDetailView;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public class CouponDetailPresenter implements ICouponDetailPresenter, CouponsListener
{
    private static final String TAG = CouponDetailPresenter.class.getSimpleName();

    private Context mContext;
    private CouponDetailView mView;
    private Gson mGson;
    private AppCompatActivity mActivity;
    private CouponsInteractor mInteractor;
    private boolean mFavorite;

    public CouponDetailPresenter(Context context, AppCompatActivity activity, CouponDetailView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mGson = new Gson();
        this.mActivity = activity;
        this.mInteractor = new CouponsInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initalize();
    }

    @Override
    public void loadDetails()
    {
        try
        {
            Cupon selectedCoupon = mGson.fromJson(UserData.getInstance(mContext).getDetailedCoupon(), Cupon.class);

            if(selectedCoupon != null)
            {
                Bundle details = new Bundle();
                details.putInt(Constants.BUNDLE_COUPON_ID, selectedCoupon.getCuponID());
                details.putString(Constants.BUNDLE_COUPON_TITLE, selectedCoupon.getTitle());
                details.putString(Constants.BUNDLE_COUPON_DESCRIPTION, selectedCoupon.getDescription());
                details.putString(Constants.BUNDLE_COUPON_URL_BACKGROUND_BRAND, selectedCoupon.getUrlBackgroundBrand());
                details.putString(Constants.BUNDLE_COUPON_URL_LOGO_DESC, selectedCoupon.getLogoColor());
                details.putString(Constants.BUNDLE_COUPON_URL_CATEGORY_ICON, selectedCoupon.getUrlImageCategory());
                details.putString(Constants.BUNDLE_COUPON_BRAND_NAME, selectedCoupon.getBrandName());
                details.putString(Constants.BUNDLE_COUPON_CODE, selectedCoupon.getCode());
                details.putInt(Constants.BUNDLE_COUPON_PIN_LEVEL, selectedCoupon.getLevel());
                details.putBoolean(Constants.BUNDLE_COUPON_PURCHASABLE, selectedCoupon.isPurchasable());
                details.putBoolean(Constants.BUNDLE_COUPON_FAVORITE, selectedCoupon.isFavorite());

                mView.loadDetails(details);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
            final DialogModel model = new DialogModel();
            model.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            model.setContent(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            model.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showDialog(model, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent back = new Intent(mActivity, Coupons.class);
                    NavFlagsUtil.addFlags(back);
                    mContext.startActivity(back);
                    mActivity.finish();
                }
            });
        }
    }

    @Override
    public void markAsFavorite(int CouponID, boolean isFavorite)
    {
        try
        {
            int favoriteValue = 0;

            mFavorite = isFavorite;

            FavoriteCouponReq request = new FavoriteCouponReq();
            request.setPinID(CouponID);

            if(isFavorite)
                favoriteValue = 0; // 0 No Favorite
            else
                favoriteValue = 1; // 1 Favorite

            //Switch value favorite
            isFavorite = !isFavorite;

            Cupon deserialized = mGson.fromJson(UserData.getInstance(mContext).getDetailedCoupon(), Cupon.class);
            deserialized.setFavorite(isFavorite);
            //Saves new data
            UserData.getInstance(mContext).saveDetailedCoupon(mGson.toJson(deserialized));

            //Toggle view
            mView.toggleFavorite(isFavorite);


            request.setFavorite(favoriteValue);

            mInteractor.saveCoupon(request, this);


        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onCoupons(JsonObject response)
    {

    }

    @Override
    public void onCouponsError(int codeStatus, Throwable throwable, String raw)
    {

    }

    @Override
    public void onPurchase(JsonObject result)
    {

    }

    @Override
    public void onPurchaseError(int codeStatus, Throwable throwable, String raw)
    {

    }

    @Override
    public void onFavorite(JsonObject response)
    {

        FavoriteCouponResponse favorite = mGson.fromJson(response, FavoriteCouponResponse.class);

        boolean isFavorite = false;

        isFavorite = favorite.getFavorite() == 1;

        mView.toggleFavorite(isFavorite);
    }

    @Override
    public void onFavoriteError(int codeStatus, String raw)
    {
        boolean prev = !mFavorite;
        mView.toggleFavorite(!prev);
        mView.showToast(mContext.getString(R.string.toast_favorite_failed));
    }
}
