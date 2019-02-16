package com.cruise.preetika.cruiseapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cruise.preetika.cruiseapp.database.DBHelper;


/**
 * Created by 300283413 on 7/26/2018.
 */

public class CommonUtilities {

    public static DBHelper getDBObject(Context mContext) {
        DBHelper dbhelper = DBHelper.getInstance(mContext);
        return dbhelper;
    }

    public static void hideKeyboard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
