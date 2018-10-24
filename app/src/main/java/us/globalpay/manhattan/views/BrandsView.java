package us.globalpay.manhattan.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.utils.interfaces.IActionResult;

/**
 * Created by Josué Chávez on 18/09/2018.
 */
public interface BrandsView
{
    void initialize();
    void renderBrands(List<Category> categories, Map<String, List<Brand>> filledCategories);
    void showLoadingDialog(String string, boolean isCancelable);
    void hideLoadingDialog();
    void showGenericDialog(DialogModel dialog);
    void showListDialog(HashMap<String, ?> arrayMap, IActionResult actionResult);
    void setStoreName(String name);
}
