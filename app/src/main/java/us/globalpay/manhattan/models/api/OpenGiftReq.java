package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 29/10/2018.
 */
public class OpenGiftReq
{
    @SerializedName("LocationID")
    @Expose
    private String locationID;
    @SerializedName("Longitude")
    @Expose
    private float longitude;
    @SerializedName("Latitude")
    @Expose
    private float latitude;
    @SerializedName("ChestType")
    @Expose
    private int chestType;

    public String getLocationID()
    {
        return locationID;
    }

    public void setLocationID(String locationID)
    {
        this.locationID = locationID;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public int getChestType()
    {
        return chestType;
    }

    public void setChestType(int chestType)
    {
        this.chestType = chestType;
    }

}
