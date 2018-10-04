package us.globalpay.manhattan.interactors.interfaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public interface IARInteractor
{
    void retrieveConsumerTracking();
    void openCoinsChest(LatLng location, String firebaseId, int chestType);
    //void saveUserTracking(Tracking pTracking);
    void saveUserTracking();
    void atemptRedeemPrize();
}
