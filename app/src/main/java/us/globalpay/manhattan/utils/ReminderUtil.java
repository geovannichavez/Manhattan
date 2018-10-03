package us.globalpay.manhattan.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by Josué Chávez on 03/10/2018.
 */
public class ReminderUtil
{
    private static final String TAG = ReminderUtil.class.getSimpleName();

    private static Context mContext;
    private static ReminderUtil mSingleton;

    private ReminderUtil(Context context)
    {
        ReminderUtil.mContext = context;
    }

    public static synchronized ReminderUtil with(Context context)
    {
        if (mSingleton == null)
        {
            mSingleton = new ReminderUtil(context);
        }
        return mSingleton;
    }

    public void addCouponReminder(Reminder reminder)
    {
        try
        {
            Intent calIntent = new Intent(Intent.ACTION_INSERT);
            calIntent.setData(CalendarContract.Events.CONTENT_URI);
            calIntent.setType("vnd.android.cursor.item/event");
            calIntent.putExtra(CalendarContract.Events.TITLE,  reminder.getTitle());
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, reminder.getEventLocation());
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, reminder.getDescription());

            GregorianCalendar calDate = new GregorianCalendar(reminder.getYear(), reminder.getMonth(), reminder.getDayOfMonth());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());

            mContext.startActivity(calIntent);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }



}
