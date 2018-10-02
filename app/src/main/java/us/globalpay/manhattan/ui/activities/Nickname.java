package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.presenters.NicknamePresenter;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.utils.Validation;
import us.globalpay.manhattan.views.NicknameView;

public class Nickname extends AppCompatActivity implements NicknameView
{
    private static final String TAG = Nickname.class.getSimpleName();

    EditText etNickInput;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog progressDialog;
    ImageView ivSpinnerBackgrnd;
    ImageView btnContinue;
    ImageView ivBackground;

    NicknamePresenter mPresenter;
    Validation mValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        etNickInput = (EditText) findViewById(R.id.etNickInput);
        ivSpinnerBackgrnd = (ImageView) findViewById(R.id.ivSpinnerBackgrnd);
        btnContinue = (ImageView) findViewById(R.id.btnContinue);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        mPresenter = new NicknamePresenter(this, this, this);
        mPresenter.initialize();
    }

    @Override
    public void initializeViews()
    {
        Glide.with(this).load(R.drawable.bg_full_purple_blue).into(ivBackground);

        btnContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validateNickname();
            }
        });

        mValidator = new Validation(this);
    }

    @Override
    public void showLoading(String label)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(label);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoading()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void showGenericMessage(DialogModel dialog)
    {
        //TODO: Consumir mecanismo previamente creado
    }

    @Override
    public void navigateNext(Intent nextActivity)
    {
        Intent main = new Intent(this, Main.class);
        NavFlagsUtil.addFlags(main);
        startActivity(main);
        finish();
    }

    @Override
    public void showGenericToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createSnackbar(String content)
    {
        Snackbar mSnackbar = Snackbar.make(coordinatorLayout, content, Snackbar.LENGTH_LONG);
        View snackbarView = mSnackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this,  R.color.color_red_error));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        mSnackbar.show();
    }

    public void validateNickname()
    {
        if(checkValidation())
        {
            mPresenter.sendNickname(etNickInput.getText().toString().trim());
        }
    }

    private boolean checkValidation()
    {
        boolean result;

        switch (mValidator.checkNickname(etNickInput, true))
        {
            case REQUIRED:
                result = false;
                createDialog(getString(R.string.validation_title_required), getString(R.string.validation_required_nickname), getString(R.string.button_accept));
                break;
            case VALID:
                result = true;
                break;
            case NOT_VALID:
                result = false;
                createDialog(getString(R.string.title_dialog_invalid_nickname), getString(R.string.label_dialog_invalid_nickname), getString(R.string.button_accept));
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    private void createDialog(String title, String message, String button)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
