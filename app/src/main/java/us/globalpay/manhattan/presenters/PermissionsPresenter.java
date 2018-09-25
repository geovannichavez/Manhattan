package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.presenters.interfaces.IPermissionsPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.PermissionsView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Josué Chávez on 25/09/2018.
 */
public class PermissionsPresenter implements IPermissionsPresenter
{
    private static final String TAG = PermissionsPresenter.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private PermissionsView mView;
    private boolean arCompatible;

    public PermissionsPresenter(PermissionsView pView, Context pContext, AppCompatActivity pActivity)
    {
        mContext = pContext;
        mView = pView;
        mActivity = pActivity;
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void checkPermission()
    {
        try
        {
            boolean permissions;

            int FirstPermissionResult = ContextCompat.checkSelfPermission(mContext, CAMERA);
            int SecondPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION);
            int ThirdPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_COARSE_LOCATION);
            int SixthPermissionResult = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);

            permissions = FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    SixthPermissionResult == PackageManager.PERMISSION_GRANTED;

            if (permissions)
            {
                mView.navegateNextActivity();
            }
            else
            {
                UserData.getInstance(mContext).hasGrantedDevicePermissions(false);
                ActivityCompat.requestPermissions(mActivity,
                        new String[]
                                {
                                        CAMERA,
                                        //READ_SMS,
                                        ACCESS_FINE_LOCATION,
                                        WRITE_EXTERNAL_STORAGE
                                }, Constants.REQUEST_PERMISSION_CODE);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage());
        }

    }

    @Override
    public void onPermissionsResult(int pRequestCode, String[] pPermissions, int[] pGrantResults)
    {
        switch (pRequestCode)
        {
            case Constants.REQUEST_PERMISSION_CODE:

                if (pGrantResults.length > 0)
                {
                    boolean CameraPermission = pGrantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessFineLocationPermission = pGrantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage = pGrantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && AccessFineLocationPermission && writeExternalStorage)
                    {
                        UserData.getInstance(mContext).hasGrantedDevicePermissions(true);
                        mView.navegateNextActivity();
                    }
                    else
                    {
                        mView.generateToast(mContext.getString(R.string.toast_permissions_denied));
                    }
                }

                break;
        }
    }
}
