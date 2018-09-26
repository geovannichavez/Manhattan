package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.BrandsListener;
import us.globalpay.manhattan.models.api.BrandsReqBody;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface IBrandsInteractor
{
    void retrieveBrands(BrandsReqBody request, BrandsListener listener);
}
