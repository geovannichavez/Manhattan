package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 27/09/2018.
 */
public class MainDataResponse
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("data")
    @Expose
    private Data data;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
