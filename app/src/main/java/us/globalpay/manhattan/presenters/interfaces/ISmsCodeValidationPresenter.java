package us.globalpay.manhattan.presenters.interfaces;

/**
 * Created by Josué Chávez on 02/10/2018.
 */
public interface ISmsCodeValidationPresenter
{
    void initialize();
    void validateSmsCode(String code);
}
