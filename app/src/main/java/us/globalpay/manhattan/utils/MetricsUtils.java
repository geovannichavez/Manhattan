package us.globalpay.manhattan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public class MetricsUtils
{
    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    public static int pixelsFromDp(Context context, float dp)
    {
        float pixels = dp * context.getResources().getDisplayMetrics().density;
        return Math.round(pixels);
    }

    public static int dpFromPixels(Context context, float px)
    {
        float dp = px / context.getResources().getDisplayMetrics().density;
        return Math.round(dp);
    }
}
