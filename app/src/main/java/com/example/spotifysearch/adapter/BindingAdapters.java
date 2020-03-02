package com.example.spotifysearch.adapter;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.subjects.PublishSubject;

import static com.example.spotifysearch.viewmodel.main.search.SearchRepository.setRxTextWatcher;


public class BindingAdapters {


    @BindingAdapter("android:rxTextChangeEvents")
    public static void setOnTextChanged(EditText editText, PublishSubject subject) {

        setRxTextWatcher(editText, subject);


    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }

    @BindingAdapter("android:loadWebUrl")
    public static void loadUrl(WebView view, String url) {
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }

    @BindingAdapter("android:webViewClient")
    public static void setWebviewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);

    }

}
