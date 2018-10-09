package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class PromosRequest
{

    @SerializedName("StoreID")
    @Expose
    private int storeID;

    public int getStoreID()
    {
        return storeID;
    }

    public void setStoreID(int storeID)
    {
        this.storeID = storeID;
    }

}
