package com.guideteee.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 14/12/18.
 */

public class DbObject {
    static DbHandler db;
    Context contx;
//Context
    public  DbObject(Context cotext) {
        this.contx=cotext;
        db=new DbHandler(cotext);
    }


    public static DbHandler getDb() {
        return db;
    }

}
