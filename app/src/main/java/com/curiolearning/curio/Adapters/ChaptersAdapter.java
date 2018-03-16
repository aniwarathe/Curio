package com.curiolearning.curio.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.curiolearning.curio.R;

import com.curiolearning.curio.Model.Chapter;
import com.curiolearning.curio.Model.SubChapter;
import com.curiolearning.curio.ViewHolder.ChaptersViewHolder;
import com.curiolearning.curio.ViewHolder.SubChaptersViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;

import java.util.ArrayList;

import static com.curiolearning.curio.ChaptersActivity.mItemOnClickListener;

/**
 * Created by Chinthaka on 12/31/2017.
 */

public class ChaptersAdapter extends AbstractExpandableItemAdapter<ChaptersViewHolder, SubChaptersViewHolder> {
    private ArrayList<Chapter> mItems;
    private int color;

    public ArrayList<Chapter> getmItems() {
        return mItems;
    }

    public ChaptersAdapter(ArrayList<Chapter> chapterItems) {
        setHasStableIds(true); // this is required for expandable feature.
        mItems=chapterItems;
    }

    public int getChild(int groupPosition){
        return getChildCount(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }


    @Override
    public int getChildCount(int groupPosition) {
        return mItems.get(groupPosition).subChapters.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // This method need to return unique value within all group items.
        return mItems.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // This method need to return unique value within the group.
        return mItems.get(groupPosition).subChapters.get(childPosition).getId();
    }

    @Override
    public ChaptersViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent, false);
        return new ChaptersViewHolder(v);
    }

    @Override
    public SubChaptersViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_chapter_item, parent, false);
        return new SubChaptersViewHolder(v, mItemOnClickListener);

    }

    @Override
    public void onBindGroupViewHolder(ChaptersViewHolder holder, int groupPosition, int viewType) {
        Chapter group = mItems.get(groupPosition);
        holder.chapterTitle.setText(group.getName());
        color=Color.parseColor(group.getColor());
        holder.chapterBackground.setBackgroundColor(color);

    }

    @Override
    public void onBindChildViewHolder(SubChaptersViewHolder holder, int groupPosition, int childPosition, int viewType) {
        SubChapter child = mItems.get(groupPosition).subChapters.get(childPosition);
        holder.subChapterTitle.setText(child.getName());
        holder.subChapterBackgroud.setBackgroundColor(color);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(ChaptersViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return true;
    }

}
