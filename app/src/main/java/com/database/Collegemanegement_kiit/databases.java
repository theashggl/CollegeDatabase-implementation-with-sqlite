package com.database.Collegemanegement_kiit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databases extends SQLiteOpenHelper {
        public static final String data = "student.db";
        public static final String TABLE_NAME = "Student_TABLE";
        public static final String COL_1 = "Fname";
        public static final String COL_2 = "password";
        public static final String COL_3 = "Fathers_name";
        public final String COL_4 = "Roll";
        public static final String COL_5 = "Image";
        private SQLiteOpenHelper dbhelper;

    public databases(Context context) {
            super(context, data, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (Fname TEXT,password TEXT,Fathers_name TEXT,Roll TEXT PRIMARY KEY,Image BLOB)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertdata(String first, String second, String fathers, String roll) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentv = new ContentValues();
            contentv.put(COL_1, first);
            contentv.put(COL_2, second);
            contentv.put(COL_3, fathers);
            contentv.put(COL_4, roll);
            Log.e("inserted","inserted");
            long result = db.insert(TABLE_NAME, null, contentv);
            if(result==-1) {
                return false;
            }
            else
                return true;
        }

        public boolean update(String first, String second, String fathers, String roll) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentv = new ContentValues();
            contentv.put(COL_1, first);
            contentv.put(COL_2, second);
            contentv.put(COL_3, fathers);
            contentv.put(COL_4, roll);
            db.update(TABLE_NAME, contentv, "roll = ?", new String[]{roll});
            return true;
        }

        public Cursor loginuser(String m, String p) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor2 = db.rawQuery("Select * from Student_TABLE where Roll=? AND password=?", new String[]{m,p});
            return cursor2;
        }
        public Cursor getall()
        {
            return(getWritableDatabase().rawQuery("Select image from Student_TABLE",null));
        }

        public Integer deletedata(String id)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            return db.delete(TABLE_NAME,"roll=?",new String[] {id});
        }

        public Cursor loginusershow(String thing) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor2 = db.rawQuery("Select * from "+ TABLE_NAME +" where Roll=?",new String[]{thing});
            if (cursor2 != null) {
                cursor2.moveToFirst();
            }
            return cursor2;
        }

        public Cursor givedata(String id)
        {
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor gfjh;
            gfjh = db.rawQuery("Select * from Student_TABLE where Roll=?",new String[]{id});
            return gfjh;
        }
    }