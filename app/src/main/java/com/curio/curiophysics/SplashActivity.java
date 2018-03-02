package com.curio.curiophysics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.curio.curiophysics.Common.CurrentChaptersArray;
import com.curio.curiophysics.Model.Chapter;
import com.curio.curiophysics.Model.SubChapter;
import com.curio.curiophysics.Util.DataBaseUtil;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class SplashActivity extends AppCompatActivity {
    DatabaseReference chapters;
    DatabaseReference subChapters;
    ArrayList<Chapter> chaptersArray = new ArrayList<>();
    ProgressBar progressBarChapters;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final CoordinatorLayout coordinatorLayout=findViewById(R.id.coordinator);

        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        //progress bar
        final AVLoadingIndicatorView loading= findViewById(R.id.loading);
        loading.show();

        //firebase
        DataBaseUtil.getDatabase();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        chapters = database.getReference("chapters");
        chapters.keepSynced(true);
        subChapters = database.getReference("subChapters");
        subChapters.keepSynced(true);

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Snackbar snackbarSuccess=Snackbar.make(coordinatorLayout,"Connected",Snackbar.LENGTH_LONG);
                    snackbarSuccess.show();
                } else {
                    if (!isConnectedToInternet(context)) {

                        Snackbar snackbar = Snackbar.make(coordinatorLayout,"No network connection,Please connect for new content" , Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();}
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        chapters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> chaptersSet = dataSnapshot.getChildren();
                for (DataSnapshot n : chaptersSet) {
                    final Chapter chapter = n.getValue(Chapter.class);
                    final String chapterKey = n.getKey();
                    chapter.setId(Integer.parseInt(chapterKey));

                    //subchapters
                    subChapters.orderByChild("chapterId").equalTo(chapterKey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> subChaptersSet = dataSnapshot.getChildren();
                            ArrayList<SubChapter> subChaptersArray = new ArrayList<>();
                            for (DataSnapshot n : subChaptersSet) {
                                SubChapter subChapter = n.getValue(SubChapter.class);
                                subChapter.setId(Integer.parseInt(n.getKey()));
                                subChaptersArray.add(subChapter);
                            }
                            chapter.setsubChapters(subChaptersArray);
                            chaptersArray.add(chapter);
                            CurrentChaptersArray.currentChaptersArray = chaptersArray;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent imCuriousIntent = new Intent(SplashActivity.this, ChaptersActivity.class);
                                    startActivity(imCuriousIntent);
                                    finish();
                                }
                            },2000);
                            loading.hide();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (!isConnectedToInternet(context)) {

            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Internet connection required" , Snackbar.LENGTH_INDEFINITE).
                            setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
            snackbar.show();

        }

    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
