package us.globalpay.manhattan.presenters;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.UUID;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.presenters.interfaces.ITermsPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.RequirementsAR;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.TermsView;

/**
 * Created by Josué Chávez on 25/09/2018.
 */
public class TermsPresenter implements ITermsPresenter
{
    private static final String TAG = TermsPresenter.class.getSimpleName();

    private Context mContext;
    private TermsView mView;

    public TermsPresenter(Context context, AppCompatActivity activity, TermsView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void acceptTerms()
    {
        UserData.getInstance(mContext).accpetedTerms(true);

        String uniqueID = UUID.randomUUID().toString().toUpperCase();
        UserData.getInstance(mContext).saveDeviceID(uniqueID);
    }

    @Override
    public void presentTerms()
    {
        mView.viewTerms(Constants.TERMS_AND_CONDITIONS_URL);
    }

    @Override
    public void checkDeviceComponents()
    {
        RequirementsAR result = new RequirementsAR();
        HashMap<Integer, String> components = new HashMap<>();

        final LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        final SensorManager sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        //Digital compass
        final boolean hasCompass = sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null;
        if(!hasCompass)
            components.put(1, mContext.getString(R.string.component_compass));

        //GPS Senson
        final boolean hasGPS = locationManager != null && locationManager.getAllProviders() != null && locationManager.getAllProviders().size() > 0;
        if(!hasGPS)
            components.put(2, mContext.getString(R.string.component_gps));

        // openGL-API version for rendering
        final boolean hasOpenGL20 = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
        if(!hasOpenGL20)
            components.put(3, mContext.getString(R.string.component_opengl));

        // Rear camera
        final boolean hasRearCam = mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(!hasRearCam)
            components.put(4, mContext.getString(R.string.component_camera));

        //Checks if requierements are fulfilled
        final boolean hasAllRequirements = hasCompass && hasGPS && hasOpenGL20 && hasRearCam;
        result.setCompatible(hasAllRequirements);
        result.setComponents(components);

        UserData.getInstance(mContext).save3DCompatibleValue(hasAllRequirements);
    }

    @Override
    public void setFirstTimeSettings()
    {
        UserData.getInstance(mContext).saveSimpleInstructionsSetting(false);
    }

    @Override
    public void grantDevicePermissions()
    {
        UserData.getInstance(mContext).hasGrantedDevicePermissions(true);
    }
}
