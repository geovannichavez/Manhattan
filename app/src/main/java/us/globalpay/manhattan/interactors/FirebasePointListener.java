package us.globalpay.manhattan.interactors;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

import us.globalpay.manhattan.models.geofire.PrizePointData;
import us.globalpay.manhattan.models.geofire.WildcardPointData;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public interface FirebasePointListener
{
    //GeoFire GoldPoints
    void query_goldPoint_onKeyEntered(String key, LatLng location);
    void query_goldPoint_onKeyExited(String key);
    void query_goldPoint_onGeoQueryReady();

    //GeoFire SilverPoints
    void query_silverPoint_onKeyEntered(String key, LatLng location);
    void query_silverPoint_onKeyExited(String key);
    void query_silverPoint_onGeoQueryReady();

    //GeoFire BronzePoints
    void query_bronzePoint_onKeyEntered(String key, LatLng location);
    void query_bronzePoint_onKeyExited(String key);
    void query_bronzePoint_onGeoQueryReady();

    //GeoFire WildcardPoints
    void query_wildcardPoint_onKeyEntered(String key, LatLng location);
    void query_wildcardPoint_onKeyExited(String key);
    void query_wildcardPoint_onGeoQueryReady();

    /*
     *
     *
     *   DATA
     *
     */

    // GeoFire GoldPointsData
    void goldPoint_onDataChange(String key, PrizePointData pGoldPointData);
    void goldPoint_onCancelled(DatabaseError databaseError);

    // GeoFire SilverPointsData
    void silverPoint_onDataChange(String key, PrizePointData pSilverPointData);
    void silverPoint_onCancelled(DatabaseError databaseError);

    // GeoFire BronzePointsData
    void bronzePoint_onDataChange(String key, PrizePointData pBronzePointData);
    void bronzePoint_onCancelled(DatabaseError databaseError);

    // GeoFire WildcardPointsData
    void wildcardPoint_onDataChange(String key, WildcardPointData wildcardYCRData);
    void wildcardPoint_onCancelled(DatabaseError databaseError);

    void detachFirebaseListeners();
}
