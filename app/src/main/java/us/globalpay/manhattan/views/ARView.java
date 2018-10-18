package us.globalpay.manhattan.views;

import android.content.DialogInterface;

import com.google.android.gms.maps.model.LatLng;

import us.globalpay.manhattan.models.DialogModel;

/**
 * Created by Josué Chávez on 03/10/2018.
 */
public interface ARView
{
    void updateUserLocation(double latitude, double longitude, double accuracy);
    void locationManagerConnected(double latitude, double longitude, double accuracy);

    void setClickListeners();
    void on2DChestClick();
    void hideArchViewNoContainersMsg();
    void showGenericDialog(DialogModel messageModel, DialogInterface.OnClickListener clickListener);
    //void showImageDialog(DialogModel dialogModel, int resource, boolean closeActivity);
    void showLoadingDialog(String pLabel);
    void hideLoadingDialog();
    void switchChestVisible(boolean isVisible);
    void makeChestBlink();
    void showToast(String pText);
    void removeBlinkingAnimation();
    void on2DChestTouch(int await);
    void removeRunnableCallback();
    void deleteModelAR();

    //void updateIndicators(String pPrizes, int pCoins, String pSouvenirs);
    void updatePrizeButton(int pCoins);

    void onGoldKeyEntered(String key, LatLng location, String folderName);
    void onGoldKeyExited(String pKey);

    void onSilverKeyEntered(String key, LatLng location, String folderName);
    void onSilverKeyExited(String pKey);

    void onBronzeKeyEntered(String key, LatLng location, String folderName);
    void onBronzeKeyExited(String pKey);

    void onWildcardKeyEntered(String key, LatLng location, String folderName);
    void onWildcardKeyExited(String pKey);

    void changeToOpenChest(int pChestType);
    void navigateToWildcard();

    void navigateToCouponDetails();
    void setEnabledChestImage(boolean enabled);

    void drawChest2D(String pKey, LatLng pLocation, int chestTypeValue);
}
