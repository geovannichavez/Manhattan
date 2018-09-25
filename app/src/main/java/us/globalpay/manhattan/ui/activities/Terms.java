package us.globalpay.manhattan.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import us.globalpay.manhattan.R;
import us.globalpay.manhattan.presenters.TermsPresenter;
import us.globalpay.manhattan.utils.NavFlagsUtil;
import us.globalpay.manhattan.views.TermsView;

public class Terms extends AppCompatActivity implements TermsView
{
    private static final String TAG = Terms.class.getSimpleName();

    private ImageView ivPrize;
    private ImageView ivBackground;
    private ImageView btnAccept;
    private ProgressBar progressBar;

    private TermsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        ivPrize = (ImageView) findViewById(R.id.ivPrize);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnAccept = (ImageView) findViewById(R.id.btnAccept);

        mPresenter = new TermsPresenter(this, this, this);
        mPresenter.initialize();
        mPresenter.checkDeviceComponents();
        mPresenter.setFirstTimeSettings();
    }

    @Override
    public void initializeViews()
    {
        Glide.with(this).load(R.drawable.bg_full_purple_blue).into(ivBackground);
        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.presentTerms();
            }
        });
    }

    @Override
    public void viewTerms(String url)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Terms.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_modal_terms_layout, null);

            ImageView ivButtonAccept = (ImageView) dialogView.findViewById(R.id.ivButtonAccept);
            WebView wv = (WebView) dialogView.findViewById(R.id.wvTerms);
            progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar);


            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setUseWideViewPort(true);
            wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            wv.loadUrl(url);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            wv.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url)
                {
                    progressBar.setVisibility(View.GONE);
                }
            });

            dialog.setView(dialogView);
            ivButtonAccept.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mPresenter.acceptTerms();
                    Intent accept;

                    if(Build.VERSION.SDK_INT >= 23)
                    {
                        accept = new Intent(Terms.this, Permissions.class);
                    }
                    else
                    {
                        mPresenter.grantDevicePermissions();
                        accept = new Intent(Terms.this, Authenticate.class);
                    }

                    NavFlagsUtil.addFlags(accept);
                    startActivity(accept);
                }
            });
            dialog.show();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
