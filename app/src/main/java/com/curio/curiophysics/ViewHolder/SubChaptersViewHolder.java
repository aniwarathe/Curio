package com.curio.curiophysics.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.curio.curiophysics.Interface.ItemClickListner;
import com.curio.curiophysics.R;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class SubChaptersViewHolder extends AbstractExpandableItemViewHolder {
    public TextView subChapterTitle;
    public SubChaptersViewHolder(View itemView) {
        super(itemView);
        subChapterTitle=itemView.findViewById(R.id.sub_chapter_title);
    }
}
