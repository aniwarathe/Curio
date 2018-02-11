package com.curio.curiophysics;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.curio.curiophysics.Adapters.ChaptersAdapter;
import com.curio.curiophysics.Common.CurrentChaptersArray;
import com.curio.curiophysics.Common.CurrentSubChapterData;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;

public class ChaptersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerViewExpandableItemManager expMgr;
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

        //recycler view

        recyclerView = findViewById(R.id.chapters_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemView(v);
            }
        };
        storageContainer();


    }

    //method to get the chapters and subchapters from firebase and call the adapter

    public void storageContainer(){
                //CurrentChaptersArray.currentChaptersArray=chapters;
        expMgr=new RecyclerViewExpandableItemManager(null);
        adapter=expMgr.createWrappedAdapter(new ChaptersAdapter(CurrentChaptersArray.currentChaptersArray));
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
        String subChapterId=String.valueOf(groupPosition+1)+String.valueOf(childPosition+1);

        CurrentSubChapterData.CurrentChapter=CurrentChaptersArray.currentChaptersArray.get(groupPosition);
        CurrentSubChapterData.CurrentSubChapter=CurrentSubChapterData.CurrentChapter.getSubChapters().get(childPosition);

        chaptersIntent=new Intent(ChaptersActivity.this,NotesLoader.class);
        chaptersIntent.putExtra("subChapterId",subChapterId);
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

        /*if (id == R.id.na) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}