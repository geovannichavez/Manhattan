package us.globalpay.manhattan.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import us.globalpay.manhattan.ui.activities.Main;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
{
    private static final String TAG = NotificationOpenedHandler.class.getSimpleName();
    private Context mContext;

    public NotificationOpenedHandler(Context context)
    {
        mContext = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result)
    {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;

        if (data != null)
        {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.i(TAG, "customkey set with value: " + customKey);
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i(TAG, "Button pressed with id: " + result.action.actionID);

        Intent intent = new Intent(mContext, Main.class); //TODO: Cambiar activity de notificaciones
        intent.putExtra(Constants.NOTIFICATION_TITLE_EXTRA, result.notification.payload.title);
        intent.putExtra(Constants.NOTIFICATION_BODY_EXTRA, result.notification.payload.body);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }
}
