package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.CouponsInteractor;
import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.BrandCouponsReq;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.presenters.interfaces.IBrandCouponsPresenter;
import us.globalpay.manhattan.utils.NavigatePlayStore;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.BrandsCouponsView;

/**
 * Created by Josué Chávez on 10/10/2018.
 */
public class BrandCouponsPresenter implements IBrandCouponsPresenter, CouponsListener
{
    private static final String TAG = BrandCouponsPresenter.class.getSimpleName();

    private Gson mGson;
    private Context mContext;
    private BrandsCouponsView mView;
    private CouponsInteractor mInteractor;

    public BrandCouponsPresenter(Context context, AppCompatActivity activity, BrandsCouponsView view)
    {
        this.mGson = new Gson();
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new CouponsInteractor(mContext);
    }

    @Override
    public void initialize(String categoryIcon)
    {
       try
       {
           mView.initialize();

           String serialized = UserData.getInstance(mContext).getSelectedBrand();
           Brand brand = mGson.fromJson(serialized, Brand.class);

           if(brand != null)
               mView.loadBrand(brand, categoryIcon);

       }
       catch (Exception ex)
       {
           Log.e(TAG, "Error: " + ex.getMessage());
       }
    }

    @Override
    public void retrieveCoupons(int brand)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_please_wait), true);
        BrandCouponsReq request = new BrandCouponsReq();
        request.setBrandID(5); //TODO: Caambiar por variable

        mInteractor.retrieveBrandsCoupons(request, this);
    }

    @Override
    public void onCoupons(JsonObject response)
    {
        try
        {
            mView.hideLoadingDialog();
            String rawResponse = mGson.toJson(response);

            //Deserializes response
            CouponsResponse deserialized = mGson.fromJson(rawResponse, CouponsResponse.class);

            //Pass data to view
            mView.renderCoupons(deserialized.getCupons().getCupons());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onCouponsError(int codeStatus, Throwable throwable, String raw)
    {
        mView.hideLoadingDialog();
        handleError(codeStatus, throwable, raw);
    }

    /*
     *
     *
     *
     *   CODE STATUS
     *
     *
     *
     * */

    private void handleError(int codeStatus, Throwable throwable, String raw)
    {
        try
        {
            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), raw);

                DialogModel dialog = new DialogModel();
                dialog.setTitle(title);
                dialog.setContent(content);
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                mView.showDialog(dialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        NavigatePlayStore.navigate(mContext);
                    }
                });
            }
            else
            {
                DialogModel dialog = new DialogModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setContent(mContext.getString(R.string.error_label_something_went_wrong));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showDialog(dialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }
}
