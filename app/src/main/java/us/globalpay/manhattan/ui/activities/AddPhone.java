package us.globalpay.manhattan.ui.activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.interactors.PhoneValidationInteractor;
import us.globalpay.manhattan.models.api.Country;
import us.globalpay.manhattan.presenters.PhoneValidationPresenter;
import us.globalpay.manhattan.utils.MetricsUtils;
import us.globalpay.manhattan.views.AddPhoneView;

public class AddPhone extends AppCompatActivity implements AddPhoneView
{
    private static final String TAG = AddPhone.class.getSimpleName();

    ImageView ivSpinnerBackgrnd;
    ImageView ivBackground;
    TextView tvCountry;
    EditText etPhoneInput;

    PhoneValidationPresenter mPresenter;

    List<String> mCountriesNames = new ArrayList<>();
    HashMap<String, Country> mCountries = new HashMap<>();
    Country mSelectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        ivSpinnerBackgrnd = (ImageView) findViewById(R.id.ivSpinnerBackgrnd);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        etPhoneInput = (EditText) findViewById(R.id.etPhoneInput);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        mPresenter = new PhoneValidationPresenter(this, this, this);
        mPresenter.initailize();
        mPresenter.retrieveCountries();
    }

    @Override
    public void initializeViews()
    {
        Glide.with(this).load(R.drawable.bg_full_purple_blue).into(ivBackground);

        tvCountry.setTypeface(null, Typeface.NORMAL);
        tvCountry.setTextColor(getResources().getColor(R.color.color_blue_light));
        tvCountry.setText(getString(R.string.label_select_country));

        etPhoneInput.setEnabled(false);
        ivSpinnerBackgrnd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayCountries();
            }
        });
    }

    @Override
    public void renderCountries(List<Country> countries)
    {
        for (Country country : countries)
        {
            mCountriesNames.add(country.getName());
            mCountries.put(country.getName(), country);
        }
    }

    @Override
    public void showDialog()
    {

    }

    private void displayCountries()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_list_item_single_choice, mCountriesNames);
        builder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String countryName = mCountriesNames.get(i);
                mSelectedCountry = mCountries.get(countryName);

                //Changes UI
                tvCountry.setText(mSelectedCountry.getName());
                tvCountry.setTypeface(null, Typeface.BOLD);
                tvCountry.setTextColor(getResources().getColor(R.color.color_purple));
                etPhoneInput.setEnabled(true);

                mPresenter.saveSelectedCountry(mSelectedCountry);
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
