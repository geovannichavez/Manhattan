package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsResponse
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("cupons")
    @Expose
    private Cupons cupons;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public Cupons getCupons()
    {
        return cupons;
    }

    public void setCupons(Cupons cupons)
    {
        this.cupons = cupons;
    }
}
