package us.globalpay.manhattan.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.FavoriteCuppon;
import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 11/09/2018.
 */
public class FavoriteCupponAdapter extends ArrayAdapter<Cupon>
{
    private static final String TAG = FavoriteCupponAdapter.class.getSimpleName();

    private Context mContext;
    private int mResource;


    public FavoriteCupponAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        final Cupon currentItem = getItem(position);

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mResource, parent, false);
        }

        row.setTag(currentItem);

        ImageView ivCupponImage = (ImageView) row.findViewById(R.id.ivCupponImage);
        TextView tvCupponDescription = (TextView) row.findViewById(R.id.tvCupponDescription);

        Glide.with(mContext).load(currentItem.getUrlLogo()).into(ivCupponImage);
        tvCupponDescription.setText(currentItem.getTitle());


        return row;
    }
}
