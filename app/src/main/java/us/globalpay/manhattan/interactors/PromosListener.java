package us.globalpay.manhattan.interactors;

import com.google.gson.JsonObject;

import us.globalpay.manhattan.models.api.PromosResponse;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public interface PromosListener
{
    void onPromosSucces(JsonObject response, PromosListener listener);
    void onPromosError(int codeStatus, Throwable throwable, String raw);
}
