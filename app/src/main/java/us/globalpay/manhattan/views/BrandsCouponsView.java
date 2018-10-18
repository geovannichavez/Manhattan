package us.globalpay.manhattan.views;

import android.content.DialogInterface;
import android.view.View;

import java.util.List;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public interface BrandsCouponsView
{
    void initialize();
    void loadBrand(Brand brand, String icon);
    void renderCoupons(List<Cupon> couponsList);
    void navigateDetails(int cuponID, boolean fromBrandCoupon);
    void showLoadingDialog(String message, boolean isCancelable);
    void hideLoadingDialog();
    void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener);
    void showCouponDialog(Cupon coupon, View.OnClickListener clickListener);
}
