package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 05/10/2018.
 */
public class GetCouponResponse
{
    @SerializedName("cupon")
    @Expose
    private Cupon cupon;

    public Cupon getCupon()
    {
        return cupon;
    }

    public void setCupon(Cupon cupon)
    {
        this.cupon = cupon;
    }
}
