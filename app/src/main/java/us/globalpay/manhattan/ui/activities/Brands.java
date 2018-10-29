package us.globalpay.manhattan.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.presenters.BrandsPresenter;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.interfaces.IActionResult;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.ui.DialogGenerator;
import us.globalpay.manhattan.views.BrandsView;
import us.globalpay.manhattan.ui.adapters.BrandsAdapter;

public class Brands extends AppCompatActivity implements BrandsView
{
    private static final String TAG = Brands.class.getSimpleName();

    //Views
    ImageView ivBackground;
    ImageView btnBack;
    ImageView spLocation;
    TextView tvLocation;
    ExpandableListView mExpandable;

    //Global variables
    String mUrlCategory;
    BrandsAdapter mBrandsAdapter;

    //MVP
    BrandsPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        mPresenter = new BrandsPresenter(this, this, this);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        mExpandable = (ExpandableListView) findViewById(R.id.lvCategories);
        spLocation = (ImageView) findViewById(R.id.spLocation);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        mPresenter.init();
        mPresenter.retrieveBrands();
    }

    @Override
    public void initialize()
    {
        View toolbar = findViewById(R.id.toolbarBrand);
        ImageView ivTitleIcon = toolbar.findViewById(R.id.ivTitleIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.lblTitle);
        ivTitleIcon.setImageResource(R.drawable.ic_brands_icon);
        title.setText(getString(R.string.title_activity_brands));

        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);

        btnBack = toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(backListener);

        spLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.floatingButton(Brands.this, v);
                mPresenter.openStoresList();
            }
        });

    }

    @Override
    public void renderBrands(List<Category> categories, Map<String, List<Brand>> filledCategories)
    {

        mBrandsAdapter = new BrandsAdapter(this, categories, filledCategories);
        mExpandable.setAdapter(mBrandsAdapter);

        mExpandable.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                Category category = (Category) mBrandsAdapter.getGroup(groupPosition);
                mUrlCategory = category.getUrlImg();
                return false;
            }
        });
        mExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Brand selected = (Brand) mBrandsAdapter.getChild(groupPosition, childPosition);
                mPresenter.saveSelectedBrand(selected);
                Intent coupons = new Intent(Brands.this, BrandsCoupons.class);
                coupons.putExtra(Constants.INTENT_BUNDLE_BRAND_ID, selected.get$id());
                coupons.putExtra(Constants.INTENT_BUNDLE_CATEGORY_ICON, mUrlCategory);
                NavFlagsUtil.addFlags(coupons);
                startActivity(coupons);
                finish();

                return true;
            }
        });

        mBrandsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showLoadingDialog(String string, boolean isCancelable)
    {
        DialogGenerator.showProgressDialog(this, string, isCancelable);
    }

    @Override
    public void hideLoadingDialog()
    {

    }

    @Override
    public void showGenericDialog(DialogModel dialog)
    {

    }

    @Override
    public void showListDialog(HashMap<String, ?> arrayMap, IActionResult actionResult)
    {
        DialogGenerator.showArrayDialog(this, arrayMap, actionResult);
    }

    @Override
    public void setStoreName(String name)
    {
        try
        {
            tvLocation.setText(name);
        }
        catch (Exception ex) {    Log.e(TAG, "Error: " + ex.getMessage());    }
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
        public void onClick(View view)
        {
            ButtonAnimator.backButton(Brands.this, view);
            navigateBack();
        }
    };

    private void navigateBack()
    {
        try
        {
            Intent main = new Intent(Brands.this, Main.class);
            NavFlagsUtil.addFlags(main);
            startActivity(main);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
