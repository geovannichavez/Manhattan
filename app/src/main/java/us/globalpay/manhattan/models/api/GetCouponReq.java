package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 17/10/2018.
 */
public class GetCouponReq
{
    @SerializedName("BrandID")
    @Expose
    private int brandID;
    @SerializedName("MethodID")
    @Expose
    private int methodID;

    public int getBrandID()
    {
        return brandID;
    }

    public void setBrandID(int brandID)
    {
        this.brandID = brandID;
    }

    public int getMethodID()
    {
        return methodID;
    }

    public void setMethodID(int methodID)
    {
        this.methodID = methodID;
    }

}
