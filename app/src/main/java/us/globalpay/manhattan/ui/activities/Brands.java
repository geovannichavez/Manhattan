package us.globalpay.manhattan.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.Brand;
import us.globalpay.manhattan.models.BrandCategory;
import us.globalpay.manhattan.views.BrandsView;
import us.globalpay.manhattan.ui.adapters.ExpandableBrandsAdapter;

public class Brands extends AppCompatActivity implements BrandsView
{

    List<BrandCategory> mGroupList;
    List<Brand> mChildList;
    Map<String, List<Brand>> mCategoriesCollection;
    ExpandableListView mExpandable;


    ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        createGroupList();
        createCollection();

        mExpandable = (ExpandableListView) findViewById(R.id.lvCategories);

        final ExpandableBrandsAdapter expandableBrandsAdapter = new
                ExpandableBrandsAdapter(this, mGroupList, mCategoriesCollection);
        mExpandable.setAdapter(expandableBrandsAdapter);


        mExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                final String selected = (String) expandableBrandsAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });

        initialize();
    }

    private void createGroupList()
    {
        mGroupList = new ArrayList<>();
        BrandCategory cat1 = new BrandCategory();
        cat1.setId(1);
        cat1.setName("Restaurantes");
        mGroupList.add(cat1);

        BrandCategory cat2 = new BrandCategory();
        cat2.setId(2);
        cat2.setName("Electrodomesticos");
        mGroupList.add(cat2);

        BrandCategory cat3 = new BrandCategory();
        cat3.setId(3);
        cat3.setName("Ropa");
        mGroupList.add(cat3);

        /*BrandCategory cat4 = new BrandCategory();
        cat4.setName("Calzado");
        mGroupList.add(cat4);

        BrandCategory cat5 = new BrandCategory();
        cat5.setName("Herramientas");
        mGroupList.add(cat5);

        BrandCategory cat6 = new BrandCategory();
        cat6.setName("Belleza");
        mGroupList.add(cat6);

        BrandCategory cat7 = new BrandCategory();
        cat7.setName("Conveniencia");
        mGroupList.add(cat7);*/

    }

    private void createCollection()
    {
        //Restaurantes
        Brand b1 = new Brand();
        b1.setName("Wendy's");
        b1.setLogoUrl("http://www.stickpng.com/assets/images/58429d58a6515b1e0ad75ae8.png");
        b1.setDescription("Hamburguesas pequeñas y caras");

        Brand b2 = new Brand();
        b2.setName("Starbucks");
        b2.setLogoUrl("https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/012011/starbucks_corporation_logo_2011.png?itok=mloHLoP-");
        b2.setDescription("Café para wannabes y alienados");

        Brand b3 = new Brand();
        b3.setName("Tony Roma's");
        b3.setLogoUrl("https://www.curacao.com/media/directory/grill-and-steakhouse/b084c45e7880f78e6cf2e392dd306a5b43889eae.jpg");
        b3.setDescription("Comida aburrida");

        //Electrodomestios
        Brand b4 = new Brand();
        b4.setName("La Curacao");
        b4.setLogoUrl("http://4.bp.blogspot.com/_WVdv7UOc-E8/Svbr6WsGasI/AAAAAAAAANQ/9Y4wU1OKjos/s320/la+curacao.jpg");
        b4.setDescription("Cosas al credito");

        Brand b5 = new Brand();
        b5.setName("TV Offer");
        b5.setLogoUrl("https://yt3.ggpht.com/a-/ACSszfHsW6jP5X2nTbVJ445NX7eDTsrEO0k6l7ff_w=s900-mo-c-c0xffffffff-rj-k-no");
        b5.setDescription("Tonteras inhutiles");

        Brand b6 = new Brand();
        b6.setName("Radioshack");
        b6.setLogoUrl("https://images-yasabe.netdna-ssl.com/r/d8/640x480/5032653.jpg");
        b6.setDescription("Cosas chinas");

        //Ropa
        Brand b7 = new Brand();
        b7.setName("SIMAN");
        b7.setLogoUrl("https://pbs.twimg.com/profile_images/997258020496248832/5EJq69P6_400x400.jpg");
        b7.setDescription("Fancy walmart");

        Brand b8 = new Brand();
        b8.setName("Forever21");
        b8.setLogoUrl("http://glency.com/wp-content/uploads/2014/05/ac2610eeab7912b47aa0599f412c8090_400x400.png");
        b8.setDescription("Ropa para mujeres");

        List<Brand> rests = new ArrayList<>();
        rests.add(b1);
        rests.add(b2);
        rests.add(b3);
        List<Brand> elec = new ArrayList<>();
        elec.add(b4);
        elec.add(b5);
        elec.add(b6);
        List<Brand> clths = new ArrayList<>();
        clths.add(b7);
        clths.add(b8);

        mCategoriesCollection = new LinkedHashMap<>();


        for(BrandCategory item : mGroupList)
        {
            if(item.getId() == 1)
                loadChild(rests);
            else if(item.getId() == 2)
                loadChild(elec);
            else
                loadChild(clths);

            mCategoriesCollection.put(item.getName(), mChildList);
        }

    }

    private void loadChild(List<Brand> list)
    {
        mChildList = new ArrayList<Brand>();
        mChildList.addAll(list);
    }

    @Override
    public void initialize()
    {
        Glide.with(this).load(R.drawable.bg_blue_purple).into(ivBackground);
    }

    @Override
    public void renderBrands()
    {

    }
}
