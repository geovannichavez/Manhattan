package us.globalpay.manhattan.presenters.interfaces;

import us.globalpay.manhattan.models.api.Cupon; /**
 * Created by Josué Chávez on 10/10/2018.
 */
public interface IBrandCouponsPresenter
{
    void initialize(String categoryIcon);
    void retrieveCoupons(int brand);
    void couponActions(Cupon cupon);
    void handlePurchaseAction(Cupon coupon);
}
