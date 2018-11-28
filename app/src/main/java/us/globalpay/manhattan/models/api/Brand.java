package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class Brand
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("BrandID")
    @Expose
    private int brandID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("UrlLogo")
    @Expose
    private String urlLogo;
    @SerializedName("UrlBackground")
    @Expose
    private String urlBackground;
    @SerializedName("HexColor")
    @Expose
    private String hexColor;
    @SerializedName("RGBColor")
    @Expose
    private String rGBColor;
    @SerializedName("CategoryID")
    @Expose
    private int categoryID;
    @SerializedName("LogoColor")
    @Expose
    private String logoColor;
    @SerializedName("MarkerLogo")
    @Expose
    private String markerLogo;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getBrandID()
    {
        return brandID;
    }

    public void setBrandID(int brandID)
    {
        this.brandID = brandID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public String getUrlLogo()
    {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo)
    {
        this.urlLogo = urlLogo;
    }

    public String getUrlBackground()
    {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground)
    {
        this.urlBackground = urlBackground;
    }

    public String getHexColor()
    {
        return hexColor;
    }

    public void setHexColor(String hexColor)
    {
        this.hexColor = hexColor;
    }

    public String getRGBColor()
    {
        return rGBColor;
    }

    public void setRGBColor(String rGBColor)
    {
        this.rGBColor = rGBColor;
    }

    public int getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }

    public String getLogoColor()
    {
        return logoColor;
    }

    public void setLogoColor(String logoColor)
    {
        this.logoColor = logoColor;
    }

    public String getMarkerLogo()
    {
        return markerLogo;
    }

    public void setMarkerLogo(String markerLogo)
    {
        this.markerLogo = markerLogo;
    }
}
