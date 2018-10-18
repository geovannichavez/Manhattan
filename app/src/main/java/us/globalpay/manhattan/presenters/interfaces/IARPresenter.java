package us.globalpay.manhattan.presenters.interfaces;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public interface IARPresenter
{
    void initialize();
    void resume();
    void chestPointsQuery(LatLng pLocation);
    void updateChestPntCriteria(LatLng pLocation);
    void openChest(JSONObject wikitudeJson);
    void open2DChest(LatLng location, String firebaseId, int chestType);
    void retrieveUserTracking();
    void redeemPrize();
    void handle2DCoinTouch();
    void handleCoinExchangeKeyUp();
    void touchWildcard_2D(String pFirebaseID, int chestType);
    void deleteFirstKeySaved();
    void registerKeyEntered(String pKey, LatLng location, String chestType);
}
