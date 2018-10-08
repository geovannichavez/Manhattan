package us.globalpay.manhattan.presenters.interfaces;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public interface ICouponsPresenter
{
    void initialize();
    void retrieveCoupons(int option, int storeId);
}
