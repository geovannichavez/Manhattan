package us.globalpay.manhattan.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.models.api.Country;
import us.globalpay.manhattan.models.api.RegisterClientResponse;
import us.globalpay.manhattan.presenters.PhoneValidationPresenter;
import us.globalpay.manhattan.utils.ButtonAnimator;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.views.AddPhoneView;

public class AddPhone extends AppCompatActivity implements AddPhoneView
{
    private static final String TAG = AddPhone.class.getSimpleName();

    ImageView ivSpinnerBackgrnd;
    ImageView ivBackground;
    ImageView btnContinue;
    TextView tvCountry;
    EditText etPhoneInput;
    ProgressDialog mProgressDialog;

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
        btnContinue = (ImageView) findViewById(R.id.btnContinue);

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
                ButtonAnimator.shadowButton(v);
                displayCountries();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    ButtonAnimator.shadowButton(v);
                    String phoneNumber = etPhoneInput.getText().toString();
                    phoneNumber = phoneNumber.replace("-", "");

                    mPresenter.requestToken(phoneNumber, Constants.INTENT_BUNDLE_AUTH_TYPE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        phoneValidations();
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
    public void retypePhoneView()
    {
        etPhoneInput.setEnabled(true);
        btnContinue.setEnabled(false);
    }

    @Override
    public void setTypedPhone(String phone)
    {
        try
        {
            etPhoneInput.setText(phone);
        }
        catch (Exception ex) { ex.printStackTrace();    }
    }

    @Override
    public void showLoading(String content)
    {
        mProgressDialog = new ProgressDialog(AddPhone.this);
        mProgressDialog.setMessage(content);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoading()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showGenericMessage(DialogModel errorMessage)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        alertDialog.setTitle(errorMessage.getTitle());
        alertDialog.setMessage(errorMessage.getContent());
        alertDialog.setPositiveButton(errorMessage.getAcceptButton(), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void navigateTokenInput(RegisterClientResponse pResponse, String stringExtra)
    {
        String phone = etPhoneInput.getText().toString();

        String rawPhone = phone.replace("-", "");
        mPresenter.saveUserGeneralData(rawPhone, pResponse.getConsumerID());

        Intent inputToken = new Intent(AddPhone.this, SmsCodeInput.class);
        inputToken.putExtra(Constants.INTENT_BUNDLE_AUTH_TYPE, stringExtra);
        NavFlagsUtil.addFlags(inputToken);
        startActivity(inputToken);
    }

    private void phoneValidations()
    {
        etPhoneInput.addTextChangedListener(new TextWatcher()
        {

            int TextLength = 0;
            private static final char dash = '-';

            @Override
            public void afterTextChanged(Editable text)
            {

                String NumberText = etPhoneInput.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 9 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    btnContinue.setEnabled(true);
                }
                else
                {
                    btnContinue.setEnabled(false);
                }

                // Remove spacing char
                if (text.length() > 0 && (text.length() % 5) == 0)
                {
                    final char c = text.charAt(text.length() - 1);
                    if (dash == c)
                    {
                        text.delete(text.length() - 1, text.length());
                    }
                }
                // Insert char where needed.
                if (text.length() > 0 && (text.length() % 5) == 0)
                {
                    char c = text.charAt(text.length() - 1);
                    // Only if its a digit where there should be a dash we insert a dash
                    if (Character.isDigit(c) && TextUtils.split(text.toString(), String.valueOf(dash)).length <= 3)
                    {
                        text.insert(text.length() - 1, String.valueOf(dash));
                    }
                }

                // Text writen in bold
                if(text.length() > 0 )
                    etPhoneInput.setTypeface(null, Typeface.BOLD);
                else
                    etPhoneInput.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                String str = etPhoneInput.getText().toString();
                TextLength = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
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
