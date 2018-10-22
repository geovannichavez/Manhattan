package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 19/10/2018.
 */
public class FavoriteCouponReq
{
    @SerializedName("PinID")
    @Expose
    private int pinID;
    @SerializedName("favorite")
    @Expose
    private int favorite;

    public int getPinID()
    {
        return pinID;
    }

    public void setPinID(int pinID)
    {
        this.pinID = pinID;
    }

    public int getFavorite()
    {
        return favorite;
    }

    public void setFavorite(int favorite)
    {
        this.favorite = favorite;
    }

}
