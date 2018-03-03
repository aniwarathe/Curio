package com.curiolearning.curio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.curiolearning.curio.Adapters.SwipeNoteAdapter;
import com.curiolearning.curio.Model.Note;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {
    ProgressBar notesProgressBar;
    SwipeNoteAdapter adapter;
    SwipeDeck noteSwipeDeck;
    ArrayList<Note> notesArrayForCards = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference notes;
    private String subChapterNoteId;
    private String subChapterName;
    private int numberOfNotes;
    private AdView mAdView;
    AVLoadingIndicatorView loading;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SUB_CHAPTER_NOTES_ARRAY = "subChapterNotes";
    private static final String SUB_CHAPTER_NAME = "subChapterName";


    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String subChapterNoteId,String subChapterName) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(SUB_CHAPTER_NOTES_ARRAY,subChapterNoteId);
        args.putString(SUB_CHAPTER_NAME,subChapterName);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subChapterNoteId = getArguments().getString(SUB_CHAPTER_NOTES_ARRAY);
            subChapterName=getArguments().getString(SUB_CHAPTER_NAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_note, container, false);
        noteSwipeDeck = view.findViewById(R.id.note_swipe_deck);
        noteSwipeDeck.bringToFront();

        //progress bar
        notesProgressBar = view.findViewById(R.id.progres_notes);
        notesProgressBar.setVisibility(View.VISIBLE);

        //progress note loading
        loading= view.findViewById(R.id.loading);
        loading.show();

        //admob
        mAdView=view.findViewById(R.id.ad_view);
        AdRequest adRequest=new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadNotes(subChapterNoteId);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void lastCardReached();
    }

    //method for loading notes
    public void loadNotes(final String subChapterRef){

        //firebase
        database = FirebaseDatabase.getInstance();
        notes = database.getReference("notes");
        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot notesetSnapShot = dataSnapshot.child(subChapterRef);
                Iterable<DataSnapshot> noteSet = notesetSnapShot.getChildren();
                ArrayList<Note> fireBaseArray = new ArrayList<>();
                for (DataSnapshot n : noteSet) {
                    Note note = n.getValue(Note.class);
                    fireBaseArray.add(note);
                }
                loading.hide();
                storageContainer(fireBaseArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        noteSwipeDeck.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                if(numberOfNotes==(notesProgressBar.getProgress()+1)){
                    mListener.lastCardReached();
                    notesProgressBar.setProgress((int) stableId + 1);
                }else
                notesProgressBar.setProgress((int) stableId + 1);
            }

            @Override
            public void cardSwipedRight(long stableId) {
                if(numberOfNotes==(notesProgressBar.getProgress()+1)){
                    mListener.lastCardReached();
                    notesProgressBar.setProgress((int) stableId + 1);
                }else
                    notesProgressBar.setProgress((int) stableId + 1);
            }
        });

    }


    public void storageContainer(final ArrayList<Note> notesArrayForCards) {
        notesProgressBar.setProgress(0);
        this.notesArrayForCards = notesArrayForCards;
        adapter = new SwipeNoteAdapter(notesArrayForCards,subChapterName,getContext());
        if (noteSwipeDeck != null) {
            noteSwipeDeck.setAdapter(adapter);
        }else{
            Log.e("noNotes","NULL");
        }
        //progress Bar
        numberOfNotes=adapter.getCount();
        notesProgressBar.setMax(numberOfNotes);
    }


    // goto previous card
    public void goToPrevCard(){
        noteSwipeDeck.unSwipeCard();
        notesProgressBar.setProgress(notesProgressBar.getProgress() - 1);
    }

}
