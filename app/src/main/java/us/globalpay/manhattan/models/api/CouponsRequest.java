package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsRequest
{
    @SerializedName("StoreID")
    @Expose
    private int storeID;
    @SerializedName("Option")
    @Expose
    private int option;

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
