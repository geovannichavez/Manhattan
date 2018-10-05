package us.globalpay.manhattan.interactors;

import us.globalpay.manhattan.models.api.GetCouponResponse;

/**
 * Created by Josué Chávez on 05/10/2018.
 */
public interface ARListener
{
    void onGetUserScoreSuccess();
    void onGetUserScoreError(int codeStatsu, Throwable throwable, String raw);
    void onRedeemCouponSuccess(GetCouponResponse response);
    void onRedeemCouponError(int codeStatus, Throwable throwable, String raw);
}
