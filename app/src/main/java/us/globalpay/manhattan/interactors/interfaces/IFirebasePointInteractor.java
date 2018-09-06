package us.globalpay.manhattan.interactors.interfaces;

import com.firebase.geofire.GeoLocation;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public interface IFirebasePointInteractor
{
    void initializeGeolocation();
    void goldPointsQuery(GeoLocation pLocation, double pRadius);
    void goldPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void silverPointsQuery(GeoLocation pLocation, double pRadius);
    void silverPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void bronzePointsQuery(GeoLocation pLocation, double pRadius);
    void bronzePointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void wildcardPointsQuery(GeoLocation location, double radius);
    void wildcardPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void detachFirebaseListeners();
}
