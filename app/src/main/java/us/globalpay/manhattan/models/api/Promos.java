package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class Promos
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ResponseCode")
    @Expose
    private int responseCode;
    @SerializedName("promos")
    @Expose
    private List<Promo> promos = null;

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

    public List<Promo> getPromos()
    {
        return promos;
    }

    public void setPromos(List<Promo> promos)
    {
        this.promos = promos;
    }
}
