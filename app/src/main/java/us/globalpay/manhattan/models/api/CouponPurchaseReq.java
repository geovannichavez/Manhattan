package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 11/10/2018.
 */
public class CouponPurchaseReq
{

    @SerializedName("CuponID")
    @Expose
    private int cuponID;
    @SerializedName("Cost")
    @Expose
    private int cost;

    public int getCuponID()
    {
        return cuponID;
    }

    public void setCuponID(int cuponID)
    {
        this.cuponID = cuponID;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
