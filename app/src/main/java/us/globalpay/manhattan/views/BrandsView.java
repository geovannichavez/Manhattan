package us.globalpay.manhattan.views;

import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;

/**
 * Created by Josué Chávez on 18/09/2018.
 */
public interface BrandsView
{
    void initialize(String storeName);
    void renderBrands(List<Category> categories, Map<String, List<Brand>> filledCategories);
    void showLoadingDialog(String string, boolean isCancelable);
    void hideLoadingDialog();
    void showGenericDialog(DialogModel dialog);
}
