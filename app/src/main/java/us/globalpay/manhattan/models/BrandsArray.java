package us.globalpay.manhattan.models;

import java.util.HashSet;

import us.globalpay.manhattan.models.api.Brand;

/**
 * Created by Josué Chávez on 26/10/2018.
 */
public class BrandsArray
{
    private HashSet<Brand> array;

    public HashSet<Brand> getArray()
    {
        return array;
    }

    public void setArray(HashSet<Brand> array)
    {
        this.array = array;
    }
}
