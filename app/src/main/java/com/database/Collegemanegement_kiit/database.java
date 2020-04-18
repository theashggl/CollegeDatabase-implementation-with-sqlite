package com.database.Collegemanegement_kiit;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class  database extends SQLiteOpenHelper {
    public static final String data = "imagedata.db";
    public static final String DBLOCATION = "/data/data/com.database.log_on/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public database(Context context){
    super(context, data, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void OpenDatabase() {
        String dbPath = mContext.getDatabasePath(data).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void CloseDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
    public long ImageInsert(int id, byte[] image) {
        ContentValues content = new ContentValues();
        content.put("ID", id);
        content.put("IMAGE", image);
        OpenDatabase();
        long returnValue = mDatabase.insert("GALLERYPIC", null, content);
        CloseDatabase();
        return returnValue;
    }
}