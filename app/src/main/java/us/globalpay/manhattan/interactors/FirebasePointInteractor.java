package us.globalpay.manhattan.interactors;

import android.content.Context;

import com.firebase.geofire.GeoLocation;

import us.globalpay.manhattan.interactors.interfaces.IFirebasePointInteractor;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public class FirebasePointInteractor implements IFirebasePointInteractor
{
    private static final String TAG = FirebasePointInteractor.class.getSimpleName();
    private Context mContext;
    private FirebasePointListener mListener;


    public FirebasePointInteractor(Context context)
    {

    }

    @Override
    public void initializeGeolocation()
    {

    }

    @Override
    public void goldPointsQuery(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void goldPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void silverPointsQuery(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void silverPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void bronzePointsQuery(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void bronzePointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void wildcardPointsQuery(GeoLocation location, double radius)
    {

    }

    @Override
    public void wildcardPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {

    }

    @Override
    public void detachFirebaseListeners()
    {

    }
}
