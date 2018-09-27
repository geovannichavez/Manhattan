package us.globalpay.manhattan.ui.activities;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.FavoriteCuppon;
import us.globalpay.manhattan.presenters.MainPresenter;
import us.globalpay.manhattan.ui.adapters.FavoriteCupponAdapter;
import us.globalpay.manhattan.utils.ButtonAnimator;
import us.globalpay.manhattan.views.MainView;

public class Main extends AppCompatActivity implements OnMapReadyCallback, MainView
{
    private static final String TAG = Main.class.getSimpleName();

    //Views
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView btnCuppons;
    private ImageView btnBrands;
    private ImageView btnToggleBar;
    private TextView tvCoinsCounter;
    private TextView tvBadge;
    private GridView gvCuppons;

    //Adapters and Layouts
    private FavoriteCupponAdapter mCupponsAdapter;
    private GoogleMap mGoogleMap;

    //MVP
    private MainPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggleBar = (ImageView) findViewById(R.id.btnToggleBar);
        btnCuppons = (ImageView) findViewById(R.id.btnCuppons);
        gvCuppons = (GridView) findViewById(R.id.gvCuppons);
        btnBrands = (ImageView) findViewById(R.id.btnBrands);
        tvCoinsCounter = (TextView) findViewById(R.id.tvCoinsCounter);
        tvBadge = (TextView) findViewById(R.id.tvBadge);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));

        mPresenter = new MainPresenter(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveData();

    }



    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;

        LatLng sydney = new LatLng(24.704697, 46.756808);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Ryhad"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void initialize()
    {
        btnCuppons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.animateButton(v);
            }
        });

        btnBrands.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.animateButton(v);
                navigateBrands();
            }
        });

        btnToggleBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (bottomSheetBehavior.getState())
                {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
            }
        });

        // Capturing the callbacks for bottom sheet
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(View bottomSheet, int newState)
            {

                if (newState == BottomSheetBehavior.STATE_EXPANDED)
                {
                    //Toast.makeText(Main.this, "text_collapse_me", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Toast.makeText(Main.this, "text_expand_me", Toast.LENGTH_LONG).show();
                }

                // Check Logs to see how bottom sheets behaves
                switch (newState)
                {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        btnToggleBar.setImageResource(R.drawable.ic_bottom_bar_up);
                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        btnToggleBar.setImageResource(R.drawable.ic_bottom_bar_down);
                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        btnToggleBar.setImageResource(R.drawable.ic_bottom_bar_down);
                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        btnToggleBar.setImageResource(R.drawable.ic_bottom_bar_up);
                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        btnToggleBar.setImageResource(R.drawable.ic_bottom_bar_up);
                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset)
            {

            }
        });
    }

    @Override
    public void renderMap()
    {
        try
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void renderCoupons()
    {
        mCupponsAdapter = new FavoriteCupponAdapter(this, R.layout.custom_promo_main_item);
        gvCuppons.setAdapter(mCupponsAdapter);

        List<FavoriteCuppon> list = new ArrayList<>();

        try
        {
            FavoriteCuppon cup1 = new FavoriteCuppon();
            cup1.setImgUrl("http://1.bp.blogspot.com/-_GSaDuoriIc/UaOzeBlLVkI/AAAAAAAABFY/W9MhFhIqB5M/s1600/almacenes-siman-logo-250x250.png");
            cup1.setDescription("15% de descuento en todas las sucursales");
            list.add(cup1);

            FavoriteCuppon cup2 = new FavoriteCuppon();
            cup2.setImgUrl("https://d3j72de684fey1.cloudfront.net/resized/d4097060ee1efd57273bd6c782402d448b688253.PjI1NngyNTY.png");
            cup2.setDescription("2x1 por compras de $50 o más");
            list.add(cup2);

            FavoriteCuppon cup3 = new FavoriteCuppon();
            cup3.setImgUrl("http://www.stickpng.com/assets/images/58429d58a6515b1e0ad75ae8.png");
            cup3.setDescription("Refill gratis al agrandar combo");
            list.add(cup3);

            for (FavoriteCuppon item : list)
            {
                mCupponsAdapter.add(item);
            }


        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void loadInitialValues(String coins, String promos)
    {
        tvCoinsCounter.setText(coins);
        tvBadge.setText(promos);
    }

    @Override
    public void navigateBrands()
    {
        Intent brands = new Intent(this, Brands.class);
        startActivity(brands);
    }
}
