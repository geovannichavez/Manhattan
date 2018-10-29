package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class Data
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("TotalCoins")
    @Expose
    private int totalCoins;
    @SerializedName("TotalNewPromo")
    @Expose
    private int totalNewPromo;
    @SerializedName("store")
    @Expose
    private List<Store> store = null;
    @SerializedName("brand")
    @Expose
    private List<Brand> brand = null;
    @SerializedName("cupon")
    @Expose
    private List<Cupon> cupon = null;
    @SerializedName("responseCode")
    @Expose
    private int responseCode;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getTotalCoins()
    {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins)
    {
        this.totalCoins = totalCoins;
    }

    public int getTotalNewPromo()
    {
        return totalNewPromo;
    }

    public void setTotalNewPromo(int totalNewPromo)
    {
        this.totalNewPromo = totalNewPromo;
    }

    public List<Store> getStore()
    {
        return store;
    }

    public void setStore(List<Store> store)
    {
        this.store = store;
    }

    public List<Brand> getBrand()
    {
        return brand;
    }

    public void setBrand(List<Brand> brand)
    {
        this.brand = brand;
    }

    public List<Cupon> getCupon()
    {
        return cupon;
    }

    public void setCupon(List<Cupon> cupon)
    {
        this.cupon = cupon;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }
}
