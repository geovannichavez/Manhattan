package us.globalpay.manhattan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ArchViewGeoObject
{
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("chest")
    @Expose
    private int chest;
    @SerializedName("sponsor")
    @Expose
    private String sponsor;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("brandid")
    @Expose
    private int brandid;
    @SerializedName("visible")
    @Expose
    private boolean visible;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("key")
    @Expose
    private String key;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getChest()
    {
        return chest;
    }

    public void setChest(int chest)
    {
        this.chest = chest;
    }

    public String getSponsor()
    {
        return sponsor;
    }

    public void setSponsor(String sponsor)
    {
        this.sponsor = sponsor;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public int getBrandid()
    {
        return brandid;
    }

    public void setBrandid(int brandid)
    {
        this.brandid = brandid;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }


}
