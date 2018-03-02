package com.curio.curiophysics;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationDrawerContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_navigation_drawer_container);

        WebView urlWebView = new WebView(this);
        urlWebView.setWebViewClient(new WebViewClient());

        urlWebView.getSettings().setJavaScriptEnabled(true);

        String drawerFragmentName=getIntent().getStringExtra("navigationFragmentName");
        if(drawerFragmentName.equals("About")) {

            Toast.makeText(this, drawerFragmentName, Toast.LENGTH_SHORT).show();
        }else if(drawerFragmentName.equals("Contribute")){
            urlWebView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSe22j3mo6LMT11FPLtQuo0JTvdVyazKm8fFeHiaym901Us4Lg/viewform?usp=sf_link");
            this.setContentView(urlWebView);
        }else if(drawerFragmentName.equals("FeedBack")){
            urlWebView.loadUrl("https://curiolearning.typeform.com/to/VvlAZT");
            this.setContentView(urlWebView);
            Toast.makeText(this, drawerFragmentName, Toast.LENGTH_SHORT).show();
        }else if(drawerFragmentName.equals("Facebook")){
            urlWebView.loadUrl("https://www.facebook.com/curiolearn/");
            this.setContentView(urlWebView);
        }else if(drawerFragmentName.equals("Rate")){
            Toast.makeText(this, drawerFragmentName, Toast.LENGTH_SHORT).show();
        }
    }
}
