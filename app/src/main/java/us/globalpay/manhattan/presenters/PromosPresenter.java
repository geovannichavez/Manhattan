package us.globalpay.manhattan.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.PromosInteractor;
import us.globalpay.manhattan.interactors.PromosListener;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.PromosRequest;
import us.globalpay.manhattan.models.api.PromosResponse;
import us.globalpay.manhattan.presenters.interfaces.IPromosPresenter;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.PromosView;

/**
 * Created by Josué Chávez on 08/10/2018.
 */
public class PromosPresenter implements IPromosPresenter, PromosListener
{
    private static final String TAG = PromosPresenter.class.getSimpleName();

    private Context mContext;
    private PromosView mView;
    private Gson mGson;
    private PromosInteractor mInteractor;

    public PromosPresenter(Context context, AppCompatActivity activity, PromosView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mGson = new Gson();
        this.mInteractor = new PromosInteractor(mContext);
    }

    @Override
    public void initialiaze()
    {
        mView.initialize();
        String savedSerialized = UserData.getInstance(mContext).getPromosData();

        if(!TextUtils.equals(savedSerialized, ""))
        {
            PromosResponse promos = mGson.fromJson(savedSerialized, PromosResponse.class);

            //Pass result to view
            mView.renderPromos(promos.getPromos().getPromos());
        }
        else
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
        }
    }

    @Override
    public void retrievePromos()
    {
        //mView.showLoadingDialog(mContext.getString(R.string.label_please_wait));
        PromosRequest request = new PromosRequest();
        request.setStoreID(1); //TODO: Añadir store
        mInteractor.retrievePromos(request, this);
    }

    @Override
    public void onPromosSucces(JsonObject response, PromosListener listener)
    {
       try
       {
           mView.hideLoadingDialog();

           String serialized = mGson.toJson(response);

           //if(!TextUtils.equals(serialized, UserData.getInstance(mContext).getPromosData()))
           //{
               //Saves new data
               UserData.getInstance(mContext).savePromosData(serialized);

               //Deserializes new data
               PromosResponse promos = mGson.fromJson(serialized, PromosResponse.class);

               //Pass result to view
               mView.renderPromos(promos.getPromos().getPromos());
           //}
       }
       catch (Exception ex)
       {
           Log.e(TAG, "Error: " + ex.getMessage());
       }

    }

    @Override
    public void onPromosError(int codeStatus, Throwable throwable, String raw)
    {
        try
        {
            mView.hideLoadingDialog();

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
                        //TODO: Usar metodo prefbrcado para enviar a la play store
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
