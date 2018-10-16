package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 16/10/2018.
 */
public class CouponPurchaseResponse
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("cupon")
    @Expose
    private Cupon cupon;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public Cupon getCupon()
    {
        return cupon;
    }

    public void setCupon(Cupon cupon)
    {
        this.cupon = cupon;
    }
}
