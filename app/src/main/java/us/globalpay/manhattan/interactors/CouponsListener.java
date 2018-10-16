package us.globalpay.manhattan.interactors;

import com.google.gson.JsonObject;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public interface CouponsListener
{
    void onCoupons(JsonObject response);
    void onCouponsError(int codeStatus, Throwable throwable, String raw);
    void onPurchase(JsonObject result);
    void onPurchaseError(int codeStatus, Throwable throwable, String raw);
}
