package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.CouponsInteractor;
import us.globalpay.manhattan.interactors.CouponsListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.CouponsRequest;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.presenters.interfaces.ICouponsPresenter;
import us.globalpay.manhattan.utils.NavigatePlayStore;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.CouponsView;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsPresenter implements ICouponsPresenter, CouponsListener
{
    private static final String TAG = CouponsPresenter.class.getSimpleName();

    private Context mContext;
    private CouponsView mView;
    private CouponsInteractor mInteractor;
    private Gson mGson;

    public CouponsPresenter(Context context, AppCompatActivity activity, CouponsView couponsView)
    {
        this.mContext = context;
        this.mView = couponsView;
        this.mGson = new Gson();
        this.mInteractor = new CouponsInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        try
        {
            mView.initialize();
            String rawResponse = UserData.getInstance(mContext).getCouponsData();

            if(!TextUtils.isEmpty(rawResponse))
            {
                //Saves new data
                UserData.getInstance(mContext).saveCouponsData(rawResponse);

                //Deserializes response
                CouponsResponse deserialized = mGson.fromJson(rawResponse, CouponsResponse.class);

                //Pass data to view
                mView.renderCoupons(deserialized.getCupons().getCupons());
            }
            else
            {
                mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

    }

    @Override
    public void retrieveCoupons(int option, int storeId)
    {
        CouponsRequest request = new CouponsRequest();
        request.setOption(option);
        request.setStoreID(storeId);

        mInteractor.retrieveCoupons(request, this);
    }

    @Override
    public void onCoupons(JsonObject response)
    {
        try
        {
            mView.hideLoadingDialog();
            String rawResponse = mGson.toJson(response);

            if(!TextUtils.equals(rawResponse, UserData.getInstance(mContext).getCouponsData()))
            {
                //Saves new data
                UserData.getInstance(mContext).saveCouponsData(rawResponse);

                //Deserializes response
                CouponsResponse deserialized = mGson.fromJson(rawResponse, CouponsResponse.class);

                //Pass data to view
                mView.renderCoupons(deserialized.getCupons().getCupons());
            }


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

    @Override
    public void onPurchase(JsonObject result)
    {

    }

    @Override
    public void onPurchaseError(int codeStatus, Throwable throwable, String raw)
    {

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

                mView.showGenericDialog(dialog, new DialogInterface.OnClickListener()
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
                mView.showGenericDialog(dialog, new DialogInterface.OnClickListener()
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
