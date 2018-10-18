package us.globalpay.manhattan.interactors.interfaces;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.interactors.ARListener;
import us.globalpay.manhattan.models.api.GetCouponReq;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public interface IARInteractor
{
    void retrieveConsumerTracking();
    void openCoinsChest(LatLng location, String firebaseId, int chestType);
    void atemptRedeemCoupon(ARListener listener);
    void getBrandCoupon(GetCouponReq request, ARListener listener);
}
