package us.globalpay.manhattan.presenters.interfaces;

import us.globalpay.manhattan.models.api.Brand; /**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface IBrandsPresenter
{
    void init();
    void retrieveBrands();
    void saveSelectedBrand(Brand selected);
}
