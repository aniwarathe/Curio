package com.curio.curiophysics;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.curio.curiophysics.Adapters.ChaptersAdapter;
import com.curio.curiophysics.Adapters.SwipeNoteAdapter;
import com.curio.curiophysics.Common.CurrentChapter;
import com.curio.curiophysics.Model.Chapter;
import com.curio.curiophysics.Model.Note;
import com.curio.curiophysics.Model.SubChapter;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotesLoader extends AppCompatActivity {
    ProgressBar notesProgressBar;
    SwipeNoteAdapter adapter;
    SwipeDeck noteSwipeDeck;
    android.support.v7.widget.Toolbar toolbar;
    ImageButton goBack;
    ImageButton close;
    String noteId;
    ArrayList<Note> notesArrayForCards = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference notes;
    private static final String placeEnum="HAM_"+String.valueOf(CurrentChapter.CurrentChapter.getsubChapters().size());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_loader);
        noteSwipeDeck = (SwipeDeck) findViewById(R.id.note_swipe_deck);
        noteSwipeDeck.bringToFront();

        if (getIntent() != null)
            noteId = getIntent().getStringExtra("noteId");


        //firebase
        database = FirebaseDatabase.getInstance();
        notes = database.getReference("notes");

        //progress bar
        notesProgressBar = findViewById(R.id.progres_notes);
        notesProgressBar.setVisibility(View.VISIBLE);
        notesProgressBar.setScaleY(2f);

        //Action bar
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(CurrentChapter.CurrentChapter.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NotesLoader.this, ChaptersActivity.class);
                startActivity(intent);
            }
        });

        BoomMenuButton leftBmb =findViewById(R.id.action_bar_left_bmb);
        CurrentChapter.CurrentChapter.getsubChapters().size();

        //get data to load boom menu
        ArrayList<String> subChapterNames=new ArrayList<>();
        List<SubChapter> subChapters=CurrentChapter.CurrentChapter.getsubChapters();
        for(int i=0;i<subChapters.size();i++){
            subChapterNames.add(i,subChapters.get(i).getName());
        }


        leftBmb.setButtonEnum(ButtonEnum.Ham);
        leftBmb.setButtonPlaceEnum(ButtonPlaceEnum.valueOf(placeEnum));
        leftBmb.setPiecePlaceEnum(PiecePlaceEnum.valueOf(placeEnum));
        leftBmb.setNormalColor(R.color.primary);
        leftBmb.setDimColor(Color.parseColor("#CDDC39"));
        leftBmb.setHamHeight(1);
        for (int i = 0; i < leftBmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalText(subChapterNames.get(i))
                    .rippleEffect(true)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            getFirebaseData(String.valueOf(noteId.charAt(0))+(index+1));
                        }
                    });
            leftBmb.addBuilder(builder);
        }

        /*//goBack Button
        goBack =findViewById(R.id.btn_goBack);*/

        //firebase
        getFirebaseData(noteId);

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

    //method for load notes
    public void getFirebaseData(final String noteRef){
        Toast.makeText(this, noteRef, Toast.LENGTH_SHORT).show();
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
        Log.i("curioApp","storage "+notesArrayForCards.get(0).getNote());
        adapter = new SwipeNoteAdapter(notesArrayForCards,getBaseContext());
        if (noteSwipeDeck != null) {
            Log.i("curioApp","now here");
                noteSwipeDeck.setAdapter(adapter);
            }
        //progress Bar
        notesProgressBar.setMax(adapter.getCount());

       /* // Reset button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteSwipeDeck.unSwipeCard();
                notesProgressBar.setProgress(notesProgressBar.getProgress() - 1);
                buttonAnimator(goBack);
            }
        });*/

    }
    private void buttonAnimator(ImageButton imageButton){
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        imageButton.startAnimation(shake); // star
    }



}



