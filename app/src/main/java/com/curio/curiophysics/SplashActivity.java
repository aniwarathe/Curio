package com.curio.curiophysics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {
    Button btnImCurious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //TODO add ripple to the button
        btnImCurious=findViewById(R.id.btn_im_curious);
        btnImCurious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imCuriousIntent=new Intent(SplashActivity.this,ChaptersActivity.class);
                startActivity(imCuriousIntent);
            }
        });
    }
}
