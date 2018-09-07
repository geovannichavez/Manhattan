package us.globalpay.manhattan.interactors.interfaces;

import com.firebase.geofire.GeoLocation;

import us.globalpay.manhattan.interactors.FirebasePointListener;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public interface IFirebasePointInteractor
{
    void initializeGeolocation();
    void goldPointsQuery(GeoLocation location, double radius);
    void goldPointsUpdateCriteria(GeoLocation location, double radius);
    void silverPointsQuery(GeoLocation location, double radius);
    void silverPointsUpdateCriteria(GeoLocation location, double radius);
    void bronzePointsQuery(GeoLocation location, double radius);
    void bronzePointsUpdateCriteria(GeoLocation location, double radius);
    void wildcardPointsQuery(GeoLocation location, double radius);
    void wildcardPointsUpdateCriteria(GeoLocation location, double radius);
    void detachFirebaseListeners();
}
