package us.globalpay.manhattan.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class NavigatePlayStore
{
    private static final String TAG = NavigatePlayStore.class.getSimpleName();
    public static void navigate(Context context)
    {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + VersionName.getPackageName(context, TAG))));
        }
        catch (android.content.ActivityNotFoundException anfe)
        {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + VersionName.getPackageName(context, TAG))));
        }
    }
}
