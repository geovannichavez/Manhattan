package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.ARListener;
import us.globalpay.manhattan.models.api.GetCouponReq;
import us.globalpay.manhattan.models.api.OpenGiftReq;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public interface IARInteractor
{
    void retrieveConsumerTracking();
    void openCoinsChest(OpenGiftReq request, ARListener listener);
    void atemptRedeemCoupon(ARListener listener);
    void getBrandCoupon(GetCouponReq request, ARListener listener);
}
