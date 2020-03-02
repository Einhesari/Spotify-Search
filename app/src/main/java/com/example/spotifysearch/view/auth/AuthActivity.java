package com.example.spotifysearch.view.auth;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifysearch.R;
import com.example.spotifysearch.databinding.ActivityAuthorizationBinding;
import com.example.spotifysearch.databinding.WebviewDialogBinding;
import com.example.spotifysearch.states.AuthResource;
import com.example.spotifysearch.utils.Const;
import com.example.spotifysearch.view.main.MainActivity;
import com.example.spotifysearch.viewmodel.ViewModelProviderFactory;
import com.example.spotifysearch.viewmodel.auth.AuthActivityViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {


    public AuthActivityViewModel authorizationActivityViewModel;
    private ActivityAuthorizationBinding activityAuthorizationBinding;
    private WebviewDialogBinding webviewDialogBinding;

    public String url = Const.AUTH_URL;

    private Dialog authDialog;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        authorizationActivityViewModel = new ViewModelProvider(this, providerFactory).get(AuthActivityViewModel.class);

        activityAuthorizationBinding = DataBindingUtil.setContentView(this, R.layout.activity_authorization);
        webviewDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.webview_dialog, null, false);

        activityAuthorizationBinding.setActivity(this);
        webviewDialogBinding.setHost(this);

        authorizationActivityViewModel.checkAutorizationStatus();
        authorizationActivityViewModel.getAuthorizationStatus().observe(this, this::handleStateChange);

        initilizeDialog(webviewDialogBinding.getRoot());
    }


    private void handleStateChange(AuthResource resource) {

        if (resource.message != null) {
            Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show();
        }

        switch (resource.status) {
            case ALREADY_AUTHORIZED:
                activityAuthorizationBinding.setIsloading(false);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case UNAUTHROIZED:
                activityAuthorizationBinding.setIsloading(false);
                break;

            case ERROR:
                activityAuthorizationBinding.setIsloading(false);
                break;

            case AUTHORIZED_SUCCESSFULLY:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case LOADING:
                activityAuthorizationBinding.setIsloading(true);
                break;
        }
    }

    public void handleOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_authorization:
                authDialog.show();
                webviewDialogBinding.setIsloading(true);
                break;

            case R.id.img_webview_close:
                authDialog.cancel();
                webviewDialogBinding.invalidateAll();
                break;
        }

    }

    private void initilizeDialog(View layout) {
        authDialog = new Dialog(this);
        authDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authDialog.setCancelable(true);
        authDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        authDialog.setContentView(layout);
    }

    public WebViewClient webViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String requestUrl = request.getUrl().toString();
                if (requestUrl.startsWith(Const.REDIRECT_URL)) {
                    authorizationActivityViewModel.getCridentials(Uri.parse(requestUrl));
                    authDialog.cancel();
                    return true;
                } else {
                    view.loadUrl(requestUrl);
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewDialogBinding.setIsloading(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                authDialog.cancel();
                Toast.makeText(AuthActivity.this, "Error occurred , Try Again Later ", Toast.LENGTH_LONG).show();

            }
        };
    }


}
