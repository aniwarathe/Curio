package com.curiolearning.curio.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.curiolearning.curio.Model.Note;
import com.curiolearning.curio.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chin on 12/12/2017.
 */

public class SwipeNoteAdapter extends BaseAdapter {

    private ArrayList<Note> notes;
    private String subChapterName;
    private Context context;
    private LottieAnimationView lottieAnimationView;


    public SwipeNoteAdapter(ArrayList<Note> notes, String subChapterName, Context context) {
        this.notes = notes;
        this.subChapterName = subChapterName;
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
            final ProgressBar noteProgress=v.findViewById(R.id.note_image_progress_bar);
            noteProgress.setVisibility(View.VISIBLE);
            Picasso.with(context).load(notes.get(position)
                    .getImage())
                    .into(((ImageView) v.findViewById(R.id.note_image)), new Callback() {
                        @Override
                        public void onSuccess() {
                            noteProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

            ShareLinkContent content=new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://www.facebook.com/curiolearn/"))
                    .build();

         /*   ShareButton fbShare=v.findViewById(R.id.btn_fb_share);
            fbShare.setShareContent(content);*/


        } else if (notes.get(position).getType().equals("1")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_with_anim, parent, false);
            lottieAnimationView = v.findViewById(R.id.anim);

            TextView note= v.findViewById((R.id.note_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                note.setText(Html.fromHtml(notes.get(position).getNote(),Html.FROM_HTML_MODE_COMPACT));
            } else {
                note.setText(Html.fromHtml(notes.get(position).getNote()));
            }
                //lottieAnimationView.setAnimation(notes.get(position).getAnim());

            lottieAnimationView.setAnimation("1205.json");
            lottieAnimationView.playAnimation();

        } else if (notes.get(position).getType().equals("2")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_with_image, parent, false);
            final ProgressBar noteProgress=v.findViewById(R.id.note_image_progress_bar);
            noteProgress.setVisibility(View.VISIBLE);

            TextView note= v.findViewById((R.id.note_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                note.setText(Html.fromHtml(notes.get(position).getNote(),Html.FROM_HTML_MODE_COMPACT));
            } else {
                note.setText(Html.fromHtml(notes.get(position).getNote()));
            }

            Picasso.with(context).load(notes.get(position)
                    .getImage())
                    .into(((ImageView) v.findViewById(R.id.note_image)), new Callback() {
                        @Override
                        public void onSuccess() {
                            noteProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        } else if (notes.get(position).getType().equals("3")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_only_text, parent, false);
            TextView note= v.findViewById((R.id.note_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                note.setText(Html.fromHtml(notes.get(position).getNote(),Html.FROM_HTML_MODE_COMPACT));
            } else {
                note.setText(Html.fromHtml(notes.get(position).getNote()));
            }


        }else if (notes.get(position).getType().equals("4")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_image_only, parent, false);
            final ProgressBar noteProgress=v.findViewById(R.id.note_image_progress_bar);
            Picasso.with(context).load(notes.get(position)
                    .getImage())
                    .into(((ImageView) v.findViewById(R.id.note_image)), new Callback() {
                        @Override
                        public void onSuccess() {
                            noteProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else if (notes.get(position).getType().equals("5")) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.note_item_last, parent, false);
        }
        return v;
    }
    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}


