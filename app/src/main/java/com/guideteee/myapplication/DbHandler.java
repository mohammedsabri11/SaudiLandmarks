package com.guideteee.myapplication;

/**
 * Created by user on 27/11/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class DbHandler extends SQLiteOpenHelper {
    //all constants as they are static and final(Db=Database)
    //Db Version
    private static final int Db_Version=1;
    //Db Name
    private static final String Db_Name="phasoraccouts";
    //table name
    private static final String Table_Name="user";
    private static final String Table_Name2="log";
    private static final String path ="/data/data/com.helpme.helpe/databases/";
    //Creating mycontacts Columns
    private static final String User_id="id";
    private static final String User_name="name";
    private static final String User_password="password";
    private static final String User_phoneNum="phone";
    private static SQLiteDatabase  db;
Context c;
    public DbHandler(Context context)
    {


        super(context,Db_Name,null,Db_Version);
    }
    public void openDatabase () throws SQLException {

        String myPath = path+Db_Name;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }
    @Override
    public synchronized void close () {

        if (db !=null){

            db.close();
        }

        super.close();

    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // writing command for sqlite to create table with required columns
        String Create_Table1="CREATE TABLE " + Table_Name + "(" + User_id
                + " INTEGER PRIMARY KEY," + User_name + " TEXT ," + User_password + " TEXT," +User_phoneNum + " TEXT" + ")";
        String Create_Table2="CREATE TABLE " + Table_Name2 + "(" + User_id
                + " INTEGER PRIMARY KEY," + User_name + " TEXT ," + User_password + " TEXT," +User_phoneNum + " TEXT" + ")";
try {

    db.execSQL(Create_Table1);
    db.execSQL(Create_Table2);
}catch (Exception r){
    r.printStackTrace();
}


    }
    //Upgrading the Db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table if exists
try{
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
    db.execSQL("DROP TABLE IF EXISTS " + Table_Name2);
        //create the table again
        onCreate(db);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    //Add new User by calling this method
    public long addUser(User usr)
    {
        // getting db instance for writing the user
         db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(User_name,usr.getName());
        cv.put(User_password,usr.getPassword());
        cv.put(User_phoneNum,usr.getphone());
        //inserting row
        long id=-1;
try {
        id=db.insert(Table_Name, null, cv);
        //close the database to avoid any leak
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

        return id;
    }
    public int checkUser(User us)
    {
        int id=-1;
      try{
       db=this.getReadableDatabase();
        String url="SELECT id FROM user WHERE name= ? AND password= ? ";
        Cursor cursor=db.rawQuery(url,new String[]{us.getName(),us.getPassword()});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
            db.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return id;
    }


    public String checklog()
    {  String phisorname = null;
       db=this.getWritableDatabase();
        String[] columns = { "name"};
        if (db!=null) {
            Cursor cursor = db.query(Table_Name2, columns, null, null, null, null, null);


        while (cursor.moveToNext())
        {

            phisorname =cursor.getString(cursor.getColumnIndex("name"));

        }}
        db.close();
        return  phisorname;
    }
    public String checklognumber()
    {
        String  phisorphone =null;
        db=this.getWritableDatabase();
        String[] columns = { "phone"};
        Cursor cursor=null;
        if (db!=null){
      cursor =db.query(Table_Name2,columns,null,null,null,null,null);


      if (cursor!=null){
        while (cursor.moveToNext())
        {

            phisorphone =cursor.getString(cursor.getColumnIndex("phone"));

        }}}
        cursor.close();
        db.close();
        return   phisorphone ;
    }

    public  void delete()
    {
        db=this.getWritableDatabase();
        try {
            String Create_Table2="CREATE TABLE " + Table_Name2 + "(" + User_id
                    + " INTEGER PRIMARY KEY," + User_name + " TEXT ," + User_password + " TEXT," +User_phoneNum + " TEXT" + ")";

            db.execSQL("DROP TABLE IF EXISTS " + Table_Name2);
                db.execSQL(Create_Table2);


        //create the table again
            db.close();
            return;
    } catch (Exception e) {

        e.printStackTrace();
            db.close();
    }



    }

    public long addlog(User usr)
    {
        // getting db instance for writing the user
         db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(User_name,usr.getName());
        cv.put(User_password,usr.getPassword());
        cv.put(User_phoneNum,usr.getphone());
        //inserting row
        long id=-1;
        try {
            id=db.insert(Table_Name2, null, cv);
            //close the database to avoid any leak
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return id;
    }




    public String phonenum(int U_id) {


        db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {User_id,User_name,User_password,User_phoneNum};
        //Select condition
        String selection = User_id + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(U_id)};
String phone=null;

        Cursor cursor = db.query(Table_Name, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            phone=cursor.getString(3);


        }
        db.close();
        return phone;

    }
    public Cursor getAllData() {
      db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_Name,null);
       db.close();
        return res;
    }



}