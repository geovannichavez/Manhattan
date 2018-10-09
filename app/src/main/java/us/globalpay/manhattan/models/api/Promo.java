package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class Promo
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UrlImage")
    @Expose
    private String urlImage;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("UrlLogo")
    @Expose
    private String urlLogo;
    @SerializedName("UrlBackground")
    @Expose
    private String urlBackground;
    @SerializedName("LogoColor")
    @Expose
    private String logoColor;
    @SerializedName("MarkerLogo")
    @Expose
    private String markerLogo;
    @SerializedName("UrlImageCategory")
    @Expose
    private String urlImageCategory;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUrlImage()
    {
        return urlImage;
    }

    public void setUrlImage(String urlImage)
    {
        this.urlImage = urlImage;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
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

    public String getUrlImageCategory()
    {
        return urlImageCategory;
    }

    public void setUrlImageCategory(String urlImageCategory)
    {
        this.urlImageCategory = urlImageCategory;
    }
}
