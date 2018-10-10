package us.globalpay.manhattan.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.presenters.BrandsPresenter;
import us.globalpay.manhattan.utils.ui.ButtonAnimator;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.views.BrandsView;
import us.globalpay.manhattan.ui.adapters.BrandsAdapter;

public class Brands extends AppCompatActivity implements BrandsView
{
    private static final String TAG = Brands.class.getSimpleName();

    ImageView ivBackground;
    ImageView btnBack;
    ExpandableListView mExpandable;

    BrandsAdapter mBrandsAdapter;

    BrandsPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        mPresenter = new BrandsPresenter(this, this, this);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        mExpandable = (ExpandableListView) findViewById(R.id.lvCategories);

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

    }

    @Override
    public void renderBrands(List<Category> categories, Map<String, List<Brand>> filledCategories)
    {

        mBrandsAdapter = new BrandsAdapter(this, categories, filledCategories);
        mExpandable.setAdapter(mBrandsAdapter);

        mExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                final String selected = (String) mBrandsAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });

        mBrandsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showLoadingDialog(String string)
    {

    }

    @Override
    public void hideLoadingDialog()
    {

    }

    @Override
    public void showGenericDialog(DialogModel dialog)
    {

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
