package com.curio.curiophysics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.curio.curiophysics.Adapters.ChaptersAdapter;
import com.curio.curiophysics.Model.Chapter;
import com.curio.curiophysics.Model.Note;
import com.curio.curiophysics.Model.SubChapter;
import com.curio.curiophysics.ViewHolder.ChaptersViewHolder;
import com.curio.curiophysics.ViewHolder.SubChaptersViewHolder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewChapterActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference chapters;
    DatabaseReference subChapters;
    ArrayList<Chapter> chaptersArray = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerViewExpandableItemManager expMgr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chapter);

        //recycler view

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        //firebase
        database = FirebaseDatabase.getInstance();
        chapters = database.getReference("chapters");
        subChapters = database.getReference("subChapters");

        chapters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> chaptersSet = dataSnapshot.getChildren();
                for (DataSnapshot n : chaptersSet) {
                    final Chapter chapter = n.getValue(Chapter.class);
                    final String chapterKey = n.getKey();
                    chapter.setId(Integer.parseInt(chapterKey));
                    //subchapters
                    subChapters.orderByChild("chapterId").equalTo(chapterKey).addValueEventListener(new ValueEventListener() {
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

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void storageContainer(ArrayList<Chapter> chapters) {
        expMgr = new RecyclerViewExpandableItemManager(null);
        adapter = expMgr.createWrappedAdapter(new ChaptersAdapter(chapters));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        expMgr.attachRecyclerView(recyclerView);
    }
}

    //Adapter for chapters and subchapters

