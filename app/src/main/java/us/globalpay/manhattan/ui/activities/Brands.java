package us.globalpay.manhattan.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;
import us.globalpay.manhattan.presenters.BrandsPresenter;
import us.globalpay.manhattan.views.BrandsView;
import us.globalpay.manhattan.ui.adapters.BrandsAdapter;

public class Brands extends AppCompatActivity implements BrandsView
{
    private static final String TAG = Brands.class.getSimpleName();

    ImageView ivBackground;
    ExpandableListView mExpandable;

    BrandsPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        mExpandable = (ExpandableListView) findViewById(R.id.lvCategories);

        mPresenter = new BrandsPresenter(this, this, this);
        mPresenter.retrieveBrands();

        initialize();
    }

    @Override
    public void initialize()
    {
        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);
    }

    @Override
    public void renderBrands(List<Category> categories, Map<String, List<Brand>> filledCategories)
    {
        final BrandsAdapter brandsAdapter = new BrandsAdapter(this, categories, filledCategories);
        mExpandable.setAdapter(brandsAdapter);


        mExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                final String selected = (String) brandsAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
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
}
