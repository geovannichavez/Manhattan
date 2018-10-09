package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class PromosResponse
{

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("promos")
    @Expose
    private Promos promos;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public Promos getPromos()
    {
        return promos;
    }

    public void setPromos(Promos promos)
    {
        this.promos = promos;
    }
}
