package us.globalpay.manhattan.interactors.interfaces;

import us.globalpay.manhattan.interactors.NicknameListener;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public interface INicknameInteractor
{
    void validateNickname(String nickname, NicknameListener listener);
}
