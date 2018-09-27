package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class Store
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("StoreID")
    @Expose
    private int storeID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Latitude")
    @Expose
    private float latitude;
    @SerializedName("Longitude")
    @Expose
    private float longitude;
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("ModDate")
    @Expose
    private String modDate;
    @SerializedName("CountryID")
    @Expose
    private int countryID;
    @SerializedName("SponsorID")
    @Expose
    private int sponsorID;
    @SerializedName("Address")
    @Expose
    private Object address;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getStoreID()
    {
        return storeID;
    }

    public void setStoreID(int storeID)
    {
        this.storeID = storeID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getRegDate()
    {
        return regDate;
    }

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

    public String getModDate()
    {
        return modDate;
    }

    public void setModDate(String modDate)
    {
        this.modDate = modDate;
    }

    public int getCountryID()
    {
        return countryID;
    }

    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    public int getSponsorID()
    {
        return sponsorID;
    }

    public void setSponsorID(int sponsorID)
    {
        this.sponsorID = sponsorID;
    }

    public Object getAddress()
    {
        return address;
    }

    public void setAddress(Object address)
    {
        this.address = address;
    }
}
