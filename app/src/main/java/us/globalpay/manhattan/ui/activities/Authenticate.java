package us.globalpay.manhattan.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.login.widget.LoginButton;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.models.DialogModel;
import us.globalpay.manhattan.presenters.AuthenticatePresenter;
import us.globalpay.manhattan.utils.ButtonAnimator;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.views.AuthenticateView;

public class Authenticate extends AppCompatActivity implements AuthenticateView
{
    private static final String TAG = Authenticate.class.getSimpleName();

    ProgressDialog progressDialog;
    LoginButton btnFacebookLogin;
    ImageView btnFacebook;
    ImageView btnGoogle;
    ImageView ivBackground;

    AuthenticatePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        btnFacebookLogin = (LoginButton) findViewById(R.id.btnFacebookLogin);
        btnFacebook = (ImageView) findViewById(R.id.btnFacebook);
        btnGoogle = (ImageView) findViewById(R.id.btnGoogle);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        mPresenter = new AuthenticatePresenter(this, this, this);
        mPresenter.init();

        mPresenter.checkPlayServices();
        mPresenter.setupFacebookAuth(btnFacebookLogin);
        mPresenter.setupGoogleAuth();
    }

    @Override
    public void initialize()
    {
        Glide.with(this).load(R.drawable.bg_full_purple_blue).into(ivBackground);
        btnFacebook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.shadowButton(v);
                btnFacebookLogin.performClick();
                mPresenter.facebookSigin();
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.shadowButton(v);
            }
        });
    }

    @Override
    public void navigateValidatePhone()
    {
        Intent validatePhone = new Intent(this, AddPhone.class);
        startActivity(validatePhone);
    }

    @Override
    public void navigateSetNickname()
    {
        Intent setNickname = new Intent(this, Nickname.class);
        startActivity(setNickname);
    }

    @Override
    public void navigateHome()
    {
        Intent main = new Intent(this, Main.class);
        NavFlagsUtil.addFlags(main);
        startActivity(main);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoadingDialog(String label)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(label);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void enableLoginFacebookButton(boolean enabled)
    {
        btnFacebook.setEnabled(true);
    }

    @Override
    public void showGenericDialog(DialogModel dialogModel)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle(dialogModel.getTitle());
        builder.setMessage(dialogModel.getContent());
        builder.setPositiveButton(dialogModel.getAcceptButton(), new DialogInterface.OnClickListener()
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
