package us.globalpay.manhattan.views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 18/09/2018.
 */
public interface MainView
{
    void initialize();
    void renderMap();
    void renderCoupons();

    void loadInitialValues(String coins, String promos);
    void displayActivateLocationDialog();
    void navigateBrands();
    void navigateCoupons();
    void checkPermissions();
    void updateUserLocationOnMap(Location location);
    void showToast(String text);
    void setInitialUserLocation(Location location);
    void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener);

    void addGoldPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addGoldPointData(String pKey, String pTitle, String pSnippet);
    void removeGoldPoint(String pKey);

    void addSilverPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addSilverPointData(String pKey, String pTitle, String pSnippet);
    void removeSilverPoint(String pKey);

    void addBronzePoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addBronzePointData(String pKey, String pTitle, String pSnippet);
    void removeBronzePoint(String pKey);

    void addWildcardPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addWildcardPointData(String pKey, String brand, String title, String message);
    void removeWildcardPoint(String pKey);

}
