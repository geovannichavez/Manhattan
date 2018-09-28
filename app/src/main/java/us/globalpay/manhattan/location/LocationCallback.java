package us.globalpay.manhattan.location;

import android.location.Location;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public interface LocationCallback
{
    void onLocationChanged(Location location);
    void onLocationApiManagerConnected(Location location);
    void onLocationApiManagerDisconnected();
}
