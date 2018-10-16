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
import us.globalpay.manhattan.models.api.BrandsResponse;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.utils.Constants;

/**
 * Created by Josué Chávez on 06/10/2018.
 */
public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder>
{
    private static final String TAG = CouponsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Cupon> mCouponsList;
    private boolean mBrandCoupons;

    public CouponsAdapter(Context context, List<Cupon> couponsList, boolean fromBrandCoupons)
    {
        this.mContext = context;
        this.mCouponsList = couponsList;
        this.mBrandCoupons = fromBrandCoupons;
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

            //Unlocked item
            holder.imgCouponBgLocked.setVisibility(View.INVISIBLE);
            holder.ivExchangeType.setVisibility(View.INVISIBLE);
            holder.ivExchangeBg.setVisibility(View.INVISIBLE);

            if(mBrandCoupons)
            {
                if(!coupon.isUnlocked())
                {
                    holder.imgCouponBgLocked.setVisibility(View.VISIBLE);
                    holder.ivExchangeType.setVisibility(View.VISIBLE);
                    holder.ivExchangeBg.setVisibility(View.VISIBLE);

                    switch (coupon.getMethodID())
                    {
                        case Constants.EXCHANGE_METHOD_1_WILDCARD:
                            Glide.with(mContext).load(coupon.getUrlLogo()).into(holder.ivExchangeType);
                            break;
                        case Constants.EXCHANGE_METHOD_2_SCANNING:
                            Glide.with(mContext).load(coupon.getUrlLogo()).into(holder.ivExchangeType);
                            break;
                        case Constants.EXCHANGE_METHOD_3_TREASURE_HUNT:
                            Glide.with(mContext).load(coupon.getUrlLogo()).into(holder.ivExchangeType);
                            break;
                        case Constants.EXCHANGE_METHOD_4_COINS_EXCHANGE:
                            Glide.with(mContext).load(R.drawable.ic_exchange_type_gift).into(holder.ivExchangeType);
                            break;
                        case Constants.EXCHANGE_METHOD_6_CHEST:
                            Glide.with(mContext).load(R.drawable.ic_exchange_type_gift).into(holder.ivExchangeType);
                            break;
                        case Constants.EXCHANGE_METHOD_5_SHOPPING:
                            Glide.with(mContext).load(R.drawable.ic_exchange_type_coins).into(holder.ivExchangeType);
                            break;
                        default:
                            holder.ivExchangeType.setVisibility(View.INVISIBLE);
                            holder.ivExchangeBg.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
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
        ImageView ivExchangeType;
        ImageView imgCouponBgLocked;
        ImageView ivExchangeBg;
        TextView tvDescription;

        public CouponsViewHolder(View row)
        {
            super(row);
            ivCoupon = (ImageView) row.findViewById(R.id.ivBrandImage);
            ivExchangeType = (ImageView) row.findViewById(R.id.ivExchangeType);
            imgCouponBgLocked = (ImageView) row.findViewById(R.id.imgCouponBgLocked);
            ivExchangeBg = (ImageView) row.findViewById(R.id.ivExchangeBg);
            tvDescription = (TextView) row.findViewById(R.id.tvDescription);
        }
    }
}
