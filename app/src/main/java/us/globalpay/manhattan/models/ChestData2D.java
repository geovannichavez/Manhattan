package us.globalpay.manhattan.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ChestData2D
{
    private int chestType;
    private LatLng location;

    public ChestData2D()
    {

    }

    public int getChestType()
    {
        return chestType;
    }

    public LatLng getLocation()
    {
        return location;
    }

    public void setChestType(int chestType)
    {
        this.chestType = chestType;
    }

    public void setLocation(LatLng location)
    {
        this.location = location;
    }
}
