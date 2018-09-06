package us.globalpay.manhattan.core;

import android.app.Application;
import android.content.res.Configuration;

import com.onesignal.OneSignal;

import us.globalpay.manhattan.utils.NotificationOpenedHandler;
import us.globalpay.manhattan.utils.NotificationReceivedHandler;

public class ManhattanApplication extends Application
{
    private static final String TAG = ManhattanApplication.class.getSimpleName();
    private static ManhattanApplication mSingleton;

    public static ManhattanApplication getInstance()
    {
        return mSingleton;
    }

    /*@Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mSingleton = this;

        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationOpenedHandler(this))
                .setNotificationReceivedHandler(new NotificationReceivedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

}
