package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class Cupons
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ResponseCode")
    @Expose
    private int responseCode;
    @SerializedName("Cupons")
    @Expose
    private List<Cupon> cupons = null;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    public List<Cupon> getCupons()
    {
        return cupons;
    }

    public void setCupons(List<Cupon> cupons)
    {
        this.cupons = cupons;
    }
}
