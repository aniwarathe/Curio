package com.curio.curiophysics.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curio.curiophysics.Interface.ItemClickListner;
import com.curio.curiophysics.Model.Chapter;
import com.curio.curiophysics.R;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.nightonke.boommenu.BoomMenuButton;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class ChaptersViewHolder extends AbstractExpandableItemViewHolder {
    public TextView chapterTitle;
    public ImageView chapterBackground;
    public ChaptersViewHolder(View itemView) {
        super(itemView);
        chapterTitle=itemView.findViewById(R.id.chapter_title);
        chapterBackground=itemView.findViewById(R.id.image_color);
    }
}
