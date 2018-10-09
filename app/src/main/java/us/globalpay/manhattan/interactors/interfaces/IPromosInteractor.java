package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.PromosListener;
import us.globalpay.manhattan.models.api.PromosRequest;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public interface IPromosInteractor
{
    void retrievePromos(PromosRequest request, PromosListener listener);
}
