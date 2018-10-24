package us.globalpay.manhattan.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Cupon;
import us.globalpay.manhattan.presenters.MainPresenter;
import us.globalpay.manhattan.ui.adapters.FavoriteCupponAdapter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.views.MainView;

public class Main extends AppCompatActivity implements OnMapReadyCallback, MainView
{
    private static final String TAG = Main.class.getSimpleName();

    //Views
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView btnCuppons;
    private ImageView btnBrands;
    private ImageView btnToggleBar;
    private ImageView btnCamera;
    private ImageView ivPromoAlert;
    private TextView tvCoinsCounter;
    private TextView tvBadge;
    private GridView gvCuppons;

    //Adapters and Layouts
    private FavoriteCupponAdapter mCupponsAdapter;
    private GoogleMap mGoogleMap;

    //Global variables
    final private int REQUEST_ACCESS_FINE_LOCATION = 3;
    private int mViewUpdatesCounter = 0;

    private Map<String, Marker> mGoldPointsMarkers;
    private Map<String, Marker> mSilverPointsMarkers;
    private Map<String, Marker> mBronzePointsMarkers;
    private Map<String, Marker> mWildcardPointsMarkers;
    private Map<String, Bitmap> mBitmapMarkers;

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
        btnCamera = (ImageView) findViewById(R.id.btnCamera);
        ivPromoAlert = (ImageView) findViewById(R.id.ivPromoAlert);
        tvCoinsCounter = (TextView) findViewById(R.id.tvCoinsCounter);
        tvBadge = (TextView) findViewById(R.id.tvBadge);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));

        mGoldPointsMarkers = new HashMap<>();
        mSilverPointsMarkers = new HashMap<>();
        mBronzePointsMarkers = new HashMap<>();
        mWildcardPointsMarkers = new HashMap<>();
        mBitmapMarkers = new HashMap<>();

        mPresenter = new MainPresenter(this, this, this);
        mPresenter.checkUserDataCompleted();
        mPresenter.initialize();
        mPresenter.retrieveData();
        mPresenter.chekcLocationServiceEnabled();

    }



    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mPresenter.checkPermissions();

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);

        try
        {
            mGoogleMap.setMyLocationEnabled(true);
        }
        catch (SecurityException ex)
        {
            ex.printStackTrace();
        }

        LatLng sydney = new LatLng(24.704697, 46.756808);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Ryhad"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mPresenter.onMapReady();
    }

    @Override
    public void initialize()
    {
        btnCuppons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.shadowButton(v);
                navigateCoupons();
            }
        });

        btnBrands.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.shadowButton(v);
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

        //Camera
        btnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent ar = new Intent(Main.this, AR.class);
                NavFlagsUtil.addFlags(ar);
                startActivity(ar);
                finish();
            }
        });

        //Promos
        ivPromoAlert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(Main.this, v);
                Intent promos = new Intent(Main.this, Promos.class);
                NavFlagsUtil.addFlags(promos);
                startActivity(promos);
                finish();
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
    public void renderCoupons(List<Cupon> coupons)
    {
        try
        {
            mCupponsAdapter = new FavoriteCupponAdapter(this, R.layout.custom_promo_main_item);
            gvCuppons.setAdapter(mCupponsAdapter);
            gvCuppons.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Cupon selected = (Cupon) parent.getItemAtPosition(position);
                    mPresenter.saveSelectedCoupon(selected);
                    Intent details = new Intent(Main.this, CouponDetail.class);
                    NavFlagsUtil.addFlags(details);
                    startActivity(details);
                    finish();
                }
            });

            mCupponsAdapter.notifyDataSetChanged();

            for(Cupon item : coupons)
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
    public void displayActivateLocationDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.dialog_title_activate_location));
        alertDialog.setMessage(getString(R.string.dialog_content_activate_location));
        alertDialog.setCancelable(false);
        alertDialog.setNeutralButton(getString(R.string.button_activate), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void navigateBrands()
    {
        Intent brands = new Intent(this, Brands.class);
        startActivity(brands);
    }

    @Override
    public void navigateCoupons()
    {
        Intent coupons = new Intent(this, Coupons.class);
        NavFlagsUtil.addFlags(coupons);
        startActivity(coupons);
        finish();
    }

    @Override
    public void checkPermissions()
    {
        try
        {
            int checkFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int checkCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


            if (checkFineLocationPermission != PackageManager.PERMISSION_GRANTED && checkCoarseLocationPermission != PackageManager.PERMISSION_GRANTED)
            {
                if(Build.VERSION.SDK_INT >= 23)
                {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                            !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main.this);
                        alertDialog.setTitle(getString(R.string.dialog_permissions_title));
                        alertDialog.setMessage(getString(R.string.dialog_permissions_location_content));
                        alertDialog.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                        alertDialog.show();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                }
            }
            else
            {
                mPresenter.connnectToLocationService();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateUserLocationOnMap(Location location)
    {
        try
        {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if(mViewUpdatesCounter <= 0)
            {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mViewUpdatesCounter = mViewUpdatesCounter + 1;
            }

            mPresenter.updatePrizePntCriteria(currentLocation);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setInitialUserLocation(Location location)
    {
        try
        {
            mPresenter.intializeGeolocation();

            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(Constants.GOOGLE_MAPS_ZOOM_CAMERA).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mPresenter.prizePointsQuery(currentLocation);

            //mPresenter.checkWelcomeChest(pLocation);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showDialog(DialogModel model, DialogInterface.OnClickListener clickListener)
    {
        DialogGenerator.showDialog(this, model, clickListener);
    }

    @Override
    public void addGoldPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);

            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {

                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gold_point)));


                mGoldPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Gold point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addGoldPointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeGoldPoint(String pKey)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);
            marker.remove();
            mGoldPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSilverPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_silver_point)));

                mSilverPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Silver point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addSilverPointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeSilverPoint(String pKey)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            marker.remove();
            mSilverPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addBronzePoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_bronze_point)));

                mBronzePointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Bronze point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addBronzePointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeBronzePoint(String pKey)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            marker.remove();
            mBronzePointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addWildcardPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                Bitmap wildcardMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_WILDCARD);

                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gold_point)));
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Wildcard point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addWildcardPointData(String pKey, String brand, String title, String message)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            marker.setTitle(title);
            marker.setSnippet(message);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeWildcardPoint(String pKey)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            marker.remove();
            mWildcardPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
