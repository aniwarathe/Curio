package com.curiolearning.curio.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.curiolearning.curio.R;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class SubChaptersViewHolder extends AbstractExpandableItemViewHolder {
    public TextView subChapterTitle;
    public ImageView subChapterBackgroud;
    public SubChaptersViewHolder(final View itemView,View.OnClickListener clickListener) {
        super(itemView);
        subChapterTitle=itemView.findViewById(R.id.sub_chapter_title);
        subChapterBackgroud=itemView.findViewById(R.id.subchapter_color);
        itemView.setOnClickListener(clickListener);

    }
}
