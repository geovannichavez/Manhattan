package us.globalpay.manhattan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Josué Chávez on 25/10/2018.
 */
public class BitmapUtils
{
    private static final String TAG = BitmapUtils.class.getSimpleName();

    public static void save(Context context, Bitmap bitmap, String name)
    {
        File directory = context.getFilesDir();
        File existingFile = new File(directory, name);

        if (!existingFile.exists())
        {
            try
            {
                FileOutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                Log.i(TAG, "Bitmap saved!");
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error while saving bitmap: " + e.getMessage());
            }
        }
    }

    public static Bitmap retrieve(Context context, String chestName)
    {
        Bitmap bitmap = null;

        try
        {
            File directory = context.getFilesDir();
            File existingFile = new File(directory, chestName);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(existingFile), null, options);
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "FileNotFoundException: " + e.getLocalizedMessage());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving bitmap: " + ex.getMessage());
        }

        return bitmap;

    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        }
        else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap scaleBrandIcon(Bitmap brandBitmap, int maxWidth, int maxHeight)
    {
        float scale;
        int newSize;
        Bitmap scaledBitmap;

        if (brandBitmap.getHeight() > brandBitmap.getWidth())
        {
            scale = (float) maxWidth / brandBitmap.getHeight();
            newSize = Math.round(brandBitmap.getWidth() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(brandBitmap, newSize, maxWidth, true);
        }
        else if (brandBitmap.getHeight() == brandBitmap.getWidth())
        {
            float ratioBitmap = (float) brandBitmap.getWidth() / (float) brandBitmap.getHeight();
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;

            if (ratioMax > ratioBitmap)
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            else
                finalHeight = (int) ((float) maxWidth / ratioBitmap);

            scaledBitmap = Bitmap.createScaledBitmap(brandBitmap, finalWidth, finalHeight, true);
        }
        else
        {
            scale = (float) maxHeight / brandBitmap.getWidth();
            newSize = Math.round(brandBitmap.getHeight() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(brandBitmap, maxHeight, newSize, true);
        }

        return scaledBitmap;
    }

    public static Bitmap scaleMarker(Bitmap marker, Context context)
    {
        int maxWidth = Constants.MARKER_MAX_WIDTH_DP_SIZE;
        int maxHeight = Constants.MARKER_MAX_HEIGHT_DP_SIZE;

        int widthDp = MetricsUtils.dpFromPixels(context, marker.getWidth());
        int heightDp = MetricsUtils.dpFromPixels(context, marker.getHeight());


        float ratioBitmap = (float) widthDp / (float) heightDp;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;


        if (ratioMax > ratioBitmap)
        {
            finalWidth = (int) ((float) maxHeight * ratioBitmap);
        }
        else
        {
            finalHeight = (int) ((float) maxWidth / ratioBitmap);
        }

        marker = Bitmap.createScaledBitmap(marker, MetricsUtils.pixelsFromDp(context, finalWidth),
                MetricsUtils.pixelsFromDp(context, finalHeight), true);
        return marker;
    }


    public static Bitmap getSponsoredMarker(Context context, Bitmap scaledMarker, Bitmap sponsor)
    {
        Bitmap finalObject = null;

        try
        {
            //Creates layer
            BitmapDrawable marker = new BitmapDrawable(context.getResources(), scaledMarker);
            BitmapDrawable spnsor = new BitmapDrawable(context.getResources(), sponsor);

            Drawable[] layers = {marker, spnsor};
            LayerDrawable drawableLayered = new LayerDrawable(layers);


            int paddingLeft = scaledMarker.getWidth() - sponsor.getWidth();
            int paddingTop = scaledMarker.getHeight() - sponsor.getHeight();

            drawableLayered.setLayerInset(1, paddingLeft, //left
                    paddingTop, //top
                    0,       //right
                    0);     //bottom*/

            finalObject =  BitmapUtils.drawableToBitmap(drawableLayered);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return finalObject;
    }

}
