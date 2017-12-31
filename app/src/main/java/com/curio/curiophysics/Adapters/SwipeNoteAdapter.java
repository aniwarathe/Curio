package com.curio.curiophysics.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.curio.curiophysics.Model.Note;
import com.curio.curiophysics.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chin on 12/12/2017.
 */

public class SwipeNoteAdapter extends BaseAdapter {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://curiophysics.appspot.com").child("C1_1_2.json");


    private ArrayList<Note> notes;
    Context context;
    LottieAnimationView lottieAnimationView;
    public String jsonRef = "";
    ArrayList<JSONObject> jsonArray=new ArrayList<>();
    String jsonString;


    public SwipeNoteAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        this.jsonArray=jsonArray;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.note_text)).setText(notes.get(position).getNote());
        lottieAnimationView = v.findViewById(R.id.anim);
        lottieAnimationView.setAnimation(notes.get(position).getAnim());
        lottieAnimationView.playAnimation();
        return v;
    }
}


