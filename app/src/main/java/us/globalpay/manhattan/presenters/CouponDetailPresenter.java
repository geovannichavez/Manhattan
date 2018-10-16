package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.presenters.interfaces.ICouponDetailPresenter;
import us.globalpay.manhattan.ui.activities.Coupons;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.CouponDetailView;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public class CouponDetailPresenter implements ICouponDetailPresenter
{
    private static final String TAG = CouponDetailPresenter.class.getSimpleName();

    private Context mContext;
    private CouponDetailView mView;
    private Gson mGson;
    private AppCompatActivity mActivity;

    public CouponDetailPresenter(Context context, AppCompatActivity activity, CouponDetailView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mGson = new Gson();
        this.mActivity = activity;
    }

    @Override
    public void initialize()
    {
        mView.initalize();
    }

    @Override
    public void loadDetails(int couponID, boolean isPurchase, boolean isBrandCoupon)
    {
        try
        {
            Cupon selectedCoupon = null;

            if(!isPurchase)
            {
                CouponsResponse serialized = null;

                if(!isBrandCoupon)
                    serialized = mGson.fromJson(UserData.getInstance(mContext).getCouponsData(), CouponsResponse.class);
                else
                    serialized = mGson.fromJson(UserData.getInstance(mContext).getSelectedBrandCoupons(), CouponsResponse.class);

                for(Cupon item : serialized.getCupons().getCupons())
                {
                    if(item.getCuponID() == couponID)
                    {
                        selectedCoupon = item;
                        break;
                    }
                }
            }
            else
            {
                selectedCoupon = mGson.fromJson(UserData.getInstance(mContext).getLastPurchasedCoupon(), Cupon.class);
            }


            if(selectedCoupon != null)
            {
                Bundle details = new Bundle();
                details.putString(Constants.BUNDLE_COUPON_ID, selectedCoupon.get$id());
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
}
