package com.curiolearning.curio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.curio_about_description))
                .setImage(R.drawable.curio_logo_splash)
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("curiolearn@gmail.com")
                .addFacebook("Curiolearn")
                .addPlayStore("com.curiolearning.curio")
                .create();

        setContentView(aboutPage);
    }
    }

//"fb://page/212929252609400"