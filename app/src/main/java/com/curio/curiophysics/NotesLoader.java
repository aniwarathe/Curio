package com.curio.curiophysics;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.curio.curiophysics.Adapters.SwipeNoteAdapter;
import com.curio.curiophysics.Model.Note;
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

public class NotesLoader extends AppCompatActivity {
    ProgressBar notesProgressBar;
    private SwipeNoteAdapter adapter;
    SwipeDeck noteSwipeDeck;
    ImageButton goBack;
    ImageButton close;
    String subChapterId;
    ArrayList<Note> notesArrayForCards = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_loader);
        noteSwipeDeck = (SwipeDeck) findViewById(R.id.note_swipe_deck);

        if (getIntent() != null)
            subChapterId = getIntent().getStringExtra("subChapterId");

        //progress bar
        notesProgressBar = findViewById(R.id.progres_notes);
        notesProgressBar.setVisibility(View.VISIBLE);
        notesProgressBar.setScaleY(2f);

        //Action bar with boom menu
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.action_bar_with_boom_menu, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
       // ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0,0);

        BoomMenuButton rightBmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_right_bmb);

        rightBmb.setButtonEnum(ButtonEnum.Ham);
        rightBmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        rightBmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        for (int i = 0; i < rightBmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_menu_camera)
                    .normalTextRes(R.string.chapter_not_available)
                    .rippleEffect(true)
                    .normalColor(Color.BLUE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(NotesLoader.this, ("tapped"+index), Toast.LENGTH_SHORT).show();
                        }
                    });
            rightBmb.addBuilder(builder);
        }

        //goBack Button
        goBack =findViewById(R.id.btn_goBack);
        close=findViewById(R.id.btn_close);

        //firebase
        database = FirebaseDatabase.getInstance();
        notes = database.getReference("notes");
        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot notesetSnapShot = dataSnapshot.child("05");
                Iterable<DataSnapshot> noteSet = notesetSnapShot.getChildren();
                ArrayList<Note> fireBaseArray = new ArrayList<>();
                for (DataSnapshot n : noteSet) {
                    Note note = n.getValue(Note.class);
                    fireBaseArray.add(note);
                    storageContainer(fireBaseArray);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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
    public void storageContainer(final ArrayList<Note> notesArrayForCards) {
        this.notesArrayForCards = notesArrayForCards;
        adapter = new SwipeNoteAdapter(notesArrayForCards,getBaseContext());
        if (noteSwipeDeck != null) {
                noteSwipeDeck.setAdapter(adapter);
            }


        //progress Bar
        notesProgressBar.setMax(adapter.getCount());

        // Reset button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteSwipeDeck.unSwipeCard();
                notesProgressBar.setProgress(notesProgressBar.getProgress() - 1);
                buttonAnimator(goBack);
            }
        });

        //close button-returns to subchapters

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAnimator(close);
                finish();
            }
        });

    }
    private void buttonAnimator(ImageButton imageButton){
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        imageButton.startAnimation(shake); // star
    }
}


