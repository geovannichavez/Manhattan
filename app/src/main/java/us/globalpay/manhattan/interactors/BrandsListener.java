package us.globalpay.manhattan.interactors;

import us.globalpay.manhattan.models.api.BrandsResponse;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface BrandsListener
{
    void onSuccess(BrandsResponse response);
    void onError(int codeStatus, Throwable throwable, String rawResponse);
}
