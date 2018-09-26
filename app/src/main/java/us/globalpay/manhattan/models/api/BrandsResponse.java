package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class BrandsResponse
{
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("categories")
    @Expose
    private Categories categories;

    public String get$id()
    {
        return $id;
    }

    public void set$id(String $id)
    {
        this.$id = $id;
    }

    public Categories getCategories()
    {
        return categories;
    }

    public void setCategories(Categories categories)
    {
        this.categories = categories;
    }
}
