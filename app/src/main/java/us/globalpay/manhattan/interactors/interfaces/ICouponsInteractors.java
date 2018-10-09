package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.api.CouponsRequest;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public interface ICouponsInteractors
{
    void retrieveCoupons(CouponsRequest request, CouponsListener listener);
}
