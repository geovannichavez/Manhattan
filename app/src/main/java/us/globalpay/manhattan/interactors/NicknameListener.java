package us.globalpay.manhattan.interactors;

import us.globalpay.manhattan.models.api.SimpleResultResponse;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface NicknameListener
{
    void onValidateNicknameSuccess(SimpleResultResponse pResultResponse, String choosenNickname);
    void onError(int pCodeStatus, Throwable pThrowable, String rawResponse);
}
