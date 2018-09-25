package us.globalpay.manhattan.presenters.interfaces;

/**
 * Created by Josué Chávez on 25/09/2018.
 */
public interface IPermissionsPresenter
{
    void initialize();
    void checkPermission();
    void onPermissionsResult(int pRequestCode, String pPermissions[], int[] pGrantResults);
}
