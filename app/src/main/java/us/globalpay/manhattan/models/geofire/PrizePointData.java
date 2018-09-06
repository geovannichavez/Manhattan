package us.globalpay.manhattan.models.geofire;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public class PrizePointData
{
    private String brand;
    private String coins;
    private String detail;

    public String getBrand()
    {
        return brand;
    }

    public String getCoins()
    {
        return coins;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public void setCoins(String coins)
    {
        this.coins = coins;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public PrizePointData()
    {

    }

    public PrizePointData(String pBrand, String pCoins, String pDetail)
    {
        this.brand = pBrand;
        this.coins = pCoins;
        this.detail = pCoins;
    }
}
