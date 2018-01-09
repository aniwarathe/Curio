package com.curio.curiophysics;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.curio.curiophysics.Adapters.ChaptersAdapter;
import com.curio.curiophysics.Common.CurrentChapter;
import com.curio.curiophysics.Common.CurrentChaptersArray;
import com.curio.curiophysics.Interface.ItemClickListner;
import com.curio.curiophysics.Model.Chapter;
import com.curio.curiophysics.Model.SubChapter;
import com.curio.curiophysics.ViewHolder.ChaptersViewHolder;
import com.curio.curiophysics.ViewHolder.SubChaptersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;

import java.util.ArrayList;

public class ChaptersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase database;
    DatabaseReference chapters;
    DatabaseReference subChapters;
    ArrayList<Chapter> chaptersArray = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerViewExpandableItemManager expMgr;
    ProgressBar progressBarChapters;
    AlertDialog.Builder alertDialogBuilder;
    public Intent chaptersIntent;
    public static View.OnClickListener mItemOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //progress bar
        progressBarChapters=findViewById(R.id.progress_chapters);
        progressBarChapters.setScaleY(2f);

        //alert diaog
        alertDialogBuilder = new AlertDialog.Builder(this);

        //recycler view

        recyclerView = findViewById(R.id.chapters_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemView(v);
            }
        };

        //firebase
        database = FirebaseDatabase.getInstance();
        chapters = database.getReference("chapters");
        subChapters = database.getReference("subChapters");

        chapters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> chaptersSet = dataSnapshot.getChildren();
                for (DataSnapshot n : chaptersSet) {
                    final Chapter chapter = n.getValue(Chapter.class);
                    final String chapterKey=n.getKey();
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
                            storageContainer(chaptersArray);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //TODO add dialog box to indcate the error ,not connecting to the database

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    //method to get the chapters and subchapters from firebase and call the adapter

    public void storageContainer(ArrayList<Chapter> chapters){
        progressBarChapters.setVisibility(View.GONE);
        CurrentChaptersArray.currentChaptersArray=chapters;
        expMgr=new RecyclerViewExpandableItemManager(null);
        adapter=expMgr.createWrappedAdapter(new ChaptersAdapter(chapters));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        expMgr.attachRecyclerView(recyclerView);
    }
    void onClickItemView(View v) {
        RecyclerView.ViewHolder vh = RecyclerViewAdapterUtils.getViewHolder(v);
        int flatPosition = vh.getAdapterPosition();

        long packedPosition = expMgr.getExpandablePosition(flatPosition);
        int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(packedPosition);
        int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(packedPosition);
        String noteId=String.valueOf(groupPosition+1)+String.valueOf(childPosition+1);
        CurrentChapter.CurrentChapter=CurrentChaptersArray.currentChaptersArray.get(groupPosition);
        chaptersIntent=new Intent(ChaptersActivity.this,NotesLoader.class);
        chaptersIntent.putExtra("noteId",noteId);
        startActivity(chaptersIntent);

        if (flatPosition == RecyclerView.NO_POSITION) {
            return;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chapters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}