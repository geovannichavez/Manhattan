package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Promo;
import us.globalpay.manhattan.models.api.PromosResponse;
import us.globalpay.manhattan.presenters.PromosPresenter;
import us.globalpay.manhattan.ui.adapters.PromosAdapter;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.views.PromosView;

public class Promos extends AppCompatActivity implements PromosView
{
    private static final String TAG = Promos.class.getSimpleName();

    private ImageView ivBackground;
    private ImageView btnBack;
    private ImageView spLocation;
    private TextView tvPromosDate;
    private TextView tvLocation;
    private RecyclerView lvPromos;
    private ProgressDialog mProgressDialog;

    private PromosAdapter mAdapter;

    private PromosPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promos);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        spLocation = (ImageView) findViewById(R.id.spLocation);
        lvPromos = (RecyclerView) findViewById(R.id.lvPromos);
        tvPromosDate = (TextView) findViewById(R.id.tvPromosDate);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        mPresenter = new PromosPresenter(this, this, this);
        mPresenter.initialiaze();
        mPresenter.retrievePromos();
    }

    @Override
    public void initialize()
    {
        View toolbar = findViewById(R.id.toolbarPromos);
        ImageView ivTitleIcon = toolbar.findViewById(R.id.ivTitleIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.lblTitle);
        ivTitleIcon.setImageResource(R.drawable.ic_promo_icon);
        title.setText(getString(R.string.title_activity_promos));

        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);

        btnBack = toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(backListener);

        spLocation.setOnClickListener(spinnerClick);

        tvPromosDate.setText(String.format(getString(R.string.label_promos_of_day), getTodaysDate()));
    }

    @Override
    public void renderPromos(List<Promo> promos)
    {
        try
        {
            mAdapter = new PromosAdapter(this, promos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());

            lvPromos.setLayoutManager(layoutManager);
            lvPromos.setItemAnimator(new DefaultItemAnimator());
            lvPromos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            lvPromos.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String label)
    {
        mProgressDialog = DialogGenerator.showProgressDialog(this, label, false);
    }

    @Override
    public void hideLoadingDialog()
    {
        DialogGenerator.dismissProgressDialog(mProgressDialog);
    }

    @Override
    public void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener)
    {
        DialogGenerator.showDialog(this, model, clickListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.shadowButton(v);
            navigateBack();
        }
    };

    private View.OnClickListener spinnerClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.floatingButton(Promos.this, v);
        }
    };

    private void navigateBack()
    {
        Intent back = new Intent(Promos.this, Main.class);
        NavFlagsUtil.addFlags(back);
        startActivity(back);
        finish();
    }

    private String getTodaysDate()
    {
        String date = "";
        try
        {
            DateFormat df = new SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", new Locale("es", "SV"));
            date = df.format(Calendar.getInstance().getTime());
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage());    }
        return date;
    }
}
