package us.globalpay.manhattan.interactors;

import com.google.gson.JsonObject;

import us.globalpay.manhattan.models.api.BrandsResponse;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface BrandsListener
{
    void onSuccess(JsonObject response);
    void onError(int codeStatus, Throwable throwable, String rawResponse);
}
