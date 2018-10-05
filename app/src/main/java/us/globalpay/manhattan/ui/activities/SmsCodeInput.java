package us.globalpay.manhattan.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.presenters.SmsCodeValidationPresenter;
import us.globalpay.manhattan.utils.ButtonAnimator;
import us.globalpay.manhattan.utils.Constants;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.UserData;
import us.globalpay.manhattan.views.SmsCodeInputView;

public class SmsCodeInput extends AppCompatActivity implements SmsCodeInputView
{
    private static final String TAG = SmsCodeInput.class.getSimpleName();

    private ImageView ivBackground;
    private ImageView btnContinue;
    private TextView lblTitle;
    private TextView lblPhoneVlidated;
    private TextView lblContactUs;
    private TextView lblButtonContent;
    private EditText etCodeInput;
    private ProgressDialog mProgressDialog;

    private SmsCodeValidationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code_input);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnContinue = (ImageView) findViewById(R.id.btnContinue);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblPhoneVlidated = (TextView) findViewById(R.id.lblPhoneVlidated);
        etCodeInput = (EditText) findViewById(R.id.etCodeInput);
        lblContactUs = (TextView) findViewById(R.id.lblContactUs);
        lblButtonContent = (TextView) findViewById(R.id.lblButtonContent);

        mPresenter = new SmsCodeValidationPresenter(this, this, this);
        mPresenter.initialize();
    }

    @Override
    public void initialViewsState()
    {
        Glide.with(this).load(R.drawable.bg_full_purple_blue).into(ivBackground);
        btnContinue.setEnabled(false);
        btnContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.animateButton(v);
                String code = etCodeInput.getText().toString();
                mPresenter.validateSmsCode(code);
            }
        });
        entriesValidations();
    }

    @Override
    public void showLoading(String content)
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(content);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismissLoading()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void showErrorMessage(DialogModel dialogModel)
    {
        //TODO: Integrar dialogo predefinido
    }

    @Override
    public void navigateHome(boolean p3DCompatible)
    {
        Intent main = new Intent(this, Main.class);
        NavFlagsUtil.addFlags(main);
        startActivity(main);
        finish();
    }

    @Override
    public void vibrateOnSuccess()
    {
        try
        {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            if(vibrator != null)
                vibrator.vibrate(500); //500 milisegundos
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void cleanFields()
    {
        etCodeInput.setText("");
    }

    @Override
    public void setCallcenterContactText()
    {
        try
        {
            String phone = getString(R.string.label_callcenter_phone_number);

            SpannableString part1 = new SpannableString(getString(R.string.label_portable_phone_number_part1));
            SpannableString part2 = new SpannableString(phone);
            part2.setSpan(new StyleSpan(Typeface.BOLD), 0, phone.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString part3 = new SpannableString(getString(R.string.label_portable_phone_number_part2));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setCodeSentLabelText(String phoneNumber)
    {
        try
        {
            String part1 = getString(R.string.label_type_token_part1) + " ";
            String part3 = getString(R.string.label_type_token_part2);

            StringBuilder builder = new StringBuilder();
            builder.append(part1);
            builder.append(phoneNumber);
            builder.append(part3);

            int end = part1.length() + phoneNumber.length();

            SpannableString spannableString = new SpannableString(builder);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD),part1.length(),end, 0);


            lblPhoneVlidated.setText(spannableString, TextView.BufferType.SPANNABLE);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void navigatePhoneValidation(boolean retypePhone)
    {
        try
        {
            Intent validatePhone = new Intent(this, AddPhone.class);
            validatePhone.putExtra(Constants.BUNDLE_PHONE_RETYPE, retypePhone);
            validatePhone.putExtra(Constants.INTENT_BUNDLE_AUTH_TYPE, UserData.getInstance(this).getAuthModeSelected());
            startActivity(validatePhone);
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void navigateNickname()
    {
        Intent nickname = new Intent(this, Nickname.class);
        NavFlagsUtil.addFlags(nickname);
        startActivity(nickname);
        finish();
    }

    private void entriesValidations()
    {
        etCodeInput.addTextChangedListener(new TextWatcher()
        {
            int TextLength = 0;

            @Override
            public void afterTextChanged(Editable text)
            {
                String NumberText = etCodeInput.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 5 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    btnContinue.setEnabled(true);
                }
                else
                {
                    btnContinue.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                String str = etCodeInput.getText().toString();
                TextLength = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
    }
}
