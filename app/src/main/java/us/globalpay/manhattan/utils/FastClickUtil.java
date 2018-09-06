package us.globalpay.manhattan.utils;

/**
 * Created by Josué Chávez on 04/09/2018.
 */
public class FastClickUtil
{
    private static long lastClickTime;

    public synchronized static boolean isFastClick()
    {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
