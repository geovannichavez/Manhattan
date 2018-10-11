package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public class BrandCouponsReq
{
    @SerializedName("BrandID")
    @Expose
    private int brandID;

    public int getBrandID()
    {
        return brandID;
    }

    public void setBrandID(int brandID)
    {
        this.brandID = brandID;
    }
}
