package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.CouponsInteractor;
import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.BrandCouponsReq;
import us.globalpay.manhattan.models.api.CouponPurchaseReq;
import us.globalpay.manhattan.models.api.CouponPurchaseResponse;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.presenters.interfaces.IBrandCouponsPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavigatePlayStore;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.views.BrandsCouponsView;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public class BrandCouponsPresenter implements IBrandCouponsPresenter, CouponsListener
{
    private static final String TAG = BrandCouponsPresenter.class.getSimpleName();

    private Gson mGson;
    private Context mContext;
    private BrandsCouponsView mView;
    private CouponsInteractor mInteractor;

    public BrandCouponsPresenter(Context context, AppCompatActivity activity, BrandsCouponsView view)
    {
        this.mGson = new Gson();
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new CouponsInteractor(mContext);
    }

    @Override
    public void initialize(String categoryIcon)
    {
       try
       {
           mView.initialize();

           String serialized = UserData.getInstance(mContext).getSelectedBrand();
           Brand brand = mGson.fromJson(serialized, Brand.class);

           if(brand != null)
               mView.loadBrand(brand, categoryIcon);

       }
       catch (Exception ex)
       {
           Log.e(TAG, "Error: " + ex.getMessage());
       }
    }

    @Override
    public void retrieveCoupons(int brand)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_please_wait), true);
        BrandCouponsReq request = new BrandCouponsReq();
        request.setBrandID(5); //TODO: Caambiar por variable

        mInteractor.retrieveBrandsCoupons(request, this);
    }

    @Override
    public void couponActions(final Cupon cupon)
    {
        try
        {
            mView.showCouponDialog(cupon, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(cupon.getMethodID() == Constants.EXCHANGE_METHOD_1_WILDCARD)
                    {
                        ButtonAnimator.floatingButton(mContext, view);

                        final DialogModel content = new DialogModel();
                        content.setTitle(mContext.getString(R.string.title_warning));
                        content.setContent(mContext.getString(R.string.label_purchase_coupon_confirmation));
                        content.setAcceptButton(mContext.getString(R.string.button_accept));
                        DialogGenerator.showImageDialog(mContext, null,
                                R.drawable.ic_coins_stacked_medium, content, new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                ButtonAnimator.floatingButton(mContext, v);
                                handlePurchaseAction(cupon);
                            }
                        });
                    }
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void handlePurchaseAction(Cupon coupon)
    {
        try
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_please_wait), true);
            CouponPurchaseReq request = new CouponPurchaseReq();
            request.setCost(coupon.getPrice());
            request.setCuponID(coupon.getCuponID());
            mInteractor.purchaseCoupon(request, BrandCouponsPresenter.this);
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
    }

    @Override
    public void selectCouponDetails(Cupon cupon)
    {
        try
        {
            CouponsResponse serialized = mGson.fromJson(UserData.getInstance(mContext).getSelectedBrandCoupons(), CouponsResponse.class);

            for(Cupon item : serialized.getCupons().getCupons())
            {
                if(item.getCuponID() == cupon.getCuponID())
                {
                    String selectedCoupon = mGson.toJson(item);
                    UserData.getInstance(mContext).saveDetailedCoupon(selectedCoupon);
                    break;
                }
            }
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
    }

    @Override
    public void onCoupons(JsonObject response)
    {
        try
        {
            mView.hideLoadingDialog();
            String rawResponse = mGson.toJson(response);

            //Saves selected brand coupons
            UserData.getInstance(mContext).saveSelectedBrandCoupons(rawResponse);

            //Deserializes response
            CouponsResponse deserialized = mGson.fromJson(rawResponse, CouponsResponse.class);

            //Pass data to view
            mView.renderCoupons(deserialized.getCupons().getCupons());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onCouponsError(int codeStatus, Throwable throwable, String raw)
    {
        mView.hideLoadingDialog();
        handleError(codeStatus, throwable, raw);
    }

    @Override
    public void onPurchase(JsonObject result)
    {
        try
        {
            mView.hideLoadingDialog();

            //Deserializes response
            String rawResponse = mGson.toJson(result);
            CouponPurchaseResponse deserialized = mGson.fromJson(rawResponse, CouponPurchaseResponse.class);

            Cupon purchased = deserialized.getCupon();

            //Saves serialized purchase
            String serializedCoupon = mGson.toJson(purchased);
            UserData.getInstance(mContext).saveDetailedCoupon(serializedCoupon);

            //Navigates to details
            mView.navigateDetails(purchased.getCuponID(), true);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onPurchaseError(int codeStatus, Throwable throwable, String raw)
    {
        mView.hideLoadingDialog();
        handleError(codeStatus, throwable, raw);
    }

    @Override
    public void onFavorite(JsonObject response)
    {

    }

    @Override
    public void onFavoriteError(int codeStatus, String raw)
    {

    }

    /*
     *
     *
     *
     *   CODE STATUS
     *
     *
     *
     * */

    private void handleError(int codeStatus, Throwable throwable, String raw)
    {
        try
        {
            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), raw);

                DialogModel dialog = new DialogModel();
                dialog.setTitle(title);
                dialog.setContent(content);
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                mView.showDialog(dialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        NavigatePlayStore.navigate(mContext);
                    }
                });
            }
            else
            {
                DialogModel dialog = new DialogModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setContent(mContext.getString(R.string.error_label_something_went_wrong));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showDialog(dialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }
}
