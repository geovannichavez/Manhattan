package us.globalpay.manhattan.presenters.interfaces;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public interface IBrandCouponsPresenter
{
    void initialize(String categoryIcon);
    void retrieveCoupons(int brand);
}
