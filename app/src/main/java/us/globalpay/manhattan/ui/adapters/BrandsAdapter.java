package us.globalpay.manhattan.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.api.Brand;
import us.globalpay.manhattan.models.api.Category;

/**
 * Created by Josué Chávez on 18/09/2018.
 */
public class BrandsAdapter extends BaseExpandableListAdapter
{
    private static final String TAG = ExpandableListView.class.getSimpleName();

    private Context mContext;
    private Map<String, List<Brand>> mChildrenCollection;
    private List<Category> mParents;

    public BrandsAdapter(Activity context, List<Category> categories, Map<String, List<Brand>> brandsCollection)
    {
        this.mContext = context;
        this.mChildrenCollection = brandsCollection;
        this.mParents = categories;
    }


    @Override
    public int getGroupCount()
    {
        return mParents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        String key = mParents.get(groupPosition).getName();
        return mChildrenCollection.get(key).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return mParents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        String key = mParents.get(groupPosition).getName();
        return mChildrenCollection.get(key).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Category category = (Category) getGroup(groupPosition);
        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_category_brand_listview_parent_item, parent, false);
        }
        TextView item = (TextView) convertView.findViewById(R.id.tvCategoryName);
        item.setText(category.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final Brand brand = (Brand) getChild(groupPosition, childPosition);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.custom_category_brand_listview_child_item, parent, false);
        }

        ImageView logo = (ImageView) convertView.findViewById(R.id.ivLogo);
        TextView name = (TextView) convertView.findViewById(R.id.tvName);
        TextView description = (TextView) convertView.findViewById(R.id.tvDescription);

        Glide.with(mContext).load(brand.getUrlLogo()).into(logo);
        name.setText(brand.getName());
        //description.setText(brand.);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
