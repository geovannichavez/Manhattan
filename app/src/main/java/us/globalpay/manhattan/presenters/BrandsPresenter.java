package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.BrandsInteractor;
import us.globalpay.manhattan.interactors.BrandsListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.BrandsReqBody;
import us.globalpay.manhattan.models.api.BrandsResponse;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.presenters.interfaces.IBrandsPresenter;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.BrandsView;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class BrandsPresenter implements IBrandsPresenter, BrandsListener
{
    private static final String TAG = BrandsPresenter.class.getSimpleName();

    private Context mContext;
    private BrandsView mView;
    private BrandsInteractor mInteractor;

    List<Category> mGroupList;
    List<Brand> mChildList;

    public BrandsPresenter(Context context, AppCompatActivity activity, BrandsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new BrandsInteractor(mContext);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void retrieveBrands()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));

        BrandsReqBody request = new BrandsReqBody();
        request.setStoreID(1); //TODO: Cambiar ID quemado
        mInteractor.retrieveBrands(request, this);
    }

    @Override
    public void onSuccess(BrandsResponse response)
    {
        mView.hideLoadingDialog();

        // Declare empty LinkedHashMap
        Map<String, List<Brand>> filledCategories = new LinkedHashMap<>();

        //Fills linkedHashMap with items to corresponding category
        for(Category cat : response.getCategories().getCategories())
        {
            filledCategories.put(cat.getName(), cat.getBrands());
        }

        // Passes lists with results to view
        mView.renderBrands(response.getCategories().getCategories(), filledCategories);
    }

    @Override
    public void onError(int codeStatus, Throwable throwable, String rawResponse)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), rawResponse);

                DialogModel dialog = new DialogModel();
                dialog.setTitle(title);
                dialog.setContent(content);
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                mView.showGenericDialog(dialog);
            }
            else
            {
                DialogModel dialog = new DialogModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setContent(mContext.getString(R.string.error_label_something_went_wrong));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(dialog);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

}
