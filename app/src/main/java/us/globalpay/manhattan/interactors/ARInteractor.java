package us.globalpay.manhattan.interactors;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.interactors.interfaces.IARInteractor;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ARInteractor implements IARInteractor
{
    private static final String TAG = ARInteractor.class.getSimpleName();

    private Context mContext;

    public ARInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveConsumerTracking()
    {

    }

    @Override
    public void openCoinsChest(LatLng location, String firebaseId, int chestType)
    {

    }

    @Override
    public void saveUserTracking()
    {

    }

    @Override
    public void atemptRedeemPrize()
    {

    }
}
