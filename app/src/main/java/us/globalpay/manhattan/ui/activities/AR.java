package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.ChestData2D;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.presenters.ARPresenter;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.ui.ChestSelector;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.ARView;

public class AR extends AppCompatActivity implements ARView, ArchitectJavaScriptInterfaceListener
{
    private static final String TAG = AR.class.getSimpleName();

    //Views
    private ImageView btnBack;
    private ImageView ivCoinsCounter;
    private ImageView ivPrize;
    private ImageView ivCoinsMetter;
    private TextView tvCoinsCounter;
    private ProgressDialog mProgressDialog;
    Toast mToast;

    //MVP
    private ARPresenter mPresenter;

    //Wikitude
    private ArchitectView architectView;

    //Global variables
    Animation mAnimation;
    HashMap<String, ChestData2D> mFirbaseObjects;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);

        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(getString(R.string.m4nh_0310018_315_jswikt_th4n_lck));
        this.architectView.onCreate(config);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        ivCoinsCounter = (ImageView) findViewById(R.id.ivCoinsCounter);
        ivPrize = (ImageView) findViewById(R.id.ivPrize);
        ivCoinsMetter = (ImageView) findViewById(R.id.ivCoinsMetter);
        tvCoinsCounter = (TextView) findViewById(R.id.tvCoinsCounter);

        mAnimation = new AlphaAnimation(1, 0);
        mFirbaseObjects = new HashMap<>();
        mHandler = new Handler();

        mPresenter = new ARPresenter(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveUserTracking();


        try
        {
            architectView.addArchitectJavaScriptInterfaceListener(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        this.architectView.onPostCreate();
        try
        {
            this.architectView.load("index.html");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserLocation(double latitude, double longitude, double accuracy)
    {
        try
        {
            LatLng location = new LatLng(latitude, longitude);
            mPresenter.updateChestPntCriteria(location);
            architectView.setLocation(latitude, longitude, accuracy);
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void locationManagerConnected(double latitude, double longitude, double accuracy)
    {
        try
        {
            LatLng location = new LatLng(latitude, longitude);
            this.mPresenter.chestPointsQuery(location);
            architectView.setLocation(latitude, longitude, accuracy);
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void setClickListeners()
    {
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.backButton(AR.this, v);
                navigateBack();
            }
        });

        ivCoinsMetter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(AR.this, v);
                mPresenter.redeemPrize();
            }
        });
    }

    //Wikitude listener
    @Override
    public void onJSONObjectReceived(JSONObject jsonObject)
    {
        mPresenter.openChest(jsonObject);
    }

    @Override
    public void on2DChestClick()
    {
        ivPrize.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.shadowButton(v);
                mPresenter.handle2DCoinTouch();
            }
        });
    }

    @Override
    public void hideArchViewNoContainersMsg()
    {
        this.architectView.callJavascript("World.modelLoaded()");
    }

    @Override
    public void showGenericDialog(DialogModel messageModel, DialogInterface.OnClickListener clickListener)
    {
        DialogGenerator.showDialog(this, messageModel, clickListener);
    }


    @Override
    public void showLoadingDialog(final String content)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mProgressDialog = DialogGenerator.showProgressDialog(AR.this, content, true);
            }
        });
    }

    @Override
    public void hideLoadingDialog()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
                catch (Exception ex) {  ex.printStackTrace();   }
            }
        });
    }

    @Override
    public void switchChestVisible(boolean isVisible)
    {
        int visible = (isVisible) ? View.VISIBLE : View.GONE;
        ivPrize.setVisibility(visible);
    }

    @Override
    public void makeChestBlink()
    {
        mAnimation.setDuration(300);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        ivPrize.startAnimation(mAnimation);
    }

    @Override
    public void showToast(String text)
    {
        if(mToast != null)
        {
            mToast.cancel();
        }
        mToast = Toast.makeText(AR.this, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void removeBlinkingAnimation()
    {
        ivPrize.clearAnimation();
    }

    @Override
    public void on2DChestTouch(int await)
    {
        removeBlinkingAnimation();

        try
        {
            //Gets the first object found
            Map.Entry<String, ChestData2D> entry = mFirbaseObjects.entrySet().iterator().next();
            ChestData2D chestData = entry.getValue();

            if(chestData.getChestType() != Constants.VALUE_CHEST_TYPE_WILDCARD)
                changeToOpenChest(chestData.getChestType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        mHandler.postDelayed(runCoinExchange, await);
    }

    @Override
    public void removeRunnableCallback()
    {
        mHandler.removeCallbacks(runCoinExchange);
    }

    @Override
    public void deleteModelAR()
    {
        try
        {
            this.architectView.callJavascript("World.deleteObjectGeo()");
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void updatePrizeButton(int coins)
    {
        int coinsButton;
        coinsButton = R.drawable.btn_coins_metter_0;

        switch (coins)
        {
            case 0:
                coinsButton = R.drawable.btn_coins_metter_0;
                break;
            case 1:
                coinsButton = R.drawable.btn_coins_metter_1;
                break;
            case 2:
                coinsButton = R.drawable.btn_coins_metter_2;
                break;
            case 3:
                coinsButton = R.drawable.btn_coins_metter_3;
                break;
            case 4:
                coinsButton = R.drawable.btn_coins_metter_4;
                break;
            case 5:
                coinsButton = R.drawable.btn_coins_metter_5;
                break;
            case 6:
                coinsButton = R.drawable.btn_coins_metter_6;
                break;
            case 7:
                coinsButton = R.drawable.btn_coins_metter_7;
                break;
            case 8:
                coinsButton = R.drawable.btn_coins_metter_8;
                break;
            case 9:
                coinsButton = R.drawable.btn_coins_metter_9;
                break;
            case 10:
                coinsButton = R.drawable.btn_coins_metter_10;
                break;
            case 11:
                coinsButton = R.drawable.btn_coins_metter_11;
                break;
            case 12:
                coinsButton = R.drawable.btn_coins_metter_12;
                break;
            case 13:
                coinsButton = R.drawable.btn_coins_metter_13;
                break;
            case 14:
                coinsButton = R.drawable.btn_coins_metter_14;
                break;
            case 15:
                coinsButton = R.drawable.btn_coins_metter_15;
                break;
            case 16:
                coinsButton = R.drawable.btn_coins_metter_16;
                break;
            case 17:
                coinsButton = R.drawable.btn_coins_metter_17;
                break;
            case 18:
                coinsButton = R.drawable.btn_coins_metter_18;
                break;
            case 19:
                coinsButton = R.drawable.btn_coins_metter_19;
                break;
            case 20:
                coinsButton = R.drawable.btn_coins_metter_20;
                break;
            default:
                coinsButton = R.drawable.btn_coins_metter_0;
                break;
        }

        ivCoinsMetter.setImageResource(coinsButton);

        if(coins != 20)
            tvCoinsCounter.setText(String.valueOf(coins));
        else
            tvCoinsCounter.setText("");
    }

    @Override
    public void onGoldKeyEntered(String key, LatLng location, String folderName)
    {
        if(UserData.getInstance(this).deviceFullCompatible())
        {
            this.architectView.callJavascript("World.createModelGoldAtLocation(" +
                    location.latitude + ", " + location.longitude + ", '" + key + "', '" + folderName + "')");
        }
        else
        {
            mPresenter.registerKeyEntered(key, location, Constants.NAME_CHEST_TYPE_GOLD);
        }
    }

    @Override
    public void onGoldKeyExited(String pKey)
    {
        this.architectView.callJavascript("World.deleteObjectGeo()");
    }

    @Override
    public void onSilverKeyEntered(String key, LatLng location, String folderName)
    {
        if(UserData.getInstance(this).deviceFullCompatible())
        {
            this.architectView.callJavascript("World.createModelSilverAtLocation(" +
                    location.latitude + ", " + location.longitude + ", '" + key + "', '" + folderName + "')");
        }
        else
        {
            mPresenter.registerKeyEntered(key, location, Constants.NAME_CHEST_TYPE_SILVER);
        }
    }

    @Override
    public void onSilverKeyExited(String pKey)
    {
        this.architectView.callJavascript("World.deleteObjectGeo()");
    }

    @Override
    public void onBronzeKeyEntered(String key, LatLng location, String folderName)
    {
        if(UserData.getInstance(this).deviceFullCompatible())
        {
            this.architectView.callJavascript("World.createModelBronzeAtLocation(" +
                    location.latitude + ", " + location.longitude + ", '" + key + "', '" + folderName + "')");
        }
        else
        {
            mPresenter.registerKeyEntered(key, location, Constants.NAME_CHEST_TYPE_BRONZE);
        }
    }

    @Override
    public void onBronzeKeyExited(String pKey)
    {
        this.architectView.callJavascript("World.deleteObjectGeo()");
    }


    @Override
    public void onWildcardKeyEntered(String key, LatLng location, String folderName)
    {
        if(UserData.getInstance(this).deviceFullCompatible())
        {
            this.architectView.callJavascript("World.createModelWildcardAtLocation(" +
                    location.latitude + ", " + location.longitude + ", '" + key + "', '" + folderName + "')");
        }
        else
        {
            mPresenter.registerKeyEntered(key, location, Constants.NAME_CHEST_TYPE_WILDCARD);
        }
    }

    @Override
    public void onWildcardKeyExited(String pKey)
    {
        this.architectView.callJavascript("World.deleteObjectGeo()");
    }

    @Override
    public void changeToOpenChest(int pChestType)
    {
        int resourceID = 0;

        switch (pChestType)
        {
            case Constants.VALUE_CHEST_TYPE_GOLD:

                resourceID = ChestSelector.getInstance(this).getGoldResource().get(Constants.CHEST_STATE_OPEN);
                Glide.with(this).load(resourceID).into(ivPrize);
                break;
            case Constants.VALUE_CHEST_TYPE_SILVER:
                resourceID = ChestSelector.getInstance(this).getSilverResource().get(Constants.CHEST_STATE_OPEN);
                Glide.with(this).load(resourceID).into(ivPrize);
                break;
            case Constants.VALUE_CHEST_TYPE_BRONZE:
                resourceID = ChestSelector.getInstance(this).getBronzeResource().get(Constants.CHEST_STATE_OPEN);
                Glide.with(this).load(resourceID).into(ivPrize);
                break;
        }
    }

    @Override
    public void navigateToWildcard()
    {
        //TODO: Navigate to wildcard
    }

    @Override
    public void navigateToCouponDetails()
    {
        Intent couponDetails = new Intent(this, CouponDetail.class);
        NavFlagsUtil.addFlags(couponDetails);
        startActivity(couponDetails);
        finish();
    }

    @Override
    public void setEnabledChestImage(boolean enabled)
    {
        ivPrize.setEnabled(enabled);
    }


    @Override
    public void drawChest2D(String pKey, LatLng pLocation, int chestTypeValue)
    {
        try
        {
            ChestData2D data = new ChestData2D();
            data.setLocation(pLocation);
            data.setChestType(chestTypeValue);

            mFirbaseObjects.clear();
            mFirbaseObjects.put(pKey, data);

            int resourceID = 0;

            switch (chestTypeValue)
            {
                case Constants.VALUE_CHEST_TYPE_GOLD:

                    resourceID = ChestSelector.getInstance(this).getGoldResource().get(Constants.CHEST_STATE_CLOSED);
                    Glide.with(this).load(resourceID).into(ivPrize);
                    break;
                case Constants.VALUE_CHEST_TYPE_SILVER:
                    resourceID = ChestSelector.getInstance(this).getSilverResource().get(Constants.CHEST_STATE_CLOSED);
                    Glide.with(this).load(resourceID).into(ivPrize);
                    break;
                case Constants.VALUE_CHEST_TYPE_BRONZE:
                    resourceID = ChestSelector.getInstance(this).getBronzeResource().get(Constants.CHEST_STATE_CLOSED);
                    Glide.with(this).load(resourceID).into(ivPrize);
                    break;
            }

            Glide.with(this).load(resourceID).into(ivPrize);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error drwing gold chest 2d: " +  ex.getMessage());
        }
    }


    Runnable runCoinExchange = new Runnable()
    {
        @Override
        public void run()
        {

            try
            {
                Map.Entry<String, ChestData2D> entry = mFirbaseObjects.entrySet().iterator().next();
                String firebaseID = entry.getKey();
                ChestData2D chestData = entry.getValue();

                if(chestData.getChestType() != Constants.VALUE_CHEST_TYPE_WILDCARD)
                {
                    //Atempt to exchange chest
                    mPresenter.open2DChest(chestData.getLocation(), firebaseID, chestData.getChestType());
                }
                else
                {
                    //Wildcard touched!
                    mPresenter.touchWildcard_2D(firebaseID, Constants.VALUE_CHEST_TYPE_WILDCARD);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };

    /*
     *
     *   ACTIVITY LIFECYCLES
     *
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        architectView.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        architectView.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //Detach Firebae listeners
        //mPresenter.detachFirebaseListeners();

        try
        {
            //Deletes any key saved, next time activity is created, will be a totally new key
            mPresenter.deleteFirstKeySaved();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        architectView.onDestroy();
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

    /*
    *
    *
    *
    *   OTHER METHODS
    *
    *
    * */

    public void navigateBack()
    {
        try
        {
            Intent map = new Intent(AR.this, Main.class);
            NavFlagsUtil.addFlags(map);
            startActivity(map);
            finish();
        }
        catch (Exception ex) {  Log.e(TAG, "Error: " + ex.getMessage()); }
    }


}
