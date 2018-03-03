package com.curiolearning.curio.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Chinthaka on 2/18/2018.
 */

public class NoteFragmentsAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> subChapterFragments=new ArrayList<>();

    public NoteFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return subChapterFragments.get(position);
    }

    @Override
    public int getCount() {
        return subChapterFragments.size();
    }

    public void addFragment(Fragment fragment){
        subChapterFragments.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
