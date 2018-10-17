package us.globalpay.manhattan.utils.ui;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.utils.Constants;

/**
 * Created by Josué Chávez on 04/10/2018.
 */
public class ChestSelector
{
    private static final String TAG = ChestSelector.class.getSimpleName();

    private static Context mContext;
    private static ChestSelector singleton;

    //TODO: Añadir cofres 2D de marca
    private ChestSelector(Context context)
    {
        ChestSelector.mContext = context;
    }

    public static synchronized ChestSelector getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ChestSelector(context);
        }
        return singleton;
    }

    public HashMap<String, Integer> getGoldResource()
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();

            drawableClosed = getDrawableId("ic_gift");
            drawableOpen = getDrawableId("ic_splashscreen");
            resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
            resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getSilverResource()
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();


            drawableClosed = getDrawableId("ic_gift");
            drawableOpen = getDrawableId("ic_splashscreen");
            resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
            resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getBronzeResource()
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();

            drawableClosed = getDrawableId("ic_gift");
            drawableOpen = getDrawableId("ic_splashscreen");
            resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
            resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getWildcardResource()
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            /*switch (eraID)
            {
                case 1: //Vikings
                    resourceMap.clear();
                    drawableClosed = getDrawableId("img_wildcard_chest_2d_closed");
                    drawableOpen = getDrawableId("img_wildcard_chest_2d_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = getDrawableId("img_02_wildcard_chest_closed");
                    drawableOpen = getDrawableId("img_02_wildcard_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 3: //WorldCup
                    drawableClosed = getDrawableId("img_03_wildcard_chest_closed");
                    drawableOpen = getDrawableId("img_03_wildcard_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 4: //Acuatica
                    drawableClosed = getDrawableId("img_04_wildcard_chest_closed");
                    drawableOpen = getDrawableId("img_04_wildcard_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
            }*/

            drawableClosed = getDrawableId("img_04_wildcard_chest_closed");
            drawableOpen = getDrawableId("img_04_wildcard_chest_open");
            resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
            resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    private int getDrawableId(String resourceName)
    {
        int drawableId = 0;

        try
        {
            Class res = R.drawable.class;
            Field field = res.getField(resourceName);
            drawableId = field.getInt(null);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Failure to get drawable id.", e);
        }

        return drawableId;
    }
}
