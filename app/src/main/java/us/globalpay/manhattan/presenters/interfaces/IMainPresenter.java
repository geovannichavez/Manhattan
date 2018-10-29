package us.globalpay.manhattan.presenters.interfaces;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public interface IMainPresenter
{
    void initialize();
    void checkUserDataCompleted();
    void chekcLocationServiceEnabled();
    void checkPermissions();
    void connnectToLocationService();
    void onMapReady();
    void intializeGeolocation();
    void retrieveData();
    void prizePointsQuery(LatLng pLocation);
    void updatePrizePntCriteria(LatLng pLocation);
    void saveSelectedCoupon(Cupon selected);
}
