package com.curio.curiophysics;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.curio.curiophysics.Adapters.SwipeNoteAdapter;
import com.curio.curiophysics.Common.CurrentSubChapterData;
import com.curio.curiophysics.Model.Note;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesLoader extends AppCompatActivity {
    ProgressBar notesProgressBar;
    SwipeNoteAdapter adapter;
    SwipeDeck noteSwipeDeck;
    android.support.v7.widget.Toolbar toolbar;
    ImageButton btnGoBack;
    String noteId;
    ArrayList<Note> notesArrayForCards = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_loader);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(CurrentSubChapterData.CurrentSubChapter.getName());
        noteSwipeDeck = (SwipeDeck) findViewById(R.id.note_swipe_deck);
        noteSwipeDeck.bringToFront();
    //    btnGoBack=findViewById(R.id.btn_go_to_previous_card);

      //  btnGoBack=findViewById(R.id.btn_go_to_previous_card);

        if (getIntent() != null)
            noteId = getIntent().getStringExtra("subChapterId");
        //firebase
        database = FirebaseDatabase.getInstance();
        notes = database.getReference("notes");

        //call for notes
        getFirebaseData(noteId);

        //progress bar
        notesProgressBar = findViewById(R.id.progres_notes);
        notesProgressBar.setVisibility(View.VISIBLE);

        //setting progress bar

        noteSwipeDeck.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                notesProgressBar.setProgress((int) stableId + 1);
            }

            @Override
            public void cardSwipedRight(long stableId) {
                notesProgressBar.setProgress((int) stableId + 1);
            }
        });
    }

    //method for loading notes
    public void getFirebaseData(final String noteRef){
        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot notesetSnapShot = dataSnapshot.child(noteRef);
                Iterable<DataSnapshot> noteSet = notesetSnapShot.getChildren();
                ArrayList<Note> fireBaseArray = new ArrayList<>();
                for (DataSnapshot n : noteSet) {
                    Note note = n.getValue(Note.class);
                    fireBaseArray.add(note);
                }
                storageContainer(fireBaseArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void storageContainer(final ArrayList<Note> notesArrayForCards) {
        notesProgressBar.setProgress(0);
        this.notesArrayForCards = notesArrayForCards;
        Log.i("cardtype",notesArrayForCards.get(0).getType());
        adapter = new SwipeNoteAdapter(notesArrayForCards,getBaseContext());
        if (noteSwipeDeck != null) {
                noteSwipeDeck.setAdapter(adapter);
            }
        //progress Bar
        notesProgressBar.setMax(adapter.getCount());

       // goto previous card
        /*btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notesProgressBar.getProgress()>0){
                    noteSwipeDeck.unSwipeCard();
                    notesProgressBar.setProgress(notesProgressBar.getProgress() - 1);}
                }
        });*/

    }
}