package us.globalpay.manhattan.interactors;

import com.google.gson.JsonObject;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public interface MainListener
{
    void onSuccess(JsonObject rawResponse);
    void onError(int codeStatus, Throwable t, String rawResponse);
}
