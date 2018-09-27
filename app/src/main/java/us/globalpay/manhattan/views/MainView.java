package us.globalpay.manhattan.views;

/**
 * Created by Josué Chávez on 18/09/2018.
 */
public interface MainView
{
    void initialize();
    void renderMap();
    void renderCoupons();
    void loadInitialValues(String coins, String promos);
    void navigateBrands();

}
