package us.globalpay.manhattan.views;

/**
 * Created by Josué Chávez on 25/09/2018.
 */
public interface PermissionsView
{
    void initializeViews();
    void navegateNextActivity();
    void generateToast(String text);
}
