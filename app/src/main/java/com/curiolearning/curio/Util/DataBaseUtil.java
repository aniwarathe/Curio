package com.curiolearning.curio.Util;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Chinthaka on 1/20/2018.
 */
public class DataBaseUtil {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            //mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }
}