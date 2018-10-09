package us.globalpay.manhattan.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.api.Cupon;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder>
{
    private static final String TAG = CouponsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Cupon> mCouponsList;

    public CouponsAdapter(Context context, List<Cupon> couponsList)
    {
        this.mContext = context;
        this.mCouponsList = couponsList;
    }


    @NonNull
    @Override
    public CouponsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_coupon_gridview_item, parent, false);

        return new CouponsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponsViewHolder holder, int position)
    {
        try
        {
            final Cupon coupon = mCouponsList.get(position);

            Glide.with(mContext).load(coupon.getUrlLogo()).into(holder.ivCoupon);
            holder.tvDescription.setText(coupon.getTitle());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mCouponsList.size();
    }

    class CouponsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivCoupon;
        TextView tvDescription;

        public CouponsViewHolder(View row)
        {
            super(row);
            ivCoupon = (ImageView) row.findViewById(R.id.ivBrandImage);
            tvDescription = (TextView) row.findViewById(R.id.tvDescription);
        }
    }
}
