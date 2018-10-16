package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 05/10/2018.
 */
public class Cupon
{

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CuponID")
    @Expose
    private int cuponID;
    @SerializedName("PinID")
    @Expose
    private int pinID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UrlBackground")
    @Expose
    private String urlBackground;
    @SerializedName("UrlBackgroundHistory")
    @Expose
    private String urlBackgroundHistory;
    @SerializedName("Purchasable")
    @Expose
    private boolean purchasable;
    @SerializedName("Favorite")
    @Expose
    private boolean favorite;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("UrlLogo")
    @Expose
    private String urlLogo;
    @SerializedName("UrlBackgroundBrand")
    @Expose
    private String urlBackgroundBrand;
    @SerializedName("LogoColor")
    @Expose
    private String logoColor;
    @SerializedName("MarkerLogo")
    @Expose
    private String markerLogo;
    @SerializedName("UrlImageCategory")
    @Expose
    private String urlImageCategory;
    @SerializedName("Price")
    @Expose
    private int price;
    @SerializedName("MethodID")
    @Expose
    private int methodID;
    @SerializedName("Free")
    @Expose
    private boolean free;
    @SerializedName("Unlocked")
    @Expose
    private boolean unlocked;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getCuponID()
    {
        return cuponID;
    }

    public void setCuponID(int cuponID)
    {
        this.cuponID = cuponID;
    }

    public int getPinID()
    {
        return pinID;
    }

    public void setPinID(int pinID)
    {
        this.pinID = pinID;
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

    public String getUrlBackground()
    {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground)
    {
        this.urlBackground = urlBackground;
    }

    public String getUrlBackgroundHistory()
    {
        return urlBackgroundHistory;
    }

    public void setUrlBackgroundHistory(String urlBackgroundHistory)
    {
        this.urlBackgroundHistory = urlBackgroundHistory;
    }

    public boolean isPurchasable()
    {
        return purchasable;
    }

    public void setPurchasable(boolean purchasable)
    {
        this.purchasable = purchasable;
    }

    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    public String getUrlBackgroundBrand()
    {
        return urlBackgroundBrand;
    }

    public void setUrlBackgroundBrand(String urlBackgroundBrand)
    {
        this.urlBackgroundBrand = urlBackgroundBrand;
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

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getMethodID()
    {
        return methodID;
    }

    public void setMethodID(int methodID)
    {
        this.methodID = methodID;
    }

    public boolean isFree()
    {
        return free;
    }

    public void setFree(boolean free)
    {
        this.free = free;
    }

    public boolean isUnlocked()
    {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked)
    {
        this.unlocked = unlocked;
    }


}
