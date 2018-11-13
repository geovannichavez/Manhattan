package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 29/10/2018.
 */
public class OpenGiftResponse
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CurrentCoins")
    @Expose
    private int currentCoins;
    @SerializedName("ExchangeCoins")
    @Expose
    private int exchangeCoins;
    @SerializedName("TotalWinCoins")
    @Expose
    private int totalWinCoins;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public int getCurrentCoins()
    {
        return currentCoins;
    }

    public void setCurrentCoins(int currentCoins)
    {
        this.currentCoins = currentCoins;
    }

    public int getExchangeCoins()
    {
        return exchangeCoins;
    }

    public void setExchangeCoins(int exchangeCoins)
    {
        this.exchangeCoins = exchangeCoins;
    }

    public int getTotalWinCoins()
    {
        return totalWinCoins;
    }

    public void setTotalWinCoins(int totalWinCoins)
    {
        this.totalWinCoins = totalWinCoins;
    }
}
