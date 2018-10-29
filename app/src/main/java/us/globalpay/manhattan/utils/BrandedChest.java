package us.globalpay.manhattan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Random;

import us.globalpay.manhattan.models.BrandsArray;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.MainDataResponse;

/**
 * Created by Josué Chávez on 26/10/2018.
 */
public class BrandedChest
{
    private static final String TAG = BrandedChest.class.getSimpleName();

    private Context mContext;
    private Random mRandomGenerator;
    private BrandsArray mBrandsArray;

    public BrandedChest(Context context)
    {
        mRandomGenerator = new Random();
        this.mContext = context;

        Gson gson = new Gson();
        MainDataResponse deserializedData = gson.fromJson(UserData.getInstance(mContext).getHomeData(), MainDataResponse.class);

        if(deserializedData != null)
        {
            HashSet<Brand> brandsArray = new HashSet<>();
            brandsArray.addAll(deserializedData.getData().getBrand());

            mBrandsArray = new BrandsArray();
            mBrandsArray.setArray(brandsArray);
        }
    }

    public Bitmap getRandomBrandBitmap()
    {
        Bitmap brand = null;

        try
        {
            int index = mRandomGenerator.nextInt(mBrandsArray.getArray().size());
            Object[] brands = mBrandsArray.getArray().toArray();

            Brand brandItem = (Brand) brands[index];

            brand = BitmapUtils.retrieve(mContext, brandItem.getName());

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

        return brand;

    }

}
