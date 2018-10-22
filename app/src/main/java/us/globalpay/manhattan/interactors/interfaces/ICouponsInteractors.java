package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.api.BrandCouponsReq;
import us.globalpay.manhattan.models.api.CouponPurchaseReq;
import us.globalpay.manhattan.models.api.CouponsRequest;
import us.globalpay.manhattan.models.api.FavoriteCouponReq;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public interface ICouponsInteractors
{
    void retrieveCoupons(CouponsRequest request, CouponsListener listener);
    void retrieveBrandsCoupons(BrandCouponsReq request, CouponsListener listener);
    void purchaseCoupon(CouponPurchaseReq request, CouponsListener listener);
    void saveCoupon(FavoriteCouponReq request, CouponsListener listener);
}
