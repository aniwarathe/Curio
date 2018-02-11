package com.curio.curiophysics.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.curio.curiophysics.Model.Note;
import com.curio.curiophysics.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by chin on 12/12/2017.
 */

public class SwipeNoteAdapter extends BaseAdapter {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://curiophysics.appspot.com").child("1.json");


    private ArrayList<Note> notes;
    private ArrayList<JSONObject> anims;
    Context context;
    LottieAnimationView lottieAnimationView;
    JSONObject jsonObject;
    InputStream inputStream;
    ArrayList<InputStream> inputStreams;


    public SwipeNoteAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        // this.jsonObject=anim;

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

        if (notes.get(position).getType().equals("0")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_front, parent, false);

            MaterialFancyButton facebookLoginBtn;
            facebookLoginBtn = v.findViewById(R.id.btn_facebook);
            facebookLoginBtn.setText("Login with Facebook");
            facebookLoginBtn.setBackgroundColor(Color.parseColor("#3b5998"));
            facebookLoginBtn.setFocusBackgroundColor(Color.parseColor("#5474b8"));
            facebookLoginBtn.setTextSize(17);
            facebookLoginBtn.setRadius(5);
            facebookLoginBtn.setIconFont("fontawesome.ttf");
            facebookLoginBtn.setIconResource("\uf082");
            facebookLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
            facebookLoginBtn.setFontIconSize(30);

        } else if (notes.get(position).getType().equals("1")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_with_anim, parent, false);
            lottieAnimationView=v.findViewById(R.id.anim);
            ((TextView) v.findViewById(R.id.note_text)).setText(notes.get(position).getNote());
           // lottieAnimationView.setAnimation(notes.get(position).getAnim());
            lottieAnimationView.setAnimation("1102.json");
            lottieAnimationView.playAnimation();

        } else if (notes.get(position).getType().equals("2")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_with_image, parent, false);
            ((TextView) v.findViewById(R.id.note_text)).setText(notes.get(position).getNote());
            Picasso.with(context).load(notes.get(position).getImage()).into((ImageView)v.findViewById(R.id.note_image));

        } else if (notes.get(position).getType().equals("3")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_only_text, parent, false);
            ((TextView)v.findViewById(R.id.note_title)).setText(notes.get(position).getTitle());
            ((TextView) v.findViewById(R.id.note_text)).setText(notes.get(position).getNote());

        }
        else if (notes.get(position).getType().equals("4")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_with_addview, parent, false);

        }
        return v;
    }
}


