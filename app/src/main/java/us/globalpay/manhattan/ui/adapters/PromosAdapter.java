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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.api.Promo;

/**
 * Created by Josué Chávez on 09/10/2018.
 */
public class PromosAdapter extends RecyclerView.Adapter<PromosAdapter.PromosViewHolder>
{
    private static final String TAG = PromosAdapter.class.getSimpleName();

    private Context mContext;
    private List<Promo> mPromosList;

    public PromosAdapter(Context context, List<Promo> promoList)
    {
        this.mContext = context;
        this.mPromosList = promoList;
    }

    @NonNull
    @Override
    public PromosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_promo_item, parent, false);

        return new PromosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PromosViewHolder holder, int position)
    {
        try
        {
            final Promo item = mPromosList.get(position);

            DateFormat df = new SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", new Locale("es", "SV"));
            String date = df.format(Calendar.getInstance().getTime());

            String validDate = String.format(mContext.getString(R.string.label_promo_valid_date),date);

            Glide.with(mContext).load(item.getUrlLogo()).into(holder.logo);
            holder.title.setText(item.getTitle());
            holder.description.setText(item.getDescription());
            holder.date.setText(validDate);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mPromosList.size();
    }

    class PromosViewHolder extends RecyclerView.ViewHolder
    {
        ImageView logo;
        TextView title;
        TextView description;
        TextView date;

        public PromosViewHolder(View row)
        {
            super(row);

            logo = (ImageView) row.findViewById(R.id.ivPromoLogo);
            title = (TextView) row.findViewById(R.id.tvPromoTitle);
            description = (TextView) row.findViewById(R.id.tvPromoDescription);
            date = (TextView) row.findViewById(R.id.tvPromoValid);
        }
    }
}
