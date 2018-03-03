package com.curiolearning.curio;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
public class NavigationDrawerContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //action bar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WebView urlWebView = new WebView(this);
        urlWebView.setWebViewClient(new WebViewClient());

        urlWebView.getSettings().setJavaScriptEnabled(true);

        String drawerFragmentName=getIntent().getStringExtra("navigationFragmentName");
        if(drawerFragmentName.equals("Contribute")){
            urlWebView.loadUrl("https://docs.google.com/forms/d/1U92sdYDjz7y-f4KYKLa5zQh66yesNJmEkHqrbp7avkc/edit");
            this.setContentView(urlWebView);

        }else if(drawerFragmentName.equals("FeedBack")){
            urlWebView.loadUrl("https://curiolearning.typeform.com/to/VvlAZT");
            this.setContentView(urlWebView);
        }else if(drawerFragmentName.equals("Rate")){
            Toast.makeText(this, drawerFragmentName, Toast.LENGTH_SHORT).show();
        }
    }
}
