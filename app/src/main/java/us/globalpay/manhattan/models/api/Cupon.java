package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 05/10/2018.
 */
public class Cupon
{
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
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("PinLevel")
    @Expose
    private int pinLevel;
    @SerializedName("Code")
    @Expose
    private String code;

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

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public int getPinLevel()
    {
        return pinLevel;
    }

    public void setPinLevel(int pinLevel)
    {
        this.pinLevel = pinLevel;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
