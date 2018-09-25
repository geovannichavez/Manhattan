package us.globalpay.manhattan.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.presenters.PermissionsPresenter;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.views.PermissionsView;

public class Permissions extends AppCompatActivity implements PermissionsView
{
    private static final String TAG = Permissions.class.getSimpleName();

    private ImageView ivBackground;
    private ImageView btnContinue;

    PermissionsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnContinue = (ImageView) findViewById(R.id.btnContinue);

        mPresenter = new PermissionsPresenter(this, this, this);
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
                mPresenter.checkPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        mPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void navegateNextActivity()
    {
        try
        {
            Intent authenticate = new Intent(this, Authenticate.class);
            NavFlagsUtil.addFlags(authenticate);
            startActivity(authenticate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void generateToast(String text)
    {
        Toast.makeText(Permissions.this, text, Toast.LENGTH_LONG).show();
    }
}
