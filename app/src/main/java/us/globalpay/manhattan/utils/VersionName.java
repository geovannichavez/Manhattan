package us.globalpay.manhattan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

/**
 * Created by Josué Chávez on 04/09/2018.
 */

public class VersionName
{
    public static String getVersionName(Context context, String tag)
    {
        String version = "";
        try
        {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;//Version Name
            Log.i(tag, "Version name: " + version);
        }
        catch (Exception ex)
        {
            Log.e(tag, "Could not retrieve version name: " + ex.getMessage());
        }

        return version;
    }

    public static String getPackageName(Context context, String tag)
    {
        String packageName = "";
        try
        {
            packageName = context.getApplicationContext().getPackageName();
        }
        catch (Exception ex)
        {
            Log.e(tag, "Error: " + ex.getMessage());
        }

        return packageName;
    }
}
